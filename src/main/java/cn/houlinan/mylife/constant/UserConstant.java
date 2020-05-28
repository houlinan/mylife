package cn.houlinan.mylife.constant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DESC：用户常量类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/13
 * Time : 5:30
 */
public class UserConstant {

    /**
     * session中用户的标识
     * */
    public static final String SESSION_LOGIN_USER = "SESSION_USER_NAME";

    public static final String USER_NOTDISPLAY_PICS_ROOT_PATH = "C:" + File.separator + File.separator + "mylifeDatas" + File.separator + "userNotDisplayPics" +File.separator ;

    public static final String USER_TOKEN_NAME = "myLifeUserLoginToken";

    public static final int USER_LOGIN_TYPE_GELUOMI = 1 ;

    public static  final  int USER_TYPE_ADMIN = 9 ;

    public static  final  int USER_TYPE_TEAMADMIN = 1 ;

    public static final int USER_TYPE_DEFAULT = 0 ;

    public static Map<String , Integer> getUserTypeMap(){
        Map<String , Integer > result = new LinkedHashMap<>( );
        result.put("普通用户" ,USER_TYPE_DEFAULT ) ;
        result.put("小组管理员" ,USER_TYPE_TEAMADMIN ) ;
        result.put("超级管理员" ,USER_TYPE_ADMIN ) ;

        return result ;
    }


}
