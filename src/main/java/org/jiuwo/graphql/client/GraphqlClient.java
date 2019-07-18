package org.jiuwo.graphql.client;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jiuwo.graphql.client.contract.ExecutionResult;
import org.jiuwo.graphql.client.contract.GraphqlRequest;
import org.jiuwo.graphql.client.contract.ResultAttribute;
import org.jiuwo.graphql.client.enums.RequestTypeEnum;
import org.jiuwo.graphql.client.util.HttpUtil;

/**
 * @author Steven Han
 */
public class GraphqlClient {
    /**
     * 远程GraphQL地址
     */
    private String url;

    /**
     * 请求类型
     */
    private RequestTypeEnum requestType = RequestTypeEnum.QUERY;

    /**
     * Headers
     */
    private Map<String, String> headers = new HashMap<>();

    private int currentRequestIndex = 0;

    private Map<Integer, GraphqlRequest> requestMap = new HashMap<>();

    /**
     * build GraphqlClient 并初使化request
     *
     * @return GraphqlClient
     */
    public static GraphqlClient build() {
        GraphqlClient graphqlClient = new GraphqlClient();
        return graphqlClient;
    }

    /**
     * 执行请求
     *
     * @return 执行结果
     */
    public ExecutionResult execute() {
        if (this.requestType.equals(RequestTypeEnum.QUERY)) {
            return query();
        } else {
            return mutation();
        }
    }

    /**
     * Queryy请求
     *
     * @return 查询结果
     */
    private ExecutionResult query() {
        return HttpUtil.postJson(url, this.toString(), this.headers, ExecutionResult.class);
    }

    /**
     * Mutation请求
     *
     * @return 操作结果
     */
    private ExecutionResult mutation() {
        return HttpUtil.postJson(url, this.toString(), this.headers, ExecutionResult.class);
    }

    /**
     * 设置URL
     *
     * @param url URL
     * @return this
     */
    public GraphqlClient url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 增加Header
     *
     * @param key   Header名
     * @param value Header值
     * @return this
     */
    public GraphqlClient addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * 设置Header
     *
     * @param headers Headers
     * @return this
     */
    public GraphqlClient headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public GraphqlClient addRequest(GraphqlRequest request) {
        this.currentRequestIndex++;
        this.requestMap.put(this.currentRequestIndex, request);
        return this;
    }

    public GraphqlClient requestType(RequestTypeEnum requestType) {
        this.requestType = requestType;
        return this;
    }

    private GraphqlRequest getRequest(int index) {
        return requestMap.get(index);
    }

    private GraphqlRequest getRequest() {
        return getRequest(this.currentRequestIndex);
    }

    /**
     * 重写toString
     *
     * @return 结果
     */
    @Override
    public String toString() {
        String query = "{\"query\":\"%s{%s}\"}";
        StringBuilder requestBuilder = new StringBuilder();
        this.requestMap.forEach((k, v) -> {
            String parameters = v.getRequestParameter().toString();
            requestBuilder.append(v.getRequestName());
            requestBuilder.append(parameters);
            requestBuilder.append("{");
            requestBuilder.append(v.getResultAttributes().stream().map(ResultAttribute::toString).collect(Collectors.joining(" ")));
            requestBuilder.append("}");
        });
        return String.format(query, requestType.equals(RequestTypeEnum.QUERY) ? "" : "mutation", requestBuilder.toString());
    }

    public static void main(String[] args) {
        String url = "http://localhost:8080/graphql";
        ExecutionResult object = GraphqlClient
                .build()
                .url(url)
                .requestType(RequestTypeEnum.QUERY)
                .addHeader("hash", "haha")
                .addRequest(GraphqlRequest.build("findBooks").addResultAttributes("id name").addResultAttribute("author", "id", "name"))
                .addRequest(GraphqlRequest.build("findAuthors").addResultAttributes("id name"))
                .execute();
        System.out.println(object);
    }
}
