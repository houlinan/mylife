package cn.houlinan.mylife.entity;

import cn.houlinan.mylife.listener.BaseEntityUpdateListener;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/2
 * Time : 17:37
 */
@MappedSuperclass
@Data
@EntityListeners(BaseEntityUpdateListener.class)
public class BaseUserEntity extends BaseEntity {


    @NotBlank(message = "用户登录名称必填")
    @ApiModelProperty(value = "用户名称" , name = "userName" , example = "test")
    @Column(name = "username", length = 60)
    private String userName ;

//    @NotBlank(message = "用户头像")
    @ApiModelProperty(value = "用户头像" , name = "headPic" , example = "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIsUhghynqYfPexrPiaKjQhicTIGbwkYa7vH0tzkVwIFHTmrT0stqLdia6QK5iarBu5O20QKYdN6R6lSg/132")
    @Column(name = "headpic", length = 600)
    private String headPic ;

    @NotBlank(message = "用户昵称必填")
    @ApiModelProperty(value = "昵称" , name = "nikeName" , example = "昵称")
    @Column(name = "nikename", length = 60)
    private String nikeName ;

    @ApiModelProperty(value = "邮箱" , name = "email" , example = "test@qq.com")
    @Column(name = "email", length = 100)
    private String email ;

    @NotBlank(message = "密码不可以为空")
    @ApiModelProperty(value = "密码" , name = "password" , example = "123456")
    @Column(name = "password", length = 100)
    private String password;


    @ApiModelProperty(value = "openID" , name = "openId" , example = "oRnZI43JJpc1VUNYrjnR2uuDi8eA")
    @Column(name = "openid", length = 100)
    private String openId;


    @ApiModelProperty(value = "手机号" , name = "mobile" , example = "17625997779")
    @Column(name = "mobile")
    private Long mobile ;

    /*
     * 最后登陆时间
     * */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     *  是否是店铺管理员s
     * */
    @ApiModelProperty(value = "用户类型，9超级管理员，1管理员，0普通用户" , name = "userType" , example = "0")
    @Column(name = "userType", length = 2 ,columnDefinition = "int(2) default 0")
    private int userType ;




}
