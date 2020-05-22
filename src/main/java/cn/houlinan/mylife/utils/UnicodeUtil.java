package cn.houlinan.mylife.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/22
 * Time : 14:48
 */
public class UnicodeUtil {

    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch+"" );
        }
        return str;
    }

}
