package cn.houlinan.mylife.DTO;

import cn.houlinan.mylife.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoVOResult extends BaseEntity{

    @ApiModelProperty(value = "mylife图片名称" , name = "PicMLName" , example = "ML20191010SDASDAS.PNG")
    private String PicMLName;

    @ApiModelProperty(value = "mylife原图图片名称" , name = "originalName" , example = "SDASDAS.PNG")
    private String originalName;

    @ApiModelProperty(value = "图片大小" , name = "fileSize" , example = "1123123")
    private Long fileSize ;

    @ApiModelProperty(value = "图片上传日期" , name = "uploadTime" , example = "2019/05/02")
    private String uploadTime ;

    @ApiModelProperty(value = "图片600访问地址" , name = "file600Address" , example = "2019/05/02")
    private String file600Address ;

    @ApiModelProperty(value = "图片访问地址" , name = "fileAddress" , example = "2019/05/02")
    private String fileAddress ;

}
