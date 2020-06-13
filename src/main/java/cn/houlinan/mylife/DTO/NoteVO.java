package cn.houlinan.mylife.DTO;

import cn.houlinan.mylife.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/10/1
 * Time : 5:52
 */

@Data
public class NoteVO extends BaseEntity {


    @ApiModelProperty(value = "所属用户id" , name = "userId" , example = "1")
    private String userId ;

    @NotBlank
    @ApiModelProperty(value = "正文内容" , name = "desc" , example = "这里是正文内容")
    private String desc ;

    @ApiModelProperty(value = "密码" , name = "userId" , example = "123456")
    private String password ;

    @ApiModelProperty(value = "是否需要密码" , name = "isNeedPwd" , example = "0")
    private String isNeedPwd ;
}











