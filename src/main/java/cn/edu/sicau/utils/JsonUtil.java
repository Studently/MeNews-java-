package cn.edu.sicau.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonUtil {

    //将字符串解析为json数据
    public static Map<String, String> toMap(String jsonString){

        Map<String, String> result;
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            result = new HashMap<String, String>();
            Iterator<?> iterator = jsonObject.keys();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {

                key = (String) iterator.next();
                value = jsonObject.getString(key);
                result.put(key, value);

            }
            return result;
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 将map数据封装成json字符串
     * @param map
     * @return
     */
    public static String toJson(Map<String,Object> map){
        JSONObject mapJson = new JSONObject(map); // 传入Map类型
        return mapJson.toString();
    }


}
