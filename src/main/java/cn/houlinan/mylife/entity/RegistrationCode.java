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
@Table(name = "Registrationcode", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class RegistrationCode extends BaseEntity{


    private static final long serialVersionUID = 5853979394101999578L;

    @ApiModelProperty(value = "注册店铺" , name = "shopId" , example = "这是一个好看的类型")
    @Column(name = "shopid", length = 60)
    private String shopId ;

    @ApiModelProperty(value = "属性id" , name = "attribute" , example = "123456")
    @Column(name = "attribute", length = 500)
    private String attribute ;

    @ApiModelProperty(value = "des加密结果" , name = "desData" , example = "123456")
    @Column(name = "desdata", length = 500)
    private String desData ;

    @ApiModelProperty(value = "是否使用" , name = "isUserd" , example = "1")
    @Column(name = "isuserd", length = 2,columnDefinition = "int(2) default 0")
    private int isUserd ;


    @ApiModelProperty(value = "是否创建过" , name = "isCreate" , example = "0")
    @Column(name = "iscreate", length = 2,columnDefinition = "int(2) default 0")
    private int isCreate ;



}
