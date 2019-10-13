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
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/10/1
 * Time : 5:52
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "note", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class Note extends BaseEntity {


    @ApiModelProperty(value = "所属用户id" , name = "userId" , example = "1")
    @Column(name = "userid", length = 50 )
    private String userId ;

    @ApiModelProperty(value = "正文内容" , name = "noteDesc" , example = "这里是正文内容")
    @Column(name = "notedesc", columnDefinition = "longtext")
    private String noteDesc ;

    @ApiModelProperty(value = "密码" , name = "userId" , example = "123456")
    @Column(name = "password", length = 50 )
    private String password ;

    @ApiModelProperty(value = "是否需要密码" , name = "isNeedPwd" , example = "0")
    @Column(name = "isneedpwd", length = 2,columnDefinition = "int(2) default 0")
    private int isNeedPwd ;
}











