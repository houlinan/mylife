package cn.houlinan.mylife.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


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
public class Team implements Serializable {


    private static final long serialVersionUID = 5458698096863590604L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "crtime", updatable = false)
    @CreationTimestamp
    private Date crTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatetime", updatable = true)
    @UpdateTimestamp
    private Date updateTime;

    @NotBlank(message = "小组名称必传")
    @ApiModelProperty(value = "小组名称" , name = "teamName" , example = "这是一个帅气的小组")
    @Column(name = "teamname", length = 100)
    private String teamName ;

    @ApiModelProperty(value = "小组密码" , name = "teamPassword" , example = "123456")
    @Column(name = "teampassword", length = 100)
    private String teamPassword ;

    @ApiModelProperty(value = "小组描述" , name = "teamDesc" , example = "123456")
    @Column(name = "teamdesc", length = 100)
    private String teamDesc ;

    @ApiModelProperty(value = "小组邮箱" , name = "teamEmail" , example = "team@qq.com")
    @Column(name = "teamemail", length = 100)
    private String teamEmail ;

    @ApiModelProperty(value = "是否发送所有成员" , name = "isSendAllUser" , example = "1")
    @Column(name = "issendalluser", length = 2 ,columnDefinition = "int(2) default 0")
    private int isSendAllUser ;

    @ApiModelProperty(value = "小组册路径" , name = "picspath" , example = "c://pic//team//abc//hiddenPic//")
    @Column(name = "picspath"  ,length = 500)
    private String picsPath ;

    @ApiModelProperty(value = "管理员账号id" , name = "adminuserid" , example = "team@qq.com")
    @Column(name = "adminuserid", length = 100)
    private String adminUserId ;


//    @ManyToOne
//    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
//    @ApiModelProperty(value = "类型信息" , name = "type" , example = "1" ,required = true)
//    private Type type ;
}
