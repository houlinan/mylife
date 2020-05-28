package cn.houlinan.mylife.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/21
 * Time : 14:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamVO {


    private static final long serialVersionUID = 5458698096863590604L;

    @NotBlank(message = "小组名称必传")
    @ApiModelProperty(value = "小组名称" , name = "teamName" , example = "这是一个帅气的小组")
    private String teamName ;

    @ApiModelProperty(value = "小组描述" , name = "teamdesc" , example = "这是一个帅气的小组")
    private String teamDesc ;

    @NotBlank(message = "小组密码必传")
    @ApiModelProperty(value = "小组密码" , name = "teamPassword" , example = "123456")
    private String teamPassword ;

    @Email
    @ApiModelProperty(value = "小组邮箱" , name = "teamEmail" , example = "team@qq.com")
    private String teamEmail ;

    @ApiModelProperty(value = "是否发送所有成员" , name = "isSendAllUser" , example = "1")
    private int isSendAllUser ;

    @ApiModelProperty(value = "小组册路径" , name = "picspath" , example = "c://pic//team//abc//hiddenPic//")
    private String picsPath ;

    @ApiModelProperty(value = "管理员账号id" , name = "adminuserid" , example = "team@qq.com")
    private String adminUserId ;
}
