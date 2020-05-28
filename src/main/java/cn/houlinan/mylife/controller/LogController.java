package cn.houlinan.mylife.controller;

import cn.hutool.core.lang.UUID;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/26
 * Time : 13:15
 */
@RequestMapping("/log")
@Controller
@Slf4j
@Api(value = "系统日志相关", tags = "系统日志相关")
public class LogController {


    //通过controller返回html界面
    @RequestMapping("/addLog")
    @ResponseBody
    public String addLog() {

        log.info("添加了一个日志  : " + UUID.randomUUID().toString());
        log.info("添加了一个日志  : " + UUID.randomUUID().toString());
        log.info("添加了一个日志  : " + UUID.randomUUID().toString());
        log.info("添加了一个日志  : " + UUID.randomUUID().toString());
        log.info("添加了一个日志  : " + UUID.randomUUID().toString());
        log.info("添加了一个日志  : " + UUID.randomUUID().toString());
        log.info("添加了一个日志  : " + UUID.randomUUID().toString());
        log.info("添加了一个日志  : " + UUID.randomUUID().toString());

        return "success";
    }


    //通过controller返回html界面
    @RequestMapping("/log")
    public String formatParamPage() {
        return "log";
    }
}
