package cn.houlinan.mylife.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: 媒立方稿费系统 <BR>
 * Description: 字符规则验证<BR>
 * TODO <BR>
 * Copyright: Copyright (c) 2004-2016 北京拓尔思信息技术股份有限公司 <BR>
 * Company: www.trs.com.cn <BR>
 *
 * @author wen.junhui
 * @version 1.0
 */
public class RegexUtil {
    private final static Logger logger = LoggerFactory.getLogger(RegexUtil.class);

    /**
     * SUBPICLEN : 截取图片名称后多少位
     */
    private final static Integer SUBPICLEN = 8;

    public static void main(String[] args) {
//        System.out.println("过滤中英文特殊字符: "+RegexUtil.stringFilter("中国~~!#$%%."));
//        System.out.println("是否包含中英文特殊字符: "+RegexUtil.isContainsSpecialChar("12"));
//        System.out.println("过滤html代码: "+RegexUtil.htmltoText("<JAVASCRIPT>12</JAVASCRIPT>DDDDD"));
//        System.out.println("判断中文字符: "+RegexUtil.isChineseChar("中国！"));
//        System.out.println("匹配汉字: "+RegexUtil.isChinese("中国！"));
//        System.out.println("判断英文字符: "+RegexUtil.isEnglish("abc!"));
//        System.out.println("判断合法字符: "+RegexUtil.isRightfulString("abc_-11AAA"));
//        System.out.println("邮政编码验证: "+RegexUtil.isZipCode("162406"));
//        System.out.println("身份证号码验证: "+RegexUtil.isIdCardNo("35052419880210133e"));
//        System.out.println("手机号码验证: "+RegexUtil.isMobile("18918611111"));
//        System.out.println("电话号码验证: "+RegexUtil.isPhone("8889333"));
//        System.out.println("电话号码验证: "+RegexUtil.isNumeric("888.9333"));
//        System.out.println("匹配密码: "+RegexUtil.isPwd("d888d_ddddd"));
//        System.out.println("匹配密码: "+RegexUtil.isUrl("http://baidu.com"));
//        System.out.println("验证字符: "+RegexUtil.stringCheck("中文aabc001_-"));
//        System.out.println(isEmail("416501600@qq.com"));
        //http://baidu.com www.baidu.com baidu.com
//        System.out.println(NumberUtils.toInt("-0000000002"));

        System.out.println(isHHssTime("23:23"));
    }

    public final static boolean isNull(Object[] objs) {
        if (objs == null || objs.length == 0) return true;
        return false;
    }

    public final static boolean isNull(Integer integer) {
        if (integer == null || integer == 0) return true;
        return false;
    }

    @SuppressWarnings("rawtypes")
    public final static boolean isNull(Collection collection) {
        if (collection == null || collection.size() == 0) return true;
        return false;
    }

    @SuppressWarnings("rawtypes")
    public final static boolean isNull(Map map) {
        if (map == null || map.size() == 0) return true;
        return false;
    }

    public final static boolean isNull(String str) {
        return str == null || "".equals(str.trim()) || "null".equals(str.toLowerCase());
    }


    public final static boolean isNull(Long longs) {
        if (longs == null || longs == 0) return true;
        return false;
    }

    public final static boolean isNotNull(Long longs) {
        return !isNull(longs);
    }

    public final static boolean isNotNull(String str) {
        return !isNull(str);
    }

    @SuppressWarnings("rawtypes")
    public final static boolean isNotNull(Collection collection) {
        return !isNull(collection);
    }

    @SuppressWarnings("rawtypes")
    public final static boolean isNotNull(Map map) {
        return !isNull(map);
    }

    public final static boolean isNotNull(Integer integer) {
        return !isNull(integer);
    }

    public final static boolean isNotNull(Object[] objs) {
        return !isNull(objs);
    }

