package org.jiuwo.graphql.client.contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Steven Han
 */
public class RequestParameter extends HashMap<String, Object> {

    /**
     * build一个RequestParameter
     *
     * @return this
     */
    public static RequestParameter build() {
        return new RequestParameter();
    }

    /**
     * 添加一个请求参数
     *
     * @param key   参数名
     * @param value 参数户的值
     * @return this
     */
    public RequestParameter addParameter(String key, Object value) {
        put(key, value);
        return this;
    }

    /**
     * 重写toString转为可用值
     *
     * @return RequestParameter String
     */
    @Override
    public String toString() {
        if (this.size() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("(");
        stringBuilder.append(String.join(" ", toList()));
        stringBuilder.append(")");
        return String.join(",", stringBuilder);

    }

    private List<String> toList() {
        if (this.size() <= 0) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        this.forEach((k, v) ->
                list.add(String.format(String.format("%s:%s", k, packValue(v))))
        );
        return list;
    }

    /**
     * 根据值类型返回相应的字符串
     *
     * @param value 值
     * @return 封装后的字符串
     */
    private String packValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Integer
                || value instanceof Boolean
                || value instanceof Float
                || value instanceof Double) {
            return String.valueOf(value);
        }
        if (value instanceof Enum) {
            Enum enumVal = (Enum) value;
            String enumName = enumVal.name();
            return enumName;
        }
        return String.format("\\\"%s\\\"", value);
    }
}
