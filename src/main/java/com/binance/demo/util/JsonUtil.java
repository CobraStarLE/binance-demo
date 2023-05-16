package com.binance.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
public class JsonUtil {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private JsonUtil() {
    }

    public static String[] jsonToStrArray(String json){
        try {
            return mapper.readValue(json,String[].class);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return new String[0];
        }
    }

    public static <T> String pojo2Json(T o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return "";
        }
    }

    public static <T> T json2Pojo(String json, Class<T> clazz) {
        try {
            return (T) mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    public static <T> T json2Pojo(InputStream in, Class clazz) {
        try {
            return (T) mapper.readValue(in, clazz);
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    public static String getJSONStringValue(String json, String key) {
        Map<String,String> map=json2Map(json,String.class,String.class);
        return map.get(key);
    }

    public static int getJSONIntValue(String json, String key) {
        Map<String,Integer> map=json2Map(json,String.class,Integer.class);
        return map.get(key);
    }

    public static <T> List<T> json2List(String json, Class clazz) {
        try {
            CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return mapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return new ArrayList<>();
        }
    }

    /**
     * ⚠注意
     * <br/>
     * &nbsp;&nbsp;
     * 如果Map的值为List类型，则返回的是List泛型为LinkHashMap，
     * <br/>
     * 如果想要实现更加复杂的反序列化，请参考：
     * <br/>
     * https://www.baeldung.com/jackson-map
     *
     * @param json
     * @param clazz_k Map中Key的类型
     * @param clazz_v Map中Value的类型
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> json2Map(String json, Class clazz_k, Class clazz_v) {
        try {
            MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, clazz_k, clazz_v);
            return mapper.readValue(json, mapType);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return Collections.emptyMap();
        }
    }

    public static Map<String, String> json2Map(String json) {
        try {
            TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
            };
            return mapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return Collections.emptyMap();
        }
    }

    /**
     * 如果想要实现更加复杂的序列化，请参考：
     * <br/>
     * https://www.baeldung.com/jackson-map
     *
     * @param map
     * @return
     */
    public static String map2Json(Map map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return "";
        }
    }

    public static String list2Json(List list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return "";
        }
    }

    /**
     * 万能型反序列化，使用方法示例:
     * <ul>
     *     <li>{@code User user2=JsonUtil.json2Obj(userJson, new TypeReference<User>() {});}</li>
     *     <li>{@code List<List<User>> list2=JsonUtil.json2Obj(json, new TypeReference<List<List<User>>>() {});}</li>
     *     <li>{@code Map<String,List<List<User>>> map2=JsonUtil.json2Obj(mapJson, new TypeReference<Map<String,List<List<User>>>>() {});}</li>
     * </ul>
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(String json,TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

}
