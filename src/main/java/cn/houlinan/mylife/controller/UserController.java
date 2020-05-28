package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.entity.GeLuoMiUser;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.GeLuoMiUserRepository;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.service.UserService;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

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
    UserService userService;

    @Autowired
    GeLuoMiUserRepository geLuoMiUserRepository ;
    /**
     * DESC:用户注册
     *
     * @author hou.linan
     * @date: 2018/8/5 12:28
     * @param: [user]
     * @return: com.trs.wxnew.utils.WXJSONResult
     */
    @ApiOperation(value = "用户注册", notes = "用户注册的接口")
    @RequestMapping("/regist")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户登陆名称", required = false, dataType = "String", paramType = "query", defaultValue = "admin"),
            @ApiImplicitParam(name = "passWord", value = "用户密码", required = false, dataType = "String", paramType = "query", defaultValue = "admin"),
            @ApiImplicitParam(name = "email", value = "用户邮箱", required = false, dataType = "String", paramType = "query", defaultValue = "houlinan@vip.qq.com"),
            @ApiImplicitParam(name = "mobile", value = "用户手机", required = false, dataType = "String", paramType = "query", defaultValue = "17625997779"),
    })
    public HHJSONResult registUser(@RequestParam(name = "userName", required = false) String userName,
                                   @RequestParam(name = "passWord", required = false) String passWord,
                                   @RequestParam(name = "email", required = false) String email,
                                   @RequestParam(name = "mobile", required = false) Long mobile
//                                  , @RequestParam(name = "openId", required = false) String openId
    ) throws Exception {

        if (CMyString.isEmpty(userName)) return HHJSONResult.errorMsg("请输入用户名称");
        if (CMyString.isEmpty(passWord)) return HHJSONResult.errorMsg("请输入用户密码");

        User user = userRepository.findUserByUserName(userName);
        if (user != null) return HHJSONResult.errorMsg("用户名已经存在");

        User userResult = userService.addUser(null, userName, passWord, email, mobile);

        return HHJSONResult.ok(userResult);
    }

    @ApiOperation(value = "验证用户名称是否可用", notes = "验证用户名称是否可用")
    @RequestMapping("/checkUserNameHasUse")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户登陆名称", required = false, dataType = "String", paramType = "query", defaultValue = "admin"),
    })
    public HHJSONResult checkUserNameHasUse(String userName) {
        User userByUserName = userRepository.findUserByUserName(userName);
        if (userByUserName != null) return HHJSONResult.errorMsg("该用户名称已经被使用");
        return HHJSONResult.ok();
    }


    @ApiOperation(value = "获取用户权限列表", notes = "获取用户权限列表")
    @RequestMapping("/getUserTypes")
    public HHJSONResult getUserTypes() {
        JSONArray result = new JSONArray() ;
        Map<String, Integer> userTypeMap = UserConstant.getUserTypeMap();
        userTypeMap.forEach((k , v) -> {
            JSONObject jsonObject = new JSONObject( );
            jsonObject.put("desc" , k);
            jsonObject.put("value" , v);
            result.add(jsonObject);
        });
        return HHJSONResult.ok(result);
    }



    @ApiOperation(value = "设置用户权限", notes = "设置用户权限")
    @GetMapping("/setUserType")
    public HHJSONResult setUserType(
            @RequestParam(name = "userName" , required = false )String userName ,
            @RequestParam(name = "userType" ,required = false )int userType
    ) {
        User userByUserName = userRepository.findUserByUserName(userName);
        if(userByUserName == null) return HHJSONResult.errorMsg(StrUtil.format("没有找到【{}】的用户信息", userName));

        userByUserName.setUserType(userType);
        userRepository.save(userByUserName);

        GeLuoMiUser geLuoMiUserByUserName = geLuoMiUserRepository.findGeLuoMiUserByUserName(userName);
        if(geLuoMiUserByUserName != null){
            geLuoMiUserByUserName.setUserType(userType);
            geLuoMiUserRepository.save(geLuoMiUserByUserName);
        }

        return HHJSONResult.ok();
    }




}
