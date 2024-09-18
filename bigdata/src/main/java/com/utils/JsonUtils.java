package com.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.flink.dataStream.etl.T1;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@UtilityClass
public class JsonUtils {

    ObjectMapper mapper=new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    public String toString(Object object){
        String s = null;
        try {
            s = mapper.writeValueAsString(object);
            return s;
        } catch (Exception e) {
            log.info("序列化错误,[obj:{}]",object,e);
            throw new RuntimeException(e);
        }
    }

    public <T> T toEntity(String value,Class<T> clazz){
        try {
            return mapper.readValue(value, clazz);
        } catch (Exception e) {
            log.info("反序列化错误,[obj:{}]",value,e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(toString(T1.builder().time(LocalDateTime.now()).build()));
        System.out.println(toEntity("{\"id\":null,\"money\":null,\"time\":\"2024-09-07T14:29:05.386\"}\n",T1.class));
    }
}
