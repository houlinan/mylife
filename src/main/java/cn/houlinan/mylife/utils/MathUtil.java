package cn.houlinan.mylife.utils;

import java.math.BigDecimal;

/**
* @Description: 数值计算类
* @Author: liujian
* @Copyright: Copyright (c) 拓尔思信息技术股份有限公司
* @Date: 2019年06月19日 13:24:27
* @Version: 1.0
*/
public class MathUtil {
    /**
     * @Description:  格式化双精度数值为4位小数,末位四舍五入
     * @Param: d
     * @return: double
     * @Author: liujian
     * @Date: 2019年06月17日 17:57:06
     */
    private static double getDouble(double d){
        BigDecimal bg = new BigDecimal(d);
        return bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * @Description:  计算两个整数的百分比
     * @Param: a
     * @Param: b
     * @return: double
     * @Author: liujian
     * @Date: 2019年06月19日 10:31:29
     */
    public static double getPercent(int a,int b){
        if (b==0)
            return getDouble(Double.valueOf(String.valueOf(b)));
        return getDouble(Double.valueOf(String.valueOf(a))/b);
    }
    /**
     * @Description:  两个double数值相减，a - b
     * @Param: a
     * @Param: b
     * @return: double
     * @Author: liujian
     * @Date: 2019年06月19日 13:13:10
     */
    public static double sub(double a,double b){
        BigDecimal da = new BigDecimal(Double.toString(a));
        BigDecimal db = new BigDecimal(Double.toString(b));
        return da.subtract(db).doubleValue();
    }
    /**
     * @Description:  两个double数值相加，a + b
     * @Param: a
     * @Param: b
     * @return: double
     * @Author: liujian
     * @Date: 2019年06月19日 13:20:46
     */
    public static double sum(double a,double b){
        BigDecimal da = new BigDecimal(Double.toString(a));
        BigDecimal db = new BigDecimal(Double.toString(b));
        return da.add(db).doubleValue();
    }
}
