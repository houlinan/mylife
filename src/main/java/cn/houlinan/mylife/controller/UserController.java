package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.service.UserService;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 19:48
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "用户相关API方法", tags = "用户相关Controller相关API")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService ;

    /**
     * DESC:用户注册
     *
     * @author hou.linan
     * @date: 2018/8/5 12:28
     * @param: [user]
     * @return: com.trs.wxnew.utils.WXJSONResult
     */
    @ApiOperation(value = "用户注册", notes = "用户注册的接口")
    @PostMapping("/regist")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户登陆名称", required = false, dataType = "String", paramType = "query", defaultValue = "admin"),
            @ApiImplicitParam(name = "passWord", value = "用户密码", required = false, dataType = "String", paramType = "query", defaultValue = "admim"),
            @ApiImplicitParam(name = "email", value = "用户邮箱", required = false, dataType = "String", paramType = "query", defaultValue = "houlinan@vip.qq.com"),
            @ApiImplicitParam(name = "mobile", value = "用户手机", required = false, dataType = "String", paramType = "query", defaultValue = "17625997779"),
    })
    public HHJSONResult registUser(@RequestParam(name = "userName" ,required = false) String userName,
                                   @RequestParam(name = "passWord" ,required = false) String passWord,
                                   @RequestParam(name = "email", required = false) String email,
                                   @RequestParam(name = "mobile", required = false) Long mobile
                                   ) throws Exception {

        if(CMyString.isEmpty(userName)) return HHJSONResult.errorMsg("请输入用户名称");
        if(CMyString.isEmpty(passWord)) return HHJSONResult.errorMsg("请输入用户密码");

        User user = userRepository.findUserByUserName(userName);
        if(user != null )  return HHJSONResult.errorMsg("用户名已经存在");

        User userResult = userService.addUser(null , userName ,passWord,email,mobile);

        return HHJSONResult.ok(userResult);
    }


}
