package cn.houlinan.mylife.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/11/19
 * Time : 17:50
 */
public class GetHtmlContentText {



    public static void main(String[] args) {

//        String html = "【龌龊！江都一男子偷拍女子如厕，被拘留七天！【扬帆】】近日，扬州市江都区大桥镇某企业的简易厕所里，有一只“黑手”越过了这道墙，做出了偷拍女士如厕的龌龊事！目前，涉事男子张某因为涉嫌侵犯他人隐私，被江都警方处以治安拘留7日、罚款500元的处罚。。网页链接（分享自@扬帆手机频道）";
//                System.out.println(extractMessage(Html2Text(html)));

        String a = "1231312" ;

        String[] split = a.split(",");

        System.out.println("11 == " + Arrays.asList(split));
    }


    /**
     * 提取中括号中内容，忽略中括号中的中括号
     * @param msg
     * @return
     */
    public static List<String> extractMessage(String msg) {

        List<String> list = new ArrayList<String>();
        int start = 0;
        int startFlag = 0;
        int endFlag = 0;
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == '【') {
                startFlag++;
                if (startFlag == endFlag + 1) {
                    start = i;
                }
            } else if (msg.charAt(i) == '】') {
                endFlag++;
                if (endFlag == startFlag) {
                    list.add(msg.substring(start + 1, i));
                }
            }
        }
        return list;
    }


    //从html中提取纯文本
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {System.err.println("Html2Text: " + e.getMessage()); }
        //剔除空格行
        textStr=textStr.replaceAll("[ ]+", " ");
        textStr=textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        return textStr;// 返回文本字符串
    }

}
