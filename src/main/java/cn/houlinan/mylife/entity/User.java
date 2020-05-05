package cn.houlinan.mylife.entity;

import cn.houlinan.mylife.listener.BaseEntityUpdateListener;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * DESC：用户实体类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/13
 * Time : 5:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class User extends BaseUserEntity{

    private static final long serialVersionUID = 4778543114061575254L;

    @ApiModelProperty(value = "隐藏相册路径" , name = "hiddenpicspath" , example = "c://pic//user//abc//hiddenPic//")
    @Column(name = "hiddenpicspath", length = 500)
    private String hiddenPicsPath ;

    /**
     * 店铺id
     * */
    @ApiModelProperty(value = "店铺id" , name = "shopId" , example = "1.")
    @Column(name = "shopid", length = 50)
    private String shopId ;


    /**
     *  是否是店铺管理员s
     * */
    @ApiModelProperty(value = "是否店铺管理员" , name = "isShopAdmin" , example = "0")
    @Column(name = "isshopadmin", length = 2 ,columnDefinition = "int(2) default 0")
    private int isShopAdmin ;



}
