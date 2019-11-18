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
 * Time : 8:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hpproductpic", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class HPProductPicList extends BaseEntity {

    @ApiModelProperty(value = "商品id" , name = "productId" , example = "1")
    @Column(name = "productid", length = 100 )
    private String productId;

    @ApiModelProperty(value = "图片地址" , name = "picSrc" , example = "1")
    @Column(name = "picSrc", length = 100 )
    private String picSrc ;

    @ApiModelProperty(value = "图片上传人" , name = "uploadUserId" , example = "1")
    @Column(name = "uploadUserId", length = 50 )
    private String uploadUserId ;


}
