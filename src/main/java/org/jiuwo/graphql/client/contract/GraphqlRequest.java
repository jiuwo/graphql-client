package org.jiuwo.graphql.client.contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import org.jiuwo.graphql.client.enums.RequestTypeEnum;

/**
 * @author Steven Han
 */
@Getter
@Setter
public class GraphqlRequest {
    /**
     * 请求类型
     */
    private RequestTypeEnum requestType = RequestTypeEnum.QUERY;

    /**
     * 请求名称
     */
    private String requestName;

    /**
     * Headers
     */
    private Map<String, String> headers = new HashMap<>();

    /**
     * 请求参数
     */
    private RequestParameter requestParameter = RequestParameter.build();

    /**
     * 返回结果字段信息
     */
    private List<ResultAttribute> resultAttributes = new ArrayList<>();

    /**
     * 重写toString
     *
     * @return 结果
     */
    @Override
    public String toString() {
        String query = "{\"query\":\"%s{%s}\"}";
        String parameters = this.requestParameter.toString();

        StringBuilder requestBuilder = new StringBuilder(requestName);
        requestBuilder.append(parameters);
        requestBuilder.append("{");
        requestBuilder.append(this.resultAttributes.stream().map(ResultAttribute::toString).collect(Collectors.joining(" ")));
        requestBuilder.append("}");

        return String.format(query, requestType.equals(RequestTypeEnum.QUERY) ? "" : "mutation", requestBuilder.toString());
    }

}