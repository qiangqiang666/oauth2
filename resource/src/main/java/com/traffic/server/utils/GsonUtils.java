/**
 *
 */
package com.traffic.server.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Administrator
 */
public class GsonUtils {

    private static Gson gson;

    static {
        ExclusionStrategy excludeStrategy = new SetterExclusionStrategy();
        gson = new GsonBuilder()
        		.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                        if (src.isNaN() || src.isInfinite()) {
                            return new JsonPrimitive(src.toString());
                        }
                        return new JsonPrimitive(src);
                    }
                })
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setExclusionStrategies(excludeStrategy)
                .create();
    }



    /**
     * 函数名称: parseData 函数描述: 将json字符串转换为map
     *
     * @param data
     * @return
     */
    public static Map<String, Object> parseData(String data) {
        Map<String, Object> map = gson.fromJson(data,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        return map;
    }

    /**
     * 函数名称: parseData 函数描述: 将json字符串转换为map
     *
     * @param data
     * @return
     */
    public static Map<String, String> parseDataStr(String data) {
        Map<String, String> map = gson.fromJson(data,
                new TypeToken<Map<String, String>>() {
                }.getType());
        return map;
    }


    /**
     * 函数名称: parseDataList 函数描述: 将json字符串转换为List<Map<String,String>>
     *
     * @param data
     * @return
     */
    public static List<Map<String, String>> parseDataList(String data){
    	List<Map<String,String>> listmap = gson.fromJson(data, new TypeToken<List<Map<String, String>>>() {
                }.getType());
    	return listmap;
    }



    /**
     * 函数名称: parseDataList 函数描述: 将json字符串转换为List<Map<String, Object>>
     *
     * @param data
     * @return
     */
    public static List<Map<String, Object>> parseObjList(String data){
    	List<Map<String,Object>> listmap = gson.fromJson(data, new TypeToken<List<Map<String, Object>>>() {
                }.getType());
    	return listmap;
    }



    /**
     * 简单Object to json
     *
     * @param obj
     * @return
     */
    public static String toJsonStr(Object obj) {
        return gson.toJson(obj);
    }


    /**
     * 复杂Object to json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
    	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(obj);
    }

    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Type type) {
        return gson.fromJson(str, type);
    }

    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Class<T> type) {
        return gson.fromJson(str, type);
    }


    /**
     * @author Administrator
     */
    private static class SetterExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes arg0) {
            boolean flag = false;
            if (arg0.getName().startsWith("_")) {
                flag = true;
            }
            if (!flag) {
                flag = arg0.getName().startsWith("set");
            }
            return flag;
        }

    }

    /**
     * json 转 map
     *
     * @param jsonStr
     *            要转换的json字符串
     * @return
     */
    public static Map<String, Object> JsonToMap(String jsonStr) {
        return JsonToMap(jsonStr, null);
    }
    /**
     * json 转 map
     *
     * @param jsonStr
     *            要转换的json字符串
     * @param result
     *            转换的结果放入位置
     * @return
     */
    public static Map<String, Object> JsonToMap(String jsonStr, Map<String, Object> result) {
        if (jsonStr == null) {
            return null;
        }
        if (result == null) {
            result = new HashMap<String, Object>();
        }
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(jsonStr);
        return JsonToMap(result, "▲▼◆", jsonElement);
    }

    private static Map<String, Object> JsonToMap(Map<String, Object> result, String key, JsonElement value) {
        // 如果key为null 直接报错
        if (key == null) {
            throw new RuntimeException("key值不能为null");
        }
        // 如果value为null,则直接put到map中
        if (value == null) {
            result.put(key, value);
        } else {
            // 如果value为基本数据类型，则放入到map中
            if (value.isJsonPrimitive()) {
                result.put(key, value.getAsString());  //
            } else if (value.isJsonObject()) {
                // 如果value为JsonObject数据类型，则遍历此JSONObject，进行递归调用本方法
                JsonObject jsonObject = value.getAsJsonObject();
                Iterator<Entry<String, JsonElement>> iterator = jsonObject.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, JsonElement> next = iterator.next();
                    result = JsonToMap(result, next.getKey(), next.getValue());
                }
            } else if (value.isJsonArray()) {
                // 如果value为JsonArray数据类型，则遍历此JsonArray，进行递归调用本方法
                JsonArray jsonArray = value.getAsJsonArray();
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                for (int i = 0, len = jsonArray.size(); i < len; i++) {
                    Map<String, Object> tempMap = new HashMap<String, Object>();
                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    Iterator<Entry<String, JsonElement>> iterator = jsonObject.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, JsonElement> next = iterator.next();
                        tempMap = JsonToMap(tempMap, next.getKey(), next.getValue());
                    }
                    list.add(tempMap);
                }
                result.put(key, list);
            }
        }
        // 返回最终结果
        return result;
    }

    public static void main(String[] args) {
    	String str = "{"
    	    	+"\"plateNo\":\"浙X16501\","
    	    	+"\"cloudParkId\":\"P330106001C\","
    	    	+"\"parkingName\":\"孔小二停车场\","
    	    	+"\"parkManageType\":\"2\","
    	    	+"\"parkIndexcode\":1111,"
    	    	+"\"parkEquipmentName\":\"order_source_gat_dahua\","
    	    	+"\"inTime\":\"2017-07-25 15:56:00\","
    	    	+"\"carType\":\"1\"}";

    	Map<String, Object> paramsMap = GsonUtils.parseData(str);
    	System.out.println(paramsMap.get("parkIndexcode"));
	}



}
