package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.DTO.PhotoAlbumVO;
import cn.houlinan.mylife.DTO.PhotoVOResult;
import cn.houlinan.mylife.entity.Photo;
import cn.houlinan.mylife.entity.PhotoAlbum;
import cn.houlinan.mylife.entity.Team;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.PhotoAlbumRepository;
import cn.houlinan.mylife.entity.primary.repository.PhotoRepository;
import cn.houlinan.mylife.service.PhotoAlbumService;
import cn.houlinan.mylife.service.PhotoService;
import cn.houlinan.mylife.service.common.MyPage;
import cn.houlinan.mylife.utils.BeanValidator;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    PhotoAlbumService photoAlbumService ;

    @Autowired
    PhotoAlbumRepository photoAlbumRepository;

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoRepository photoRepository;

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

        Team team = user.getTeam();
        if(team == null) return HHJSONResult.errorMsg("请先绑定小组");

        PhotoAlbum photoAlbum = new PhotoAlbum( );
        BeanUtils.copyProperties(photoAlbumVO , photoAlbum);

        return HHJSONResult.ok(photoAlbumService.createPhotoAlbum(photoAlbum , user)) ;
    }

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
    public HHJSONResult uploadPic(@ApiParam(value = "上传文件",required = false) @RequestParam(name = "files" , required = false) MultipartFile[] files ,
                                  @RequestParam("albumId")  String albumId,User user){

        Team team = user.getTeam();
        if(team == null)  return HHJSONResult.errorMsg("请先绑定组织");

        if(CMyString.isEmpty(albumId)) return HHJSONResult.errorMsg("请传入相册id");

        PhotoAlbum photoAlbumById = photoAlbumRepository.findPhotoAlbumById(albumId);
        if(photoAlbumById == null) return HHJSONResult.errorMsg("没有找到该相册的信息");

        if(!photoAlbumById.getTeamid().equals(user.getTeamid())) return HHJSONResult.errorMsg("上传的非您小组相册");


        //  上传文件
        for(int a = 0 ;a <files.length ;a ++ ){
            MultipartFile file = files[a];
            if(!ObjectUtils.isEmpty(file)){

                Photo photo = photoService.uploadPic(file , photoAlbumById, false );
                photoRepository.save(photo);
            }
        }

        return HHJSONResult.ok() ;
    }


    @ApiOperation(value = "根据用户id获取用户所有相册", notes = "根据用户id获取用户所有相册的接口")
    @RequestMapping("/getAllAlbumByUserId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "String", paramType="query", defaultValue = "dsadadas"),
    })
    @ResponseBody
    public HHJSONResult getAllAlbumByUserId(User user, @RequestParam(value = "userId", required = false) String userId){
        if(user == null ) return HHJSONResult.errorMsg("请登录");
        return HHJSONResult.ok(photoAlbumService.findAppAlubmByUser(user.getId()));
    }


    @ApiOperation(value = "根据用户id获取用户所有相册", notes = "根据用户id获取用户所有相册的接口")
    @RequestMapping("/findAppAlubmByUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "albumId", value = "相册id", required = false, dataType = "String", paramType="query", defaultValue = "dsadadas"),
    })
    @ResponseBody
    public HHJSONResult findAppAlubmByUser(User user ,
                                           @RequestParam(value = "albumId" , required = false)String albumId,
                                           @RequestParam(value = "pageSize",required = false ,defaultValue = "9")int pageSize,
                                           @RequestParam(value = "pageNum",required = false ,defaultValue = "0")int pageNum
                                           )throws Exception{

        if(CMyString.isEmpty(albumId)) return HHJSONResult.errorMsg("传入相册id为空");

        MyPage<PhotoVOResult> photoByAlbumId = photoService.findPhotoByAlbumId(user, albumId, pageSize, pageNum);

        return HHJSONResult.ok(photoByAlbumId);
    }


    /**
     *DESC:支持多文件上传图片
     *@param:  files
     *@return:  Result 返回文件的名称和静态地址
     *@author hou.linan
     *@date:
     */
    @PostMapping(value = "/uploadPicToUserDisPlayAlbum" , consumes = "multipart/*" , headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传图片", notes = "上传图片的接口")
    @ResponseBody
    public HHJSONResult uploadPicToUserDisPlayAlbum(
            @ApiParam(value = "上传文件",required = false)
            @RequestParam(name = "files" , required = false) MultipartFile[] files ,
                                  User user){

        Team team = user.getTeam();
        if(team == null)  return HHJSONResult.errorMsg("请先绑定组织");

        String path = user.getHiddenPicsPath();

        //  上传文件
        for(int a = 0 ;a <files.length ;a ++ ){
            MultipartFile file = files[a];
            photoService.copyFileToPath(file , path);
        }

        return HHJSONResult.ok() ;
    }



    @ApiOperation(value = "根据用户id获取用户所有相册", notes = "根据用户id获取用户所有相册的接口")
    @RequestMapping("/checkAlbumPassword")
    @ResponseBody
    public HHJSONResult checkAlbumPassword(
            @RequestParam(value = "albumId" , required = false)String albumId,
            @RequestParam(value = "passWord" , required = false)String passWord){

        PhotoAlbum photoAlbumById = photoAlbumRepository.findPhotoAlbumById(albumId);
        if(photoAlbumById == null ) return HHJSONResult.errorMsg("没找到相册信息");
        if(CMyString.isEmpty(photoAlbumById.getPassword())) return HHJSONResult.ok();
        if(CMyString.isEmpty(passWord)) return HHJSONResult.errorMsg("密码错误");
        if(!photoAlbumById.getPassword().equals(passWord)) return HHJSONResult.errorMsg("密码错误");
        return HHJSONResult.ok();
    }


}
