package cn.houlinan.mylife.service;

import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.entity.GeLuoMiUser;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.GeLuoMiUserRepository;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/1
 * Time : 22:49
 */
@Service
public class GeLuoMiUserService {

    @Autowired
    Sid sid ;

    @Autowired
    GeLuoMiUserRepository geLuoMiUserRepository;

    @Autowired
    UserRepository userRepository ;


    public GeLuoMiUser saveUser(GeLuoMiUser geLuoMiUser){


        String id = geLuoMiUser.getId();

        if(CMyString.isEmpty(id))
            geLuoMiUser.setId(sid.nextShort());

        if("admin".equals(geLuoMiUser.getUserName()))geLuoMiUser.setUserType(UserConstant.USER_TYPE_ADMIN);

        geLuoMiUserRepository.save(geLuoMiUser);

        User user = new User( );

        BeanUtils.copyProperties(geLuoMiUser , user);
        if("admin".equals(geLuoMiUser.getUserName())) user.setUserType(UserConstant.USER_TYPE_ADMIN);
        userRepository.save(user);

        return geLuoMiUser ;
    }
}
