package cn.houlinan.mylife.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

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
@Table(name = "hpshop", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class HPShop extends BaseEntity{



    @ApiModelProperty(value = "店铺名称" , name = "shopName" , example = "一个很好听的店铺")
    @Column(name = "shopname", length = 60)
    private String shopName ;

    @ApiModelProperty(value = "店铺简介" , name = "shopDesc" , example = "一个很好听的店铺简介")
    @Column(name = "shopdesc", length = 60)
    private String shopDesc ;

    @ApiModelProperty(value = "店铺电话" , name = "mobile" , example = "17625997779")
    @Column(name = "mobile", length = 20)
    private String mobile ;

    @ApiModelProperty(value = "店铺地址" , name = "address" , example = "黑龙江省鹤岗市审计四号楼")
    @Column(name = "address", length = 200)
    private String address ;

    @ApiModelProperty(value = "营业时间" , name = "openTime" , example = "早九点，晚八点")
    @Column(name = "opentime", length = 50)
    private String openTime ;

    @ApiModelProperty(value = "店铺头像封面图" , name = "headPic" , example = "https://www.baidu.com")
    @Column(name = "headpic", length = 50)
    private String headPic  ;

    @ApiModelProperty(value = "店铺店长二维码" , name = "admin2RCode" , example = "https://www.baidu.com")
    @Column(name = "admin2rcode", length = 50)
    private String admin2RCode ;

    @ApiModelProperty(value = "是否置顶" , name = "isTop" , example = "0")
    @Column(name = "istop", length = 2,columnDefinition = "int(2) default 0")
    private Integer isTop;

    @ApiModelProperty(value = "超管数量" , name = "adminNum" , example = "1")
    @Column(name = "adminnum", length = 2,columnDefinition = "int(2) default 1")
    private Integer adminNum ;

    @ApiModelProperty(value = "商品数量" , name = "productNum" , example = "20")
    @Column(name = "productnum", length = 3,columnDefinition = "int(3) default 20")
    private Integer productNum;

    @ApiModelProperty(value = "商品图片数量" , name = "picNum" , example = "5")
    @Column(name = "picnum", length = 3,columnDefinition = "int(3) default 5")
    private Integer picNum ;

    @ApiModelProperty(value = "店铺商品超时间" , name = "productOutTime" , example = "2")
    @Column(name = "productouttime", length = 2 ,columnDefinition = "int(2) default 5")
    private Integer productOutTime;

}
