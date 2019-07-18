package org.jiuwo.graphql.client.contract;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * @author Steven Han
 */
@Getter
public class GraphqlRequest {

    /**
     * 请求名称
     */
    private String requestName;

    /**
     * 请求参数
     */
    private RequestParameter requestParameter = RequestParameter.build();

    /**
     * 返回结果字段信息
     */
    private List<ResultAttribute> resultAttributes = new ArrayList<>();

    public GraphqlRequest(String requestName) {
        this.requestName = requestName;
    }

    public static GraphqlRequest build(String requestName) {
        return new GraphqlRequest(requestName);
    }

    /**
     * 增加参数
     *
     * @param key   参数名
     * @param value 参数值
     * @return this
     */
    public GraphqlRequest addParameter(String key, Object value) {
        this.requestParameter.addParameter(key, value);
        return this;
    }

    /**
     * 设置参数
     *
     * @param requestParameter 参数
     * @return this
     */
    public GraphqlRequest parameter(RequestParameter requestParameter) {
        this.requestParameter = requestParameter;
        return this;
    }

    /**
     * 增加返回字段
     *
     * @param names 字段名
     * @return this
     */
    public GraphqlRequest addResultAttributes(String... names) {
        if (names != null && names.length > 0) {
            for (String key : names) {
                this.resultAttributes.add(ResultAttribute.build().setName(key));
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
    public GraphqlRequest addResultAttribute(String rootName, String... names) {
        this.resultAttributes.add(ResultAttribute.build().setName(rootName).addChildrenResultAttribute(names));
        return this;
    }

    /**
     * 设置返回字段
     *
     * @param resultAttributes 返回字段
     * @return this
     */
    public GraphqlRequest resultAttribute(List<ResultAttribute> resultAttributes) {
        this.resultAttributes = resultAttributes;
        return this;
    }

}