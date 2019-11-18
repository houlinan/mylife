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
 * CREATED DATE ：2019/11/17
 * Time : 11:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hpcomment", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class HPComment extends BaseEntity{

    @ApiModelProperty(value = "商品id" , name = "productId" , example = "1")
    @Column(name = "productid", length = 150 )
    private String productId ;

    @ApiModelProperty(value = "评论内容" , name = "comment" , example = "1")
    @Column(name = "comment", length = 500 )
    private String comment ;

    @ApiModelProperty(value = "父类评论id" , name = "parentId" , example = "1")
    @Column(name = "parentid", length = 50 )
    private String parentId ;

    @ApiModelProperty(value = "评论人" , name = "commentUser" , example = "15156saddsa181123")
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private User commentUser ;

    @ApiModelProperty(value = "店主用户" , name = "shopAdminUser" , example = "15156saddsa181123")
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private User shopAdminUser ;

    @ApiModelProperty(value = "来源店铺" , name = "fromShop" , example = "15156saddsa181123")
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private HPShop fromShop;

    @ApiModelProperty(value = "来源商品" , name = "fromProduct" , example = "15156saddsa181123")
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private HPProduct fromProduct ;


}
