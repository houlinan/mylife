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
 * CREATED DATE ：2019/9/13
 * Time : 6:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "type", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class Type extends BaseEntity{


    private static final long serialVersionUID = 5853979394101999578L;

    @ApiModelProperty(value = "类型名称" , name = "typeName" , example = "这是一个好看的类型")
    @Column(name = "typename", length = 60)
    private String typeName ;

    @ApiModelProperty(value = "类型密码" , name = "password" , example = "123456")
    @Column(name = "password", length = 60)
    private String password ;

    @ApiModelProperty(value = "是否隐藏" , name = "isdisplay" , example = "1")
    @Column(name = "isdisplay", length = 2,columnDefinition = "int(2) default 0")
    private Integer isDisplay ;


}
