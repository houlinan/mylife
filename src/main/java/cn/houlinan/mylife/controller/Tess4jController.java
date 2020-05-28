package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.constant.GeLuoMiConstant;
import cn.houlinan.mylife.service.PhotoService;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.OkhttpUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/24
 * Time : 20:20
// */
@RestController
@RequestMapping("/tess4j")
@Slf4j
public class Tess4jController {
//
    @Autowired
    PhotoService photoService ;
//
//
    /**
     *DESC:支持多文件上传图片
     *@param:  files
     *@return:  Result 返回文件的名称和静态地址
     *@author hou.linan
     *@date:
     */
    @PostMapping(value = "/uploadPic" , consumes = "multipart/*" , headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传图片", notes = "上传图片的接口")
    @ResponseBody
    public HHJSONResult uploadPic
    (@ApiParam(value = "上传文件",required = false) @RequestParam(name = "files" , required = false)
             MultipartFile files)throws Exception{
//
            if(!ObjectUtils.isEmpty(files)){

                String photo = photoService.saveFileToPath(files , GeLuoMiConstant.GELUOMI_PICS_PATH );
                String finalPath = GeLuoMiConstant.GELUOMI_PICS_PATH  + photo ;

                return HHJSONResult.ok(OkhttpUtil.sendMultipartHttp("file" , photo ,finalPath ));
//                return Tess4jUtil.testTess4j(finalPath) ;
            }
        return HHJSONResult.errorMsg("失败");
    }

}
