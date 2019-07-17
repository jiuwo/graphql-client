package org.jiuwo.graphql.client.contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * @author Steven Han
 */
@Getter
public class ResultAttribute {

    /**
     * 子节点
     */
    public List<ResultAttribute> childrenResultAttributes = new ArrayList<>();
    /**
     * 返回字段名
     */
    private String name;

    /**
     * build ResultAttribute
     *
     * @return ResultAttribute
     */
    public static ResultAttribute build() {
        return new ResultAttribute();
    }

    /**
     * SET字段名值
     *
     * @param name 值
     * @return this
     */
    public ResultAttribute setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 增加子节点
     *
     * @param names 节点字段名
     * @return this
     */
    public ResultAttribute addChildrenResultAttribute(String... names) {
        if (names != null && names.length > 0) {
            for (String str : names) {
                childrenResultAttributes.add(ResultAttribute.build().setName(str));
            }

        }
        return this;
    }

    /**
     * 增加子节点
     *
     * @param resultAttributeArr 子节点
     * @return this
     */
    public ResultAttribute addChildrenResultAttributes(ResultAttribute... resultAttributeArr) {
        if (resultAttributeArr != null && resultAttributeArr.length > 0) {
            childrenResultAttributes.addAll(Arrays.asList(resultAttributeArr));
        }
        return this;
    }

    /**
     * 重写toString返回可用值
     *
     * @return ResultAttribute String
     */
    @Override
    public String toString() {
        if (childrenResultAttributes.size() == 0) {
            return name;
        }
        StringBuilder stringBuilder = new StringBuilder(name);
        stringBuilder.append("{");
        stringBuilder.append(childrenResultAttributes.stream().map(ResultAttribute::toString).collect(Collectors.joining(" ")));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
