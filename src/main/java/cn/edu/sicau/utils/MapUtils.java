package cn.edu.sicau.utils;

import cn.edu.sicau.domain.News;

import java.util.*;

public class MapUtils {

    /**
     *
     * Map转String
     * @param map
     * @return
     */
    public static String getMapToString(Map<String,Object> map){
        Set<String> keySet = map.keySet();
        //将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        //给数组排序(升序)
        Arrays.sort(keyArray);
        //因为String拼接效率会很低的，所以转用StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
            }
            if(i != keyArray.length-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     *
     * String转map
     * @param str
     * @return
     */
    public static Map<String,Object> getStringToMap(String str){
        //根据逗号截取字符串数组
        String[] str1 = str.split(",");
        //创建Map对象
        Map<String,Object> map = new HashMap<>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            String[] str2 = str1[i].split(":");
            //str2[0]为KEY,str2[1]为值
            map.put(str2[0],str2[1]);
        }
        return map;
    }


    /**
     * 使用 Map按value进行排序
     * @param oriMap
     * @return
     */

    public static Map<News, Double> sortMapByValue(Map<News, Double> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<News, Double> sortedMap = new LinkedHashMap<News, Double>();
        List<Map.Entry<News, Double>> entryList = new ArrayList<Map.Entry<News, Double>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<News, Double>> iter = entryList.iterator();
        Map.Entry<News, Double> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
}

class MapValueComparator implements Comparator<Map.Entry<News, Double>> {

    @Override
    public int compare(Map.Entry<News, Double> me1, Map.Entry<News, Double> me2) {

        return me2.getValue().compareTo(me1.getValue());
    }
}
