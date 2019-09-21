package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.DTO.PhotoAlbumVO;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.utils.BeanValidator;
import cn.houlinan.mylife.utils.HHJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/22
 * Time : 0:20
 */
@Controller
@RequestMapping("/photo")
@Slf4j
@Api(value = "照片相册相关", tags = "照片相册相关")
public class PhotoController {


    @ApiOperation(value = "创建相册", notes = "创建相册的接口")
    @PostMapping("/createPhotoAlbum")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "相册名称", required = true, dataType = "String", paramType="query", defaultValue = "aaa相册"),
            @ApiImplicitParam(name = "password", value = "相册密码", required = false, dataType = "String", paramType="query", defaultValue = "123456"),
            @ApiImplicitParam(name = "isDisplay", value = "是否隐藏相册", required = false, dataType = "String",paramType="query", defaultValue = "0")
    })
    @ResponseBody
    public HHJSONResult createPhotoAlbum(
            PhotoAlbumVO photoAlbumVO ,User user ){

        BeanValidator.check(photoAlbumVO);






        return HHJSONResult.ok() ;
    }



}
