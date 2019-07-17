package org.jiuwo.graphql.client.util;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Steven Han
 */
public class HttpUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String HTTPS = "https";

    private static OkHttpClient httpClient = null;

    private static OkHttpClient httpClientSsl = null;

    static TrustManager[] trustAllCerts() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        X509Certificate[] x509Certificates = new X509Certificate[0];
                        return x509Certificates;
                    }

                    @Override
                    public void checkClientTrusted(
                            X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                }
        };
    }

    static HostnameVerifier doNotVerify() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    static X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }

    public static OkHttpClient getOkHttpClientSSL() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(getTrustedSSLSocketFactory(), x509TrustManager())
                .hostnameVerifier(doNotVerify());
        return builder.build();
    }

    private static SSLSocketFactory getTrustedSSLSocketFactory() {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts(), new java.security.SecureRandom());
            return sc.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OkHttpClient getHttpClientInstance() {
        if (httpClient == null) {
            synchronized (OkHttpClient.class) {
                if (httpClient == null) {
                    httpClient = getOkHttpClient();
                }
            }
        }
        return httpClient;
    }

    public static OkHttpClient getHttpClientSslInstance() {
        if (httpClientSsl == null) {
            synchronized (OkHttpClient.class) {
                if (httpClientSsl == null) {
                    httpClientSsl = getOkHttpClientSSL();
                }
            }
        }
        return httpClientSsl;
    }


    public static OkHttpClient getHttpClient(String url) {
        if (!url.startsWith(HTTPS)) {
            return getHttpClientInstance();
        }
        return getHttpClientSslInstance();
    }

    private static void closeResponse(Response response) {
        if (response != null) {
            response.close();
        }
    }

    /**
     * POST JSON
     *
     * @param url     URL
     * @param json    数据
     * @param headers Headers
     * @param clazz   clazz
     * @param <T>     返回数据类型
     * @return 请求结果
     */
    public static <T> T postJson(String url, String json, Map<String, String> headers, Class<T> clazz) {
        Response response = null;
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .headers(Headers.of(headers))
                    .build();
            response = getHttpClient(url).newCall(request).execute();
            return JsonUtil.toObject(response.body().string(), clazz);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            closeResponse(response);
        }
    }

}




