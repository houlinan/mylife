package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.service.TeamService;
import cn.houlinan.mylife.utils.HHJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 22:28
 */
@RestController
@RequestMapping("/team")
@Slf4j
@Api(value = "小组相关API方法", tags = "小组相关Controller相关API")
public class TeamController {

    @Autowired
    TeamService teamService ;


    @ApiOperation(value = "小组注册", notes = "小组注册的接口")
    @PostMapping("/create")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamName", value = "小组名称", required = true,
                    dataType = "String", paramType = "form", defaultValue = "aaa小组"),
            @ApiImplicitParam(name = "teamPassword", value = "小组密码", required = true,
                    dataType = "String", paramType = "form", defaultValue = "123456"),
            @ApiImplicitParam(name = "teamEmail", value = "小组邮箱", required = false,
                    dataType = "String", paramType = "form", defaultValue = "houlinan@vip.qq.com"),
            @ApiImplicitParam(name = "isSendAllUser", value = "是否发送所有人", required =
                    false, dataType = "String", paramType = "form", defaultValue = "1"),
    })
    public HHJSONResult registUser(@RequestParam(name = "teamName") String teamName,
                                   @RequestParam(name = "teamPassword") String teamPassword,
                                   @RequestParam(name = "teamEmail" , required = false) String teamEmail,
                                   @RequestParam(name = "isSendAllUser", required = false) Integer isSendAllUser ) throws Exception {

        teamService.createTeam(null , teamName , teamPassword , teamEmail ,null ,isSendAllUser);



        return null ;

    }


}
