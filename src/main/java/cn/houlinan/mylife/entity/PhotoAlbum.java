package cn.houlinan.mylife.entity;

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
 * DESC：相册表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 14:25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "photoalbum", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class PhotoAlbum extends BaseEntity{

    @ApiModelProperty(value = "名称" , name = "name" , example = "这是一个帅气的小组")
    @Column(name = "name", length = 100)
    private String name ;

    @ApiModelProperty(value = "密码" , name = "password" , example = "123456")
    @Column(name = "password", length = 100)
    private String password ;

    @ApiModelProperty(value = "相册路径" , name = "path" , example = "c://pic//photoAlbum//abc//")
    @Column(name = "path", length = 500)
    private String path ;

    @ApiModelProperty(value = "所属用户id" , name = "fromUserId" , example = "1")
    @Column(name = "fromuserid", length = 50 )
    private String fromUserId;

    @ApiModelProperty(value = "是否隐藏" , name = "isdisplay" , example = "1")
    @Column(name = "isdisplay", length = 2,columnDefinition = "int(2) default 0")
    private Integer isDisplay ;

    @ApiModelProperty(value = "是否有密码" , name = "isHasPwd" , example = "1")
    @Column(name = "ishaspwd", length = 2,columnDefinition = "int(2) default 0")
    private Integer isHasPwd ;


    @ApiModelProperty(value = "封面图片路径" , name = "coverPicPath" , example = "1")
    @Column(name = "coverpicpath", length = 200 )
    private String coverPicPath;

    @ApiModelProperty(value = "相册简介" , name = "albumDesc" , example = "1")
    @Column(name = "albumdesc", length = 500 )
    private String albumDesc;

    @ApiModelProperty(value = "相册标签" , name = "albumLabel" , example = "1")
    @Column(name = "albumlabel", length = 500 )
    private String albumLabel;


}
