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
    @Column(name = "productheadpic", length = 100 )
    private String productHeadPic ;

    /**
     * 标题名称
     * */
    @ApiModelProperty(value = "标题名称" , name = "title" , example = "1")
    @Column(name = "title", length = 100 )
    private String title ;

    /**
     * 运费
     * */
    @ApiModelProperty(value = "库存" , name = "freight" , example = "0")
    @Column(name = "freight", length = 5,columnDefinition = "int(5) default 0")
    private String freight ;

    /**
     * 库存
     * */
    @ApiModelProperty(value = "库存" , name = "stockNumber" , example = "0")
    @Column(name = "stocknumber", length = 5,columnDefinition = "int(5) default 0")
    private int stockNumber ;

    /**
     * 实际价格
     * */
    @ApiModelProperty(value = "实际价格" , name = "price" , example = "0")
    @Column(name = "price", length = 10,columnDefinition = "DOUBLE(10, 2 )")
    private int price ;

    /**
     * 原价
     * */
    @ApiModelProperty(value = "原始价格" , name = "orgPrice" , example = "0")
    @Column(name = "orgprice", length = 10,columnDefinition = "DOUBLE(10 ,2 ) ")
    private int orgPrice ;



}
