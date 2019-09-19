package cn.houlinan.mylife.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * DESC：小组实体类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/13
 * Time : 5:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "team", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class Team extends BaseEntity{


    private static final long serialVersionUID = 5458698096863590604L;

    @ApiModelProperty(value = "小组名称" , name = "teamName" , example = "这是一个帅气的小组")
    @Column(name = "teamname", length = 100)
    private String teamName ;

    @ApiModelProperty(value = "小组密码" , name = "teamPassword" , example = "123456")
    @Column(name = "teampassword", length = 100)
    private String teamPassword ;

    @ApiModelProperty(value = "小组邮箱" , name = "teamEmail" , example = "team@qq.com")
    @Column(name = "teamemail", length = 100)
    private String teamEmail ;

    @ApiModelProperty(value = "是否发送所有成员" , name = "isSendAllUser" , example = "1")
    @Column(name = "issendalluser", length = 2 ,columnDefinition = "int(2) default 0")
    private int isSendAllUser ;

    @ApiModelProperty(value = "小组册路径" , name = "picspath" , example = "c://pic//team//abc//hiddenPic//")
    @Column(name = "picspath")
    private String picsPath ;

    @ApiModelProperty(value = "管理员账号id" , name = "adminuserid" , example = "team@qq.com")
    @Column(name = "adminuserid", length = 100)
    private String adminUserId ;


//    @ManyToOne
//    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
//    @ApiModelProperty(value = "类型信息" , name = "type" , example = "1" ,required = true)
//    private Type type ;
}
