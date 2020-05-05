package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.entity.GeLuoMiUser;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.GeLuoMiUserRepository;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.service.GeLuoMiUserService;
import cn.houlinan.mylife.utils.BeanValidator;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/1
 * Time : 21:07
 */

@RequestMapping("/geluomi/user")
@RestController
@Slf4j
@Api(value = "格洛米用户controller", tags = "格洛米用户controller")
public class GeLuoMiUserController {

    @Autowired
    GeLuoMiUserService geLuoMiUserService ;

    @Autowired
    UserRepository userRepository ;

    @ResponseBody
    @PostMapping("/createUser")
    @ApiOperation(value = "创建格洛米用户", notes = "创建格洛米用户接口")
    public HHJSONResult createUser(GeLuoMiUser geLuoMiUser){

        BeanValidator.check(geLuoMiUser);

        User userByUserName = userRepository.findUserByUserName(geLuoMiUser.getUserName());
        if(userByUserName != null) return HHJSONResult.errorMsg("用户【" + userByUserName.getUserName() + "】已经存在");

        return HHJSONResult.ok(geLuoMiUserService.saveUser(geLuoMiUser));
    }




}
