package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.DTO.UserVO;
import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.entity.GeLuoMiUser;
import cn.houlinan.mylife.entity.Team;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.GeLuoMiUserRepository;
import cn.houlinan.mylife.entity.primary.repository.TeamRepository;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.service.GeLuoMiUserService;
import cn.houlinan.mylife.service.UserService;
import cn.houlinan.mylife.utils.*;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import cn.hutool.core.thread.ThreadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    GeLuoMiUserRepository geLuoMiUserRepository ;

    @Autowired
    TeamRepository teamRepository ;

    @Autowired
    UserService userService ;

    @ResponseBody
    @PostMapping("/createUser")
    @ApiOperation(value = "创建格洛米用户", notes = "创建格洛米用户接口")
    public HHJSONResult createUser(GeLuoMiUser geLuoMiUser ,
                                   @RequestParam(name = "teamid" , required = false)String teamid ,
                                    @RequestParam(name = "teamPassword", required = false)String teamPassword ,
                                   HttpServletResponse res)throws Exception{

        BeanValidator.check(geLuoMiUser);

        User userByUserName = userRepository.findUserByUserName(geLuoMiUser.getUserName());
        if(userByUserName != null) return HHJSONResult.errorMsg("用户【" + userByUserName.getUserName() + "】已经存在");
        GeLuoMiUser geLuoMiUser1 = geLuoMiUserService.saveUser(geLuoMiUser);
        userByUserName = userRepository.findUserByUserName(geLuoMiUser.getUserName());
        log.info("传入的teamid = 【{}】 传入的teamPasswor=【{}】" ,teamid ,teamPassword );
        if(!CMyString.isEmpty(teamid) && !CMyString.isEmpty(teamPassword)){
            Team teamByIdAndTeamPassword = teamRepository.findTeamByIdAndTeamPassword(teamid, teamPassword);
            if(teamByIdAndTeamPassword != null ){
                geLuoMiUser1.setTeam(teamByIdAndTeamPassword);
                geLuoMiUser1.setTeamid(teamByIdAndTeamPassword.getId());
                geLuoMiUserRepository.save(geLuoMiUser1);
                userByUserName.setTeamid(teamByIdAndTeamPassword.getId());
                userByUserName.setTeam(teamByIdAndTeamPassword);
                userRepository.save(userByUserName);
            }else{
                log.info("没有找到{}的小组信息，用户{}绑定失败" , teamid ,geLuoMiUser.getUserName() );
            }
        }
        userService.synSendUserInfoToWechat(geLuoMiUser);

        return HHJSONResult.ok(userService.geLuoMiUserLogin(geLuoMiUser , res ));
    }

    @GetMapping("/findGeluomiUserByOpenIdAndLogin")
    @ApiOperation(value = "通过openId获取格洛米用户，如果存在就登录", notes = "通过openId获取格洛米用户，如果存在就登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "openId", dataType = "String", paramType = "query", defaultValue = "oRnZI43JJpc1VUNYrjnR2uuDi8eA")
    })
    public HHJSONResult findGeluomiUserByOpenIdAndLogin(
            @RequestParam(name = "openId", required = false) String openId , HttpServletResponse res
    ) throws Exception {

        if(CMyString.isEmpty(openId)) return HHJSONResult.errorMsg("获取获取到相关的用户信息");

        GeLuoMiUser geLuoMiUserByOpenId = geLuoMiUserRepository.findGeLuoMiUserByOpenId(openId);
        if(geLuoMiUserByOpenId == null)  return HHJSONResult.errorMsg("获取获取到相关的用户信息");

        return HHJSONResult.ok(userService.geLuoMiUserLogin(geLuoMiUserByOpenId , res ));
    }
}
