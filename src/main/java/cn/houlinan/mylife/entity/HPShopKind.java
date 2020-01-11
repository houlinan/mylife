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
 * DESC：首页展示种类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/12/31
 * Time : 11:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hpshopkind", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class HPShopKind extends BaseEntity{


    @ApiModelProperty(value = "父类id" , name = "parentKindId" , example = "1.")
    @Column(name = "parentkindid", length = 50)
    String parentKindId ;

    @ApiModelProperty(value = "类别名称" , name = "kindName" , example = "1.")
    @Column(name = "kindname", length = 50)
    String kindName ;

    @ApiModelProperty(value = "类别拼音名称" , name = "kindPinYin" , example = "1.")
    @Column(name = "kindpinyin", length = 50)
    String kindPinYin ;

    @ApiModelProperty(value = "类别颜色" , name = "kindColor" , example = "1.")
    @Column(name = "kindcolor", length = 50)
    String kindColor ;

    @ApiModelProperty(value = "类别等级" , name = "kindLevel" , example = "0")
    @Column(name = "kindlevel", length = 2 ,columnDefinition = "int(2) default 0")
    Integer kindLevel ;

    @ApiModelProperty(value = "店铺id" , name = "shopId" , example = "1.")
    @Column(name = "shopid", length = 50)
    String shopId ;

    @ApiModelProperty(value = "店铺admin用户id" , name = "shopAdminUserId" , example = "1.")
    @Column(name = "shopadminuserid", length = 50)
    String shopAdminUserId ;

    @ApiModelProperty(value = "是否置顶" , name = "isTop" , example = "0")
    @Column(name = "isTop", length = 2 ,columnDefinition = "int(2) default 0")
    String isTop ;

    @ApiModelProperty(value = "图标" , name = "icon" , example = "1.")
    @Column(name = "icon", length = 50)
    String icon ;
}
