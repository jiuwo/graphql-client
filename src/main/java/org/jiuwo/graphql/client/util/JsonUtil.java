package org.jiuwo.graphql.client.util;

import java.lang.reflect.Type;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Steven Han
 */
public class JsonUtil {
    private static final SerializeConfig config;
    private static final SerializerFeature[] features = {
            // 输出空置字段
            SerializerFeature.WriteMapNullValue,
            // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullListAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty
    };

    static {
        config = new SerializeConfig();
        // 使用和json-lib兼容的日期输出格式
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer());
        // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
    }

    /**
     * 对象转Json
     *
     * @param object 对象
     * @return Json
     */
    public static String toJson(Object object) {
        return JSON.toJSONString(object, config, features);
    }

    /**
     * 对象转Json 不用features
     *
     * @param object 对象
     * @return Json
     */
    public static String toJsonNoFeatures(Object object) {
        return JSON.toJSONString(object, config);
    }

    /**
     * @param json Json转对象
     * @return 对象
     */
    public static Object toObject(String json) {
        return JSON.parse(json);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> T toObject(String json, Type type) {
        return JSON.parseObject(json, type);
    }

    /**
     * 转换为List
     *
     * @param json  Json
     * @param clazz clazz
     * @param <T>   对象类型
     * @return List
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

}
