package com.smart4j.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用jackson实现json对pojo类的转换
 * Created by Administrator on 2017/7/19.
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将POJO转换成json
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        String json;
        try {
            json=OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("转换json失败", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将pojo转换成json
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json,Class<?> type){
        T pojo;
        try {
            pojo= (T) OBJECT_MAPPER.readValue(json,type);
        } catch (Exception e) {
            LOGGER.error("转换POJO失败", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
