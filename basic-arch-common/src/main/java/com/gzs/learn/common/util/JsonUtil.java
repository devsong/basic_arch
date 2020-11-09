package com.gzs.learn.common.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 忽略未识别的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        //
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 输出非空字段
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // jsonConvertInstance.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
    }

    /**
     * 对象到json
     * @param obj
     * @param features
     * @return
     */
    public static String toJSONString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
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
            T readValue = objectMapper.readValue(str, clazz);
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
            T readValue = objectMapper.readValue(str, ref);
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
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            List<T> readValue = objectMapper.readValue(json, javaType);
            return readValue;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
