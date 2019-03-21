package cn.edu.sicau.utils;

import java.util.Map;

public class KeywordUtils {

    final static int count=1000;//用户兴趣关键字的最大条数
    final static double x=0.7;//衰减系数

    /**
     * 将新闻关键字添加到用户关键字字符串中
     * @param userkeyword 用户关键字字符串
     * @param newskeyword 新闻关键字字符串
     * @return
     */
    public static String addKeywords(String userkeyword,String newskeyword){

        //将用户和新闻的关键字转换成Map
        Map<String, Object> userMap=MapUtils.getStringToMap(userkeyword);
        Map<String, Object> newsMap=MapUtils.getStringToMap(newskeyword);

        //遍历新闻关键字map
        for(Map.Entry<String,Object> entry:newsMap.entrySet()){
            if(userMap.get(entry.getKey())==null){
                //如果新闻关键字不包含在用户关键字中，添加进来
                userMap.put(entry.getKey(),entry.getValue());
            }else{
                //如果新闻关键字包含在用户关键字中，权重相加
                Double d=(Double) entry.getValue()+(Double) userMap.get(entry.getKey());
                userMap.put(entry.getKey(),d.toString());
            }
        }

        return MapUtils.getMapToString(userMap);
    }


    public static String keywordDamping(String userKeyword){
        //用户关键字转换成map
        Map<String, Object> userMap= MapUtils.getStringToMap(userKeyword);
        //遍历用户关键字
        for(Map.Entry<String,Object> entry:userMap.entrySet()){
            Double d= x*(Double)entry.getValue();
            userMap.put(entry.getKey(),d);
        }
        return MapUtils.getMapToString(userMap);
    }
}
