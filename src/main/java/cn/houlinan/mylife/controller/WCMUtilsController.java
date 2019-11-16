package cn.houlinan.mylife.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/11/15
 * Time : 10:36
 */
@RequestMapping("/wcm")
@Controller
public class WCMUtilsController {


    @GetMapping("/testFormat")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "测试数据", required = true, dataType = "String", paramType = "path")
    })
    @ApiOperation(value = "wcm测试用例前端参数转换成json数据", notes = "wcm测试用例前端参数转换成json数据接口")
    public String WCMTestFormatJSONUtils(@RequestParam(name = "data" , required = false)String data){


        System.out.println(data);

        return "";
    }


    //通过controller返回html界面
    @RequestMapping("/test")
    public  String testJumpPage(){
        return "test";
    }

    //通过controller返回html界面
    @RequestMapping("/index")
    public  String indexJumpPage(){
        return "index";
    }



}
