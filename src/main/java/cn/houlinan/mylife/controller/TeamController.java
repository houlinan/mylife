package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.DTO.TeamVO;
import cn.houlinan.mylife.entity.Team;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.service.TeamService;
import cn.houlinan.mylife.utils.BeanValidator;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 22:28

 */
@Controller
@RequestMapping("/team")
@Slf4j
@Api(value = "小组相关API方法", tags = "小组相关Controller相关API")
public class TeamController {

    @Autowired
    TeamService teamService ;

    @Autowired
    UserRepository userRepository ;

    @ApiOperation(value = "小组注册", notes = "小组注册的接口")
    @PostMapping("/create")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamName", value = "小组名称", required = true, dataType = "String", paramType="query", defaultValue = "aaa小组"),
            @ApiImplicitParam(name = "teamPassword", value = "小组密码", required = true, dataType = "String", paramType="query", defaultValue = "123456"),
            @ApiImplicitParam(name = "teamEmail", value = "小组邮箱", required = false, dataType = "String",paramType="query", defaultValue = "houlinan@vip.qq.com"),
            @ApiImplicitParam(name = "isSendAllUser", value = "是否发送所有人", required = false, dataType = "String", paramType="query", defaultValue = "1"),
    })
    @ResponseBody
    public HHJSONResult createTeam(
                    TeamVO teamVO , User user
//            @RequestParam(name = "teamName",required = false)  String teamName,
//                                   @RequestParam(name = "teamPassword",required = false ) @NotBlank(message = "小组密码必传") String teamPassword,
//                                   @RequestParam(name = "teamEmail" , required = false) String teamEmail,
//                                   @RequestParam(name = "isSendAllUser", required = false) Integer isSendAllUser
    ) throws Exception {

        BeanValidator.check(teamVO);
        Team team = new Team( );
        BeanUtils.copyProperties(teamVO, team);
        teamService.createTeam(team ,user);
        return HHJSONResult.ok(team) ;

    }


    @ApiOperation(value = "用户绑定组织", notes = "用户绑定组织")
    @PostMapping("/userbindTeam")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminUserName", value = "超管用户名称", required = true, dataType = "String", paramType="query", defaultValue = "admin"),
            @ApiImplicitParam(name = "teamPassword", value = "小组密码", required = true, dataType = "String", paramType="query", defaultValue = "123")
    })
    public HHJSONResult userBindTeam(@RequestParam(name = "adminUserName")String adminUserName ,
                                     @RequestParam(name = "teamPassword" ,required = false) String teamPassword ,
                                     User user  ){

        if(user.getTeam() != null || !CMyString.isEmpty(user.getTeamid()) ) return HHJSONResult.errorMsg("请先取消自己的组织");

        User adminUser = userRepository.findUserByUserName(adminUserName) ;
        if(adminUser == null)  return HHJSONResult.errorMsg("没有找到【" + adminUserName + "】的用户");

        Team team = adminUser.getTeam() ;
        String teampassword = team.getTeamPassword() ;

        if(!teampassword.equals(teamPassword)) return HHJSONResult.errorMsg("小组密码错误");

        user.setTeam(team );
        user.setTeamid(team.getId());
        userRepository.save(user);


        return HHJSONResult.ok(user) ;
    }


    @ApiOperation(value = "用户取消组织", notes = "用户取消组织")
    @PostMapping("/userCancelTeam")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPassword", value = "当前用户密码", required = true, dataType = "String", paramType="query", defaultValue = "admin")
    })
    public HHJSONResult userCancelTeam(@RequestParam(name = "userPassword")String userPassword ,
                                     User user  ){

        String password = user.getPassword() ;
        if(!password.equals(userPassword)) return HHJSONResult.errorMsg("您的密码错误。请重试");

        user.setTeam(null);
        user.setTeamid(null);
        userRepository.save(user);

        return HHJSONResult.ok(user) ;
    }


}
