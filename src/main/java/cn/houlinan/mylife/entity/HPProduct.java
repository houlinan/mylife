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
import javax.swing.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/11/17
 * Time : 8:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hpproduct", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class HPProduct extends BaseEntity {

    /**
     * 封面图片
     * */
    @ApiModelProperty(value = "封面图片" , name = "productHeadPic" , example = "1")
    @Column(name = "productheadpic", length = 1000 )
    private String productHeadPic ;

    /**
     * 标题名称
     * */
    @NotBlank(message = "店铺需要一个名称哦！")
    @ApiModelProperty(value = "标题名称" , name = "title" , example = "1")
    @Column(name = "title", length = 100 )
    private String title ;

    /**
     * 标题名称
     * */
//    @NotBlank(message = "商品描述")
    @ApiModelProperty(value = "商品描述" , name = "productDesc" , example = "1")
    @Column(name = "productdesc", length = 100 )
    private String productDesc ;

    /**
     * 运费
     * */
//    @NotBlank(message = "没有运费可以使用默认的0哦!")
    @ApiModelProperty(value = "运费" , name = "freight" , example = "0")
    @Column(name = "freight", length = 5,columnDefinition = "int(5) default 0")
    private String freight ;

    /**
     * 库存
     * */
//    @NotBlank(message = "没有库存可以使用默认的0哦！")
    @ApiModelProperty(value = "库存" , name = "stockNumber" , example = "0")
    @Column(name = "stocknumber", length = 5,columnDefinition = "int(5) default 0")
    private int stockNumber ;

    /**
     * 实际价格
     * */
//    @NotBlank(message = "需要一个价格哦！")
    @ApiModelProperty(value = "实际价格" , name = "price" , example = "0")
    @Column(name = "price", length = 10,columnDefinition = "DOUBLE(10, 2 )")
    private double price ;

    /**
     * 原价
     * */
    @ApiModelProperty(value = "原始价格" , name = "orgPrice" , example = "0")
    @Column(name = "orgprice", length = 10,columnDefinition = "DOUBLE(10 ,2 ) ")
    private double orgPrice ;


    /**
     * 店铺id
     * */
    @ApiModelProperty(value = "店铺id" , name = "shopId" , example = "0")
    @Column(name = "shopid", length = 50 )
    private String shopId ;

    /**
     * 店铺id
     * */
    @ApiModelProperty(value = "所属种类全路径" , name = "kindDN" , example = "0")
    @Column(name = "kinddn", length = 200 )
    private String kindDN ;

    /**
     * 店铺id
     * */
    @ApiModelProperty(value = "所属父类种类id" , name = "rootKindId" , example = "0")
    @Column(name = "rootkindid", length = 200 )
    private String rootKindId ;


}
