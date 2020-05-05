package cn.houlinan.mylife.service;

import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.UpdataObjectUtil;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

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


}
