package cn.houlinan.mylife.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;


public class MapUtil {

    public static Long getLongFromMap(Map<String, Object> oMap, String key) {
        //设置租户id默认值以适配MLF无租户场景
        Long defalutValue = 0L;
        //租户ID的Key
        String tenantId = "tenantId";

        if (null == oMap.get(key)) {
            return tenantId.equalsIgnoreCase(key) ? defalutValue : null;
        }
        if (CMyString.isEmpty(String.valueOf(oMap.get(key)))) {
            return tenantId.equalsIgnoreCase(key) ? defalutValue : null;
        }
        return Long.parseLong(String.valueOf(oMap.get(key)));
    }

    public static Integer getIntegerFromMap(Map<String, Object> oMap, String key) {
        if (null == oMap.get(key)) {
            return null;
        }
        if (CMyString.isEmpty(String.valueOf(oMap.get(key)))) {
            return null;
        }
        return Integer.parseInt(String.valueOf(oMap.get(key)));
    }

    public static String getStringFromMap(Map<String, Object> oMap, String key) {
        if (null == oMap.get(key.toUpperCase())) {
            return null;
        }
        return String.valueOf(oMap.get(key));
    }

    public static Date getDateFromMap(Map<String, Object> oMap, String key) {
        Date oDate = DateUtil.now();
        if (null == oMap.get(key)) {
            return null;
        }
        if (CMyString.isEmpty(String.valueOf(oMap.get(key)))) {
            return null;
        }
        try {
            oDate = DateUtil.parseDate(String.valueOf(oMap.get(key)), "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oDate;
    }

    public static TreeSet longIdsToTreeSet(List<Long> ids){
        TreeSet<Long> returl = new TreeSet<>( );
        ids.stream().forEach(e -> returl.add(e));
        return returl ;
    }



}
