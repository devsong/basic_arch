package com.gzs.learn.common.util;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {
    private static final ObjectMapper jsonConvertInstance = new ObjectMapper();

    static {
        // 忽略未识别的属性
        jsonConvertInstance.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        //
        jsonConvertInstance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 输出非空字段
        jsonConvertInstance.setSerializationInclusion(Include.NON_NULL);
    }

    /**
     * 对象到json
     * @param obj
     * @param features
     * @return
     */
    public static String toJSONString(Object object) {
        try {
            return jsonConvertInstance.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json到对象
     * @param <T>
     * @param str
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String str, Class<T> clazz) {
        try {
            T readValue = jsonConvertInstance.readValue(str, clazz);
            return readValue;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json到对象
     * @param <T>
     * @param str
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String str, TypeReference<T> ref) {
        try {
            T readValue = jsonConvertInstance.readValue(str, ref);
            return readValue;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 序列化列表
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        JavaType javaType = jsonConvertInstance.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            List<T> readValue = jsonConvertInstance.readValue(json, javaType);
            return readValue;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
