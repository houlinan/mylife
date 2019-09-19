package cn.houlinan.mylife.DTO;

import cn.houlinan.mylife.entity.Team;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/18
 * Time : 23:39
 */
@Data
@ApiModel(value = "用户返给前端实体类" , description = "用户实体对象")
public class UserVO {

    @Id
    @ApiModelProperty(value = "用户主键ID" , hidden = true)
    private String id ;

    @ApiModelProperty(value = "用户名称" , name = "userName" , example = "test")
    private String userName ;

    @ApiModelProperty(value = "邮箱" , name = "email" , example = "test@qq.com")
    private String email ;

    //    @NotBlank(message = "密码不可以为空")
    @ApiModelProperty(value = "密码" , name = "password" , example = "123456")
    private String password;

    @ApiModelProperty(value = "手机号" , name = "mobile" , example = "17625997779")
    private Long mobile ;

    @ApiModelProperty(value = "用户Token" , name = "userToken" , example = "dsadasd-dsada-dsadas" )
    private String userToken;


    @ApiModelProperty(value = "所属小组" , name = "team" , example = "1" ,required = true)
    private Team team ;

    @ApiModelProperty(value = "隐藏相册路径" , name = "hiddenpicspath" , example = "c://pic//user//abc//hiddenPic//")
    private String hiddenPicsPath ;

    /*
     * 最后登陆时间
     * */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date lastLoginTime;

}
