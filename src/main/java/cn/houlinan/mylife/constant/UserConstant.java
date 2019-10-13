package cn.houlinan.mylife.constant;

import java.io.File;

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
}
