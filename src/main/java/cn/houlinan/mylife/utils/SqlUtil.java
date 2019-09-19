package cn.houlinan.mylife.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlUtil {
    public static String KEY_SQL = "sql";
    public static String KEY_PRAMS = "prams";

    /**
     * @param channelSql in条件前的sql语句
     * @param sPrams     in (sPrams)
     * @return 返回map，sql语句的key为sql，参数的key为prams
     * @throws @Author：liujian 2018年8月2日 上午10:46:54
     * @Title: buildWhereWithIn 构造符合jpa规范的in条件查询sql
     * @Description: 构造符合jpa规范的in条件查询sql, 返回map，sql语句的key为sql，参数的key为prams
     */
    public static Map<String, Object> buildWhereWithIn(String channelSql, String sPrams) {
        String[] arrIds = sPrams.split(",");
        Map<String, Object> oPramsMap = new HashMap<String, Object>();
        StringBuffer sWhere = new StringBuffer(channelSql).append(" in (");
        for (int i = 0; i < arrIds.length; i++) {
            oPramsMap.put(String.valueOf(i + 1), arrIds[i]);
            sWhere.append(":").append(i + 1).append(",");
        }
        sWhere.replace(sWhere.length() - 1, sWhere.length(), ")");
        Map<String, Object> oMap = new HashMap<String, Object>();
        oMap.put(KEY_SQL, sWhere.toString());
        oMap.put(KEY_PRAMS, oPramsMap);
        return oMap;
    }

    /**
     * @param ids
     * @return
     * @throws @Author：liujian 2018年8月8日 下午4:53:13
     * @Title: 构造JPA进行IN查询时的List<Long>
     * @Description: TODO
     */
    public static List<Long> buildInIds(String ids) {
        List<Long> idsList = new ArrayList<>();
        String[] arrIds = ids.split(",");
        for (int i = 0; i < arrIds.length; i++) {
            idsList.add(Long.parseLong(arrIds[i]));
        }
        return idsList;
    }

    /**
     * Description: 构造in sql语句，超过1000个id单独拼接sql <BR>
     *
     * @param fieldName 字段名
     * @param ids       id集合
     * @return
     * @author liu.zhuan
     * @date 2018年9月4日 上午10:19:46
     * @version 1.0
     */
    public static <T> String buildSqlIn(String fieldName, List<T> ids) {
        StringBuilder sSqlIn = new StringBuilder(fieldName).append(" in (");
        if (ids.size() <= 1000) {
            sSqlIn.append(CMyString.join(ids, ",")).append(")");
            return sSqlIn.toString();
        }
        for (int i = 1; i <= ids.size(); i++) {
            T t = ids.get(i - 1);
            sSqlIn.append(t);
            if (i % 1000 == 0 && i < ids.size()) {
                sSqlIn.append(") and ").append(fieldName).append(" in (");
            } else {
                if (i == ids.size()) {
                    sSqlIn.append(")");
                } else {
                    sSqlIn.append(",");
                }
            }
        }
        return sSqlIn.toString();
    }

    public static void main(String[] args) {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 2001; i++) {
            int a = (i + 1);
            ids.add(Long.valueOf(a + ""));
        }
        System.out.println(buildSqlIn("ids", ids));
    }
}
