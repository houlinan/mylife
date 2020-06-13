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
 * CREATED DATE ：2020/6/8
 * Time : 21:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "express", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class Express  extends BaseEntity{

    @ApiModelProperty(value = "用户id" , name = "userId" , example = "1")
    @Column(name = "userid", length = 50 )
    private String userId ;

    @ApiModelProperty(value = "是否已经删除" , name = "isDelete" , example = "1")
    @Column(name = "isdelete", length = 2 ,columnDefinition = "int(2) default 0")
    private int isDelete ;

    @ApiModelProperty(value = "快递单号" , name = "expressNumber" , example = "1")
    @Column(name = "expressnumber", length = 50 )
    private String expressNumber ;

    @ApiModelProperty(value = "快递公司code" , name = "expressCode" , example = "1")
    @Column(name = "expresscode", length = 50 )
    private String expressCode ;

    @ApiModelProperty(value = "快递公司名称" , name = "expressName" , example = "1")
    @Column(name = "expressname", length = 50 )
    private String expressName ;

    @ApiModelProperty(value = "评论人" , name = "commentUser" , example = "15156saddsa181123")
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private User fromUser ;


}
