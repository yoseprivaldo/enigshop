package com.enigmacamp.enigshop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {
    private final ObjectMapper objectMapper;

    public JsonUtils (ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public <T> T parseJson(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }
}
