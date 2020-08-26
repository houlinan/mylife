package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.constant.ConfigConstant;
import cn.houlinan.mylife.entity.LinuxCommand;
import cn.houlinan.mylife.entity.primary.repository.LinuxCommandRepository;
import cn.houlinan.mylife.service.common.PrimaryBaseService;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @className :LinuxController
 * @DESC :linux 相关controller
 * @Author :hou.linan
 * @date :2020/8/26 15:11
 */
@RequestMapping("/linux")
@Controller
@Slf4j
@Api(value = "linux 相关controller", tags = "linux 相关controller")
public class LinuxController {

    @Autowired
    LinuxCommandRepository linuxCommandRepository ;

    @Autowired
    ConfigConstant configConstant ;

    @Autowired
    PrimaryBaseService primaryBaseService;

    @PostMapping("/addCommand")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commandTitle", value = "操作标题", required = true, dataType = "String", paramType = "query", defaultValue = "查询端口使用"),
            @ApiImplicitParam(name = "commandDesc", value = "操作描述/备注", required = true, dataType = "String", paramType = "query", defaultValue = "查询端口使用"),
            @ApiImplicitParam(name = "commandValue", value = "操作命令行", required = true, dataType = "String", paramType = "query", defaultValue = "ss -ntl"),
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, dataType = "String", paramType = "query", defaultValue = "houlinan")
    })
    @ApiOperation(value = "添加一个命令", notes = "添加一个命令")
    @ResponseBody
    public HHJSONResult addCommand(
            @RequestParam(value = "commandTitle" ,  required = false )String commandTitle  ,
            @RequestParam(value = "commandDesc"  ,  required = false)String commandDesc  ,
            @RequestParam(value = "commandValue" ,  required = false)String commandValue,
            @RequestParam(value = "userName" ,  required = false)String userName
    )throws Exception{

        if(!configConstant.getAllowAddLinuxCommandUserNameList().contains(userName)) return HHJSONResult.errorMsg("您无权上传任何数据");

        if(commandTitle == null || commandValue == null) throw new Exception("传入命令信息为空");
        LinuxCommand data = LinuxCommand.builder()
                .commDesc(commandDesc)
                .commValue(commandValue)
                .title(commandTitle)
                .build();
        data.setId((UUID.randomUUID().toString()));
        linuxCommandRepository.save(data);
        return HHJSONResult.ok(userName);
    }
    @RequestMapping("/getAll")
    @ApiOperation(value = "获取全部linux命令", notes = "获取全部linux命令")
    @ResponseBody
    public HHJSONResult getAllLinuxCommand(@RequestParam(value = "searchCommandValue" , required = false)String searchCommandValue ){
        if(!CMyString.isEmpty(searchCommandValue) && !CMyString.isEmpty(searchCommandValue.trim()))

            try {
                String sql = StrUtil.format("select * from linuxcommand where" +
                        " title like '%{}%' " +
                        "or  commDesc like '%{}%' " +
                        "or  commValue like '%{}%' " , searchCommandValue ,searchCommandValue, searchCommandValue);
                List<LinuxCommand> linuxCommands = primaryBaseService.queryData(sql, new HashMap<>(), LinuxCommand.class);
                return HHJSONResult.ok(linuxCommands);
            } catch (Exception e) {
                e.printStackTrace();
            }

        return HHJSONResult.ok(linuxCommandRepository.findAll()) ;

    }

    //通过controller返回html界面
    @RequestMapping("/linux")
    public String linuxPage() {
        return "linux";
    }

}
