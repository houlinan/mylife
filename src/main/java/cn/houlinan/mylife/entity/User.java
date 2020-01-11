package cn.houlinan.mylife.entity;

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
public class User extends BaseEntity{

    private static final long serialVersionUID = 4778543114061575254L;

//    @NotBlank(message = "用户名不可以为空")
    @ApiModelProperty(value = "用户名称" , name = "userName" , example = "test")
    @Column(name = "username", length = 60)
    private String userName ;

    @ApiModelProperty(value = "邮箱" , name = "email" , example = "test@qq.com")
    @Column(name = "email", length = 100)
    private String email ;

//    @NotBlank(message = "密码不可以为空")
    @ApiModelProperty(value = "密码" , name = "password" , example = "123456")
    @Column(name = "password", length = 100)
    private String password;

    @ApiModelProperty(value = "手机号" , name = "mobile" , example = "17625997779")
    @Column(name = "mobile")
    private Long mobile ;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    @ApiModelProperty(value = "所属小组" , name = "team" , example = "1" ,required = true)
    private Team team ;

    @ApiModelProperty(value = "隐藏相册路径" , name = "hiddenpicspath" , example = "c://pic//user//abc//hiddenPic//")
    @Column(name = "hiddenpicspath", length = 500)
    private String hiddenPicsPath ;

    /*
     * 最后登陆时间
     * */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Column(name = "last_login_time")
    private Date lastLoginTime;

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