    /**
     * 匹配URL地址
     *
     * @param str
     * @return
     * @author jiqinlin
     */
    public final static boolean isUrl(String str) {
        return match(str, "^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$");
    }

    /**
     * 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
     *
     * @param str
     * @return
     * @author jiqinlin
     */
    public final static boolean isPwd(String str) {
        return match(str, "^[a-zA-Z]\\w{6,12}$");
    }

    /**
     * 验证字符，只能包含中文、英文、数字、下划线等字符。
     *
     * @param str
     * @return
     * @author jiqinlin
     */
    public final static boolean stringCheck(String str) {
        return match(str, "^[a-zA-Z0-9\u4e00-\u9fa5-_]+$");
    }

    /**
     * 匹配Email地址
     *
     * @param str
     * @return
     * @author jiqinlin
     */
    public final static boolean isEmail(String str) {
        return match(str, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    /**
     * Description: 匹配非负整数（正整数+0） <BR>
     *
     * @param str
     * @return
     * @author wen.junhui
     * @date 2016年1月18日 下午10:21:18
     */

    public final static boolean isInteger(String str) {
        return match(str, "^[+]?\\d+$");
    }

    /**
     * 判断数值类型，包括整数和浮点数
     *
     * @param str
     * @return
     * @author jiqinlin
     */
    public final static boolean isNumeric(String str) {
        if (isFloat(str) || isInteger(str)) return true;
        return false;
    }

    /**
     * 只能输入数字
     *
     * @param str
     * @return
     * @author jiqinlin
     */
    public final static boolean isDigits(String str) {
        return match(str, "^[0-9]*$");
    }

    /**
     * 匹配正浮点数
     *
     * @param str
     * @return
     * @author jiqinlin
     */
    public final static boolean isFloat(String str) {
        return match(str, "^[-\\+]?\\d+(\\.\\d+)?$");
    }

    /**
     * 联系电话(手机/电话皆可)验证
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isTel(String text) {
        if (isMobile(text) || isPhone(text)) return true;
        return false;
    }

    /**
     * 电话号码验证
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isPhone(String text) {
        return match(text, "^(\\d{3,4}-?)?\\d{7,9}$");
    }

    /**
     * 手机号码验证
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isMobile(String text) {
        if (text.length() != 11) return false;
        return match(text, "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$");
    }

    /**
     * 身份证号码验证
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isIdCardNo(String text) {
        return match(text, "^(\\d{6})()?(\\d{4})(\\d{2})(\\d{2})(\\d{3})(\\w)$");
    }

    /**
     * 邮政编码验证
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isZipCode(String text) {
        return match(text, "^[0-9]{6}$");
    }

    /**
     * 判断整数num是否等于0
     *
     * @param num
     * @return
     * @author jiqinlin
     */
    public final static boolean isIntEqZero(int num) {
        return num == 0;
    }

    /**
     * 判断整数num是否大于0
     *
     * @param num
     * @return
     * @author jiqinlin
     */
    public final static boolean isIntGtZero(int num) {
        return num > 0;
    }

    /**
     * 判断整数num是否大于或等于0
     *
     * @param num
     * @return
     * @author jiqinlin
     */
    public final static boolean isIntGteZero(int num) {
        return num >= 0;
    }

    /**
     * 判断浮点数num是否等于0
     *
     * @param num 浮点数
     * @return
     * @author jiqinlin
     */
    public final static boolean isFloatEqZero(float num) {
        return num == 0f;
    }

    /**
     * 判断浮点数num是否大于0
     *
     * @param num 浮点数
     * @return
     * @author jiqinlin
     */
    public final static boolean isFloatGtZero(float num) {
        return num > 0f;
    }

    /**
     * 判断浮点数num是否大于或等于0
     *
     * @param num 浮点数
     * @return
     * @author jiqinlin
     */
    public final static boolean isFloatGteZero(float num) {
        return num >= 0f;
    }

    /**
     * 判断是否为合法字符(a-zA-Z0-9-_)
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isRightfulString(String text) {
        return match(text, "^[A-Za-z0-9_-]+$");
    }

    /**
     * 判断英文字符(a-zA-Z)
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isEnglish(String text) {
        return match(text, "^[A-Za-z]+$");
    }

    /**
     * 判断中文字符(包括汉字和符号)
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isChineseChar(String text) {
        return match(text, "^[\u0391-\uFFE5]+$");
    }

    /**
     * 匹配汉字
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isChinese(String text) {
        return match(text, "^[\u4e00-\u9fa5]+$");
    }

    /**
     * 是否包含中英文特殊字符，除英文"-_"字符外
     *
     * @param
     * @return
     */
    public static boolean isContainsSpecialChar(String text) {
        if (StringUtils.isBlank(text)) return false;
        String[] chars = {"[", "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "=", "|", "{", "}", "'",
                ":", ";", "'", ",", "[", "]", ".", "<", ">", "/", "?", "~", "！", "@", "#", "￥", "%", "…", "&", "*", "（", "）",
                "—", "+", "|", "{", "}", "【", "】", "‘", "；", "：", "”", "“", "’", "。", "，", "、", "？", "]"};
        for (String ch : chars) {
            if (text.contains(ch)) return true;
        }
        return false;
    }

    /**
     * 过滤中英文特殊字符，除英文"-_"字符外
     *
     * @param text
     * @return
     */
    public static String stringFilter(String text) {
        String regExpr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regExpr);
        Matcher m = p.matcher(text);
        return m.replaceAll("").trim();
    }

    /**
     * 过滤html代码
     *
     * @param inputString 含html标签的字符串
     * @return
     */
    public static String htmlFilter(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_ba;
        Matcher m_ba;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String patternStr = "\\s+";

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll(""); // 过滤空格

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        return textStr;// 返回文本字符串
    }

    /**
     * 正则表达式匹配
     *
     * @param text 待匹配的文本
     * @param reg  正则表达式
     * @return
     * @author jiqinlin
     */
    private final static boolean match(String text, String reg) {
        if (StringUtils.isBlank(text) || StringUtils.isBlank(reg))
            return false;
        return Pattern.compile(reg).matcher(text).matches();
    }

    /**
     * @param htmlContent html正文内容
     * @param domain      添加的域名地址
     * @return
     * @throws
     * @Title: 转换html中img标签的src属性值
     * @Description:
     * @Author：liujian 2018年8月30日 上午9:33:35
     */
    public static String repairContent(String htmlContent, String domain) {
        if (CMyString.isEmpty(htmlContent) || CMyString.isEmpty(domain)) {
            return htmlContent;
        }
        String chrPathSeparator = "/";
        String patternStr = "<img\\s*([^>]*)\\s*src=\\\"(.*?)\\\"\\s*([^>]*)>";
        Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(htmlContent);
        String result = htmlContent;
        while (matcher.find()) {
            String src = matcher.group(2);
            StringBuffer replaceSrc = new StringBuffer();
            if (src.length() > 10 && !src.startsWith("http://") && !src.startsWith("https://")) {
                replaceSrc.append("=\"").append(domain).append(chrPathSeparator)
                        .append(src.substring(0, 8)).append(chrPathSeparator)
                        .append(src.substring(0, 10)).append(chrPathSeparator).append(src).append("\"");
            }
            result = result.replaceAll("=\"" + src + "\"", replaceSrc.toString());
        }
        return result;
    }

    /**
     * @param text
     * @return
     * @throws
     * @Title: isHHssTime
     * @Description: 校验小时和分钟时间格式
     * @Author：liujian 2018年9月13日 下午3:10:30
     */
    public final static boolean isHHssTime(String text) {
        Pattern p = Pattern.compile("^([01][0-9]|2[0-3]):([0-5][0-9])$");
        Matcher m = p.matcher(text);
        return m.find();
    }
}
