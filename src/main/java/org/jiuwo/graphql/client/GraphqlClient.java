package org.jiuwo.graphql.client;

import java.util.List;
import java.util.Map;

import org.jiuwo.graphql.client.contract.ExecutionResult;
import org.jiuwo.graphql.client.contract.GraphqlRequest;
import org.jiuwo.graphql.client.contract.RequestParameter;
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
     * 请求对象
     */
    private GraphqlRequest request;

    /**
     * build GraphqlClient 并初使化request
     *
     * @return GraphqlClient
     */
    public static GraphqlClient build() {
        GraphqlClient graphqlClient = new GraphqlClient();
        graphqlClient.request = new GraphqlRequest();
        return graphqlClient;
    }

    /**
     * 执行请求
     *
     * @return 执行结果
     */
    public ExecutionResult execute() {
        if (request.getRequestType().equals(RequestTypeEnum.QUERY)) {
            return query(request);
        } else {
            return mutation(request);
        }
    }

    /**
     * Queryy请求
     *
     * @param request Request
     * @return 查询结果
     */
    private ExecutionResult query(GraphqlRequest request) {
        return HttpUtil.postJson(url, request.toString(), request.getHeaders(), ExecutionResult.class);
    }

    /**
     * Mutation请求
     *
     * @param request Request
     * @return 操作结果
     */
    private ExecutionResult mutation(GraphqlRequest request) {
        return HttpUtil.postJson(url, request.toString(), request.getHeaders(), ExecutionResult.class);
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
     * 增加返回字段
     *
     * @param names 字段名
     * @return this
     */
    public GraphqlClient addResultAttributes(String... names) {
        if (names != null && names.length > 0) {
            for (String key : names) {
                request.getResultAttributes().add(ResultAttribute.build().setName(key));
            }
        }
        return this;
    }

    /**
     * 增加返回字段
     *
     * @param rootName 根节点名
     * @param names    节点
     * @return this
     */
    public GraphqlClient addResultAttribute(String rootName, String... names) {
        request.getResultAttributes().add(ResultAttribute.build().setName(rootName).addChildrenResultAttribute(names));
        return this;
    }

    /**
     * 设置返回字段
     *
     * @param resultAttributes 返回字段
     * @return this
     */
    public GraphqlClient setResultAttribute(List<ResultAttribute> resultAttributes) {
        request.setResultAttributes(resultAttributes);
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
        request.getHeaders().put(key, value);
        return this;
    }

    /**
     * 设置Header
     *
     * @param headers Headers
     * @return this
     */
    public GraphqlClient setHeader(Map<String, String> headers) {
        request.setHeaders(headers);
        return this;
    }

    /**
     * 增加参数
     *
     * @param key   参数名
     * @param value 参数值
     * @return this
     */
    public GraphqlClient addParameter(String key, Object value) {
        request.getRequestParameter().addParameter(key, value);
        return this;
    }

    /**
     * 设置参数
     *
     * @param requestParameter 参数
     * @return this
     */
    public GraphqlClient setParameter(RequestParameter requestParameter) {
        request.setRequestParameter(requestParameter);
        return this;
    }

    /**
     * 设置请求名
     *
     * @param requestName 请求名
     * @return this
     */
    public GraphqlClient setRequestName(String requestName) {
        request.setRequestName(requestName);
        return this;
    }

    /**
     * 设置请求类型
     *
     * @param requestType 请求类型
     * @return this
     */
    public GraphqlClient setRequestType(RequestTypeEnum requestType) {
        request.setRequestType(requestType);
        return this;
    }
}
