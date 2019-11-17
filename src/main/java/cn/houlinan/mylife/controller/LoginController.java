package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.DTO.UserVO;
import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.service.UserService;
import cn.houlinan.mylife.utils.CookieUtils;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.MD5Utils;
import cn.houlinan.mylife.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 23:30
 */
@RestController
@Slf4j
@RequestMapping("/login")
@Api(value = "登录相关API方法", tags = "登录相关Controller相关API")
public class LoginController {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    UserService userService;

    @Autowired
    RedisOperator redisOperator;

    /**
     * DESC:用户登陆
     *
     * @author hou.linan
     * @date: 2018/8/5 12:28
     * @param: [user]
     * @return: com.trs.wxnew.utils.WXJSONResult
     */
    @RequestMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, dataType = "String", paramType = "query", defaultValue = "admin"),
            @ApiImplicitParam(name = "passWord", value = "用户密码", required = true, dataType = "String", paramType = "query", defaultValue = "admin"),

    })
    @ApiOperation(value = "用户登陆", notes = "用户登陆接口")
    public HHJSONResult login(@RequestParam(value = "userName",required = false) String userName, @RequestParam(value = "passWord" , required = false ) String passWord ,
                              HttpServletResponse res
    ) throws Exception {
//        String userName = user.getUserName();
//        String passWord = user.getPassWord() ;
        log.info("用户尝试登陆， 用户名为：{} ， 用户的密码为：{}", userName, passWord);

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            return HHJSONResult.errorMsg("用户传入的用户名或者密码为空");
        }

        //用户不存在？
        if (userRepository.findUserByUserName(userName) == null) {
            return HHJSONResult.errorMsg("用户名或密码错误");
        }
        User currUser = userRepository.findUserByUserName(userName);


        // 判断密码
        if (!currUser.getPassword().equals(passWord)) {
            return HHJSONResult.errorMsg("用户名或密码错误");
        }
        currUser.setLastLoginTime(new Date());
        userService.updateUser(currUser);

        currUser.setPassword("");

        //设置UserToken
        UserVO usersVO = setUserRedisSessionToken(currUser);

        String userToken = usersVO.getUserToken() ;
        CookieUtils.writeCookie(res,UserConstant.USER_TOKEN_NAME,userToken);


        log.info("用户【{}】登陆成功，已经跳转到响应页面", userName);
        return HHJSONResult.ok(usersVO);
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
}
