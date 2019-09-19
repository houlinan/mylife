package cn.houlinan.mylife.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "photo", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class Photo extends BaseEntity{

    @ApiModelProperty(value = "mylife图片名称" , name = "PicMLName" , example = "ML20191010SDASDAS.PNG")
    @Column(name = "picmlname", length = 100)
    private String PicMLName;

    @ApiModelProperty(value = "mylife原图图片名称" , name = "originalName" , example = "SDASDAS.PNG")
    @Column(name = "originalname", length = 100)
    private String originalName;

    @ApiModelProperty(value = "所属相册id" , name = "fromPhotoAlbumId" , example = "sdadsad-dsada-dsa")
    @Column(name = "fromphotoalbumid", length = 50)
    private String fromPhotoAlbumId ;

    @ApiModelProperty(value = "图片大小" , name = "fileSize" , example = "1123123")
    @Column(name = "filesize", length = 20 ,columnDefinition = "int(20) default 0")
    private Integer fileSize ;

    @ApiModelProperty(value = "所属用户id" , name = "fromUserId" , example = "sdadsad-dsada-dsa")
    @Column(name = "fromuserid", length = 50)
    private String fromUserId ;

    @ApiModelProperty(value = "文件缩略图" , name = "file600Path" , example = "C://xxxx")
    @Column(name = "file600path", length = 50)
    private String file600Path ;

    @ApiModelProperty(value = "文件路径" , name = "filePath" , example = "sdadsad-dsada-dsa")
    @Column(name = "filepath", length = 50)
    private String filePath ;

}
