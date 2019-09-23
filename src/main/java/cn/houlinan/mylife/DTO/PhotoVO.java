package cn.houlinan.mylife.DTO;

import cn.houlinan.mylife.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoVO extends BaseEntity{

    @ApiModelProperty(value = "mylife图片名称" , name = "PicMLName" , example = "ML20191010SDASDAS.PNG")
    private String PicMLName;

    @ApiModelProperty(value = "mylife原图图片名称" , name = "originalName" , example = "SDASDAS.PNG")
    private String originalName;

    @ApiModelProperty(value = "所属相册id" , name = "fromPhotoAlbumId" , example = "sdadsad-dsada-dsa")
    private String fromPhotoAlbumId ;

    @ApiModelProperty(value = "图片大小" , name = "fileSize" , example = "1123123")
    private Long fileSize ;

    @ApiModelProperty(value = "所属用户id" , name = "fromUserId" , example = "sdadsad-dsada-dsa")
    private String fromUserId ;

    @ApiModelProperty(value = "文件缩略图" , name = "file600Path" , example = "C://xxxx")
    private String file600Path ;

    @ApiModelProperty(value = "文件路径" , name = "filePath" , example = "sdadsad-dsada-dsa")
    private String filePath ;

    @ApiModelProperty(value = "图片上传日期" , name = "uploadTime" , example = "2019/05/02")
    private String uploadTime ;
}
