package cn.houlinan.mylife.constant;

import java.io.File;
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
    public static final String RESET_USER_ERROR_TIME = "RESET_USER_ERROR_TIME";

    public static final String USER_NOTDISPLAY_PICS_ROOT_PATH = "C:" + File.separator + File.separator + "mylifeDatas" + File.separator + "userNotDisplayPics" +File.separator ;

    public static final String USER_TOKEN_NAME = "myLifeUserLoginToken";

    public static final int USER_LOGIN_TYPE_GELUOMI = 1 ;


    public static Map<String , Integer> getUserTypeMap(){
        Map<String , Integer > result = new LinkedHashMap<>( );
        result.put( UserTypeEnum.USER_TYPE_DEFAULT.getName()  , UserTypeEnum.USER_TYPE_DEFAULT.getValue() ) ;
        result.put(UserTypeEnum.USER_TYPE_TEAMADMIN.getName() , UserTypeEnum.USER_TYPE_TEAMADMIN.getValue()) ;
        result.put(UserTypeEnum.USER_TYPE_ADMIN.getName() , UserTypeEnum.USER_TYPE_ADMIN.getValue()) ;

        return result ;
    }


}
