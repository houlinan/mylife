package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.utils.HHJSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/3
 * Time : 17:11
 */
@Controller
@Slf4j
@RequestMapping("/pic")
public class GetPicListController {

    @Value("${upload.path}")
    private String path ;

    @ResponseBody
    @RequestMapping("/getPicList")
    public HHJSONResult getPicListUrl(){


        return null ;
    }


}
