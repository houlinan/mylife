package cn.houlinan.mylife.DTO;

import cn.houlinan.mylife.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * DESC：相册表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 14:25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoAlbumVO extends BaseEntity{

    @NotBlank(message = "相册名称不能为空")
    @ApiModelProperty(value = "名称" , name = "name" , example = "这是一个帅气的小组")
    private String name ;

    @ApiModelProperty(value = "密码" , name = "password" , example = "123456")
    private String password ;

    @ApiModelProperty(value = "相册路径" , name = "path" , example = "c://pic//photoAlbum//abc//")
    private String path ;

    @ApiModelProperty(value = "所属用户id" , name = "fromUserId" , example = "1")
    private String fromUserId;

    @ApiModelProperty(value = "是否隐藏" , name = "isdisplay" , example = "1")
    private Integer isDisplay ;

    @ApiModelProperty(value = "封面图片路径" , name = "coverPicPath" , example = "1")
    private String coverPicPath;

    @ApiModelProperty(value = "相册简介" , name = "albumDesc" , example = "1")
    private String albumDesc;

    @ApiModelProperty(value = "相册标签" , name = "albumLabel" , example = "1")
    private String albumLabel;

}
