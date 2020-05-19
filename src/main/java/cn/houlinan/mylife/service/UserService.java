package cn.houlinan.mylife.service;

import cn.houlinan.mylife.DTO.UserVO;
import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.entity.GeLuoMiUser;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.GeLuoMiUserRepository;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.utils.*;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * DESC：用户service层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 20:48
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    Sid sid;

    @Autowired
    RedisOperator redisOperator;
    @Autowired
    GeLuoMiUserRepository geLuoMiUserRepository ;

    /**
     *DESC:注册使用
     *@param:  userName
     *@Param: password
     *@Param: email
     *@Param: mobile
     *@return:  cn.houlinan.mylife.entity.User
     *@author hou.linan
     *@date:
    */
    @Transactional
    public User addUser(String userId , String userName , String password , String email , Long mobile
//            , String openId
    ){


        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setPassword(password);


        return saveUser(user);
    }

    public User saveUser(User user){

        if(CMyString.isEmpty(user.getId()) || "0".equals(user.getId())) {
            user.setId(sid.nextShort());
            user.setHiddenPicsPath(UserConstant.USER_NOTDISPLAY_PICS_ROOT_PATH + user.getUserName()+ File.separator );
            user.setCrTime(new Date());
            user.setUpdateTime(new Date());
        }
        userRepository.save(user);

        return user ;
    }


    public User updateUser(User user) {
        String userName = user.getUserName();
        User currUser = userRepository.findUserByUserName(userName);
        UpdataObjectUtil.copyNullProperties(currUser, user);
        return userRepository.save(user);
    }

    public GeLuoMiUser geLuoMiUserLogin(GeLuoMiUser geLuoMiUser , HttpServletResponse res)throws Exception{



        User userByUserName1 = userRepository.findUserByUserName(geLuoMiUser.getUserName());
        userByUserName1.setPassword("");
        if(userByUserName1 == null) throw new Exception("处理用户数据失败，请联系管理员");

        //设置UserToken
        UserVO usersVO = setUserRedisSessionToken(userByUserName1);

        String userToken = usersVO.getUserToken() ;
        CookieUtils.writeCookie(res, UserConstant.USER_TOKEN_NAME,userToken);
        log.info("userVo = {}" ,usersVO);
        geLuoMiUser.setUserToken(userToken);
        geLuoMiUser.setPassword("");

        return geLuoMiUser ;
    }


    /*
     *DESC: 想redis中添加用户的token
     *@author hou.linan
     *@date:  2018/8/22 16:21
     *@param:  [user]
     *@return:  com.trs.wxnew.ResultVO.UsersVO
     */
    public UserVO setUserRedisSessionToken(User user){
        //将用户的信息添加到redis中
        String uniqueToken = UUID.randomUUID().toString();

        //此处使用 ‘ ： ’ 可以在redis中将数据分类保存
        redisOperator.set(UserConstant.SESSION_LOGIN_USER+":"+user.getId() , uniqueToken , 1000*60*30 );
        redisOperator.set(UserConstant.SESSION_LOGIN_USER+":"+uniqueToken , user.getId() , 1000*60*30 );
        //将用户的token放入session中


        UserVO usersVO = new UserVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserToken(uniqueToken);
        log.info("将用户【{}】 Token信息放入redis成功" , user.getUserName());
        return usersVO ;
    }

    public Object getUserDescByUserLoginType(int userLoginTyep , UserVO user ){
        if (user == null) return null ;
        switch (userLoginTyep){
            case UserConstant.USER_LOGIN_TYPE_GELUOMI:
                GeLuoMiUser geLuoMiUser = geLuoMiUserRepository.findGeLuoMiUserByUserName(user.getUserName());
                geLuoMiUser.setPassword("");
                geLuoMiUser.setUserToken(user.getUserToken());
                 return  geLuoMiUser;
            default:
                return user ;
        }
    }

}
