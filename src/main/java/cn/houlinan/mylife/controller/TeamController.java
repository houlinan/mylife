package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.DTO.TeamVO;
import cn.houlinan.mylife.entity.Team;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.TeamRepository;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


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

    @Autowired
    TeamRepository teamRepository ;

    @ApiOperation(value = "小组注册", notes = "小组注册的接口")
    @PostMapping("/create")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamName", value = "小组名称", required = true, dataType = "String", paramType="query", defaultValue = "aaa小组"),
            @ApiImplicitParam(name = "teamPassword", value = "小组密码", required = true, dataType = "String", paramType="query", defaultValue = "123456"),
            @ApiImplicitParam(name = "teamEmail", value = "小组邮箱", required = false, dataType = "String",paramType="query", defaultValue = "houlinan@vip.qq.com"),
            @ApiImplicitParam(name = "isSendAllUser", value = "是否发送所有人", required = false, dataType = "String", paramType="query", defaultValue = "1"),
            @ApiImplicitParam(name = "teamDesc", value = "小组描述", required = false, dataType = "String", paramType="query", defaultValue = "这里是一个帅气的描述")
    })
    @ResponseBody
    public HHJSONResult createTeam(
            TeamVO teamVO ,User user
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


        if(user.getTeam() != null || user.getTeamid() != null ) return HHJSONResult.errorMsg("请先取消自己的组织");

        User adminUser = userRepository.findUserByUserName(adminUserName) ;
        if(adminUser == null)  return HHJSONResult.errorMsg("没有找到【" + adminUserName + "】的用户");

        Team team = adminUser.getTeam() ;
        String teampassword = team.getTeamPassword() ;

        if(!teampassword.equals(teamPassword)) return HHJSONResult.errorMsg("小组密码错误");

        user.setTeam(team );
        user.setTeamid(team.getId());
        userRepository.save(user);

        //处理其他需要保存的用户数据
        teamService.otherSaveUser(user);


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

    @ApiOperation(value = "验证小组是否正确", notes = "验证小组是否正确\"")
    @GetMapping("/checkTeam")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamPassword", value = "当前用户密码", required = true, dataType = "String", paramType="query", defaultValue = "123456"),
            @ApiImplicitParam(name = "teamId", value = "小组id", required = true, dataType = "String", paramType="query", defaultValue = "1")
    })
    @ResponseBody
    public HHJSONResult checkTeam(String teamId , String teamPassword){
        if(CMyString.isEmpty(teamPassword) || CMyString.isEmpty(teamId )){
            return HHJSONResult.errorMsg("传入的小组信息为空");
        }
        Team teamByIdAndTeamPassword = teamRepository.findTeamByIdAndTeamPassword(teamId, teamPassword);
        if(teamByIdAndTeamPassword ==  null) return HHJSONResult.errorMsg("没有找到对应的小组信息");
        return HHJSONResult.ok(teamByIdAndTeamPassword.getTeamName());
    }


    @ApiOperation(value = "查询小组内其他成员", notes = "查询小组内其他成员")
    @GetMapping("/queryTeamUsers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "openId", required = true, dataType = "String", paramType="query", defaultValue = "123456")
          })
    @ResponseBody
    public HHJSONResult queryTeamUsers(User user){

        return HHJSONResult.ok(  userRepository.findUsersByTeamid(user.getTeamid()).stream().map(a -> a.getUserName()).collect(Collectors.toList()));
    }




    @ApiOperation(value = "查询小组内其他成员", notes = "查询小组内其他成员")
    @GetMapping("/queryTeamUsersToJson")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "openId", required = true, dataType = "String", paramType="query", defaultValue = "123456")
    })
    @ResponseBody
    public HHJSONResult queryTeamUsersToJson(User user){
        JSONArray reuslt = new JSONArray();
        userRepository.findUsersByTeamid(user.getTeamid()).forEach(a ->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName" , a.getUserName());
            reuslt.add(jsonObject);
        });
        return HHJSONResult.ok(  reuslt);
    }

}
