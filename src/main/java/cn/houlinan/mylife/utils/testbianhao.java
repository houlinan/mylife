package cn.houlinan.mylife.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/2/12
 * Time : 14:53
 */
public class testbianhao {

//    public static void main(String[] args) {
//
//
//        String str = "新建文稿用于导出【正文】新建文稿用于导出1【导语】新建文稿用于导出2【编后】新建文稿用于导出3【正文】新建文稿用于导出4【导语】新建文稿用于导出5【编后】新建文稿用于导出6\",";
//
////        String newStr = str.replaceAll("\\n", "");
//
//        String regex = "【.+?】.+?【";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(str);
//        List<String> strList = new ArrayList< >();
//        while (matcher.find()) {
////            String tocaijian ="【"  +matcher.group(1)+"】";
////            System.out.println(str.indexOf(tocaijian));\
//            String currStr = matcher.group(0);
//            if (!currStr.startsWith("【")){
//                currStr = "【" + currStr  ;
//            }
//            if(currStr.endsWith("【")){
//                currStr = currStr.substring(0 , currStr.length()-1);
//            }
//            System.err.println(currStr);
//        }
//
//    }


    public static void main(String[] args) {
        String str ="【编后】123123213";
        System.out.println(str.substring(4));
        System.out.println("【编后】".length());
    }
}
