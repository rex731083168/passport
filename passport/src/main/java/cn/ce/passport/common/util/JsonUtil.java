package cn.ce.passport.common.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * json编解码工具类
 * 
 * @author kyo.ou 2013-7-16
 * 
 */

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将一个对象编码为json字符串
     * 
     * @param obj
     *            ,if null return "null" 要编码的字符串
     * @return json字符串
     * @throws RuntimeException
     *             若对象不能被编码为json串
     */
    public static String toJson(Object obj)
    {
        if (obj == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("error encode json for " + obj, e);
        }
    }
    
//    public static void main(String[] args) {
//		List<Order> ors = new ArrayList<Order>();
//		Order or = new Order();
//		or.setAmount(1);
//		or.setOrderNo("absdd");
//		
//		ors.add(or);
//		ors.add(or);
//		
//		String json = toJson(ors);
//		System.out.println(json);
//	}

    /**
     * 将一个对象编码成字节
     * 
     * @param obj
     * @return
     */
    public static byte[] toBytes(Object obj)
    {

        try {
            return MAPPER.writeValueAsBytes(obj);
        } catch (Exception e) {
            throw new RuntimeException("error encode json for " + obj, e);
        }
    }

    /**
     * 将一个json字符串解码为java对象
     * 
     * 注意：如果传入的字符串为null，那么返回的对象也为null
     * 
     * @param json
     *            json字符串
     * @param cls
     *            对象类型
     * @return 解析后的java对象
     * @throws RuntimeException
     *             若解析json过程中发生了异常
     */
    public static <T> T toObject(String json, Class<T> cls)
    {
        if (json == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, cls);
        } catch (Exception e) {
            throw new RuntimeException("error decode json to " + cls, e);
        }
    }

    /**
     * 将json字节解码为java对象
     * 
     * @param jsonBytes
     *            json字节
     * @param cls
     *            对象类型
     * @return 解码后的对象
     */
    public static <T> T toObject(byte[] jsonBytes, Class<T> cls)
    {
        try {
            return MAPPER.readValue(jsonBytes, cls);
        } catch (Exception e) {
            throw new RuntimeException("error decode json to " + cls, e);
        }
    }

    /**
     * 将json字符串解码为java对象
     * 
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> T toObject(String json, TypeReference typeReference)
    {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("error decode json to " + typeReference, e);
        }
    }

    /**
     * 将json字节解码为java对象
     * 
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> T toObject(byte[] jsonBytes, TypeReference typeReference)
    {
        try {
            return MAPPER.readValue(jsonBytes, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("error decode json to " + typeReference, e);
        }
    }
    
    /**
     * 读取JSON字符串为MAP
     * 
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> readMap(String json)
    {
        try {
            return MAPPER.readValue(json, HashMap.class);
        } catch (Exception e) {
            throw new RuntimeException("error decode json to hashmap" , e);
        }
    }

}
