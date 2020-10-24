package com.kupug.kuoauth.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kupug.kuoauth.KuOAuthException;

/**
 * <p>
 * json 工具助手
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class JsonUtils {

    private static ObjectMapper objectMapper;

    /*
     * jackson 全局配置
     * 增加忽视反序列化，未匹配到字段时，抛出异常的配置
     */
    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private JsonUtils() {
    }

    public static String toJSONString(Object source) {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new KuOAuthException(String.format("Json processing fail[%s].", e.getMessage()), e);
        }
    }

    public static <T> T parseObject(String source, Class<T> clazz) {

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        try {
            return objectMapper.readValue(source, clazz);
        } catch (JsonProcessingException e) {
            throw new KuOAuthException(String.format("Json processing fail[%s].", e.getMessage()), e);
        }
    }

    public static <T> T parseObject(Object source, Class<T> clazz) {

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

        return objectMapper.convertValue(source, clazz);
    }

    public static JsonNode parseObject(String source) {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        try {
            return objectMapper.readTree(source);
        } catch (JsonProcessingException e) {
            throw new KuOAuthException(String.format("Json processing fail[%s].", e.getMessage()), e);
        }
    }
}
