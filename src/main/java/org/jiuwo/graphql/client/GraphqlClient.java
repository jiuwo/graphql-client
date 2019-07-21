package org.jiuwo.graphql.client;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import org.jiuwo.graphql.client.contract.ExecutionResult;
import org.jiuwo.graphql.client.contract.GraphqlRequest;
import org.jiuwo.graphql.client.contract.ResultAttribute;
import org.jiuwo.graphql.client.enums.RequestTypeEnum;
import org.jiuwo.graphql.client.util.HttpUtil;
import org.jiuwo.graphql.client.util.JsonUtil;

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

    /**
     * 当前索引
     */
    private int currentRequestIndex = 0;

    /**
     * Request Map
     */
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
     * 执行请求
     *
     * @return 执行结果
     */
    public <T> ExecutionResult<T> executeSingle(Type type) {
        ExecutionResult<T> executionResult = this.execute();
        Object firstValue = ((JSONObject) executionResult.getData()).values().stream().findFirst().orElse(null);
        if (firstValue == null) {
            executionResult.setDataSingle(null);
        } else {
            executionResult.setDataSingle(JsonUtil.toObject(JsonUtil.toJson(firstValue), type));
        }
        return executionResult;
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

    /**
     * 增加Request
     *
     * @param request Request
     * @return this
     */
    public GraphqlClient addRequest(GraphqlRequest request) {
        this.currentRequestIndex++;
        this.requestMap.put(this.currentRequestIndex, request);
        return this;
    }

    /**
     * 本次请求类型
     *
     * @param requestType 请求类型
     * @return this
     */
    public GraphqlClient requestType(RequestTypeEnum requestType) {
        this.requestType = requestType;
        return this;
    }

    /**
     * 根据索引取Request
     *
     * @param index 索引
     * @return this
     */
    private GraphqlRequest getRequest(int index) {
        return requestMap.get(index);
    }

    /**
     * 取当前Request
     *
     * @return request
     */
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
}
