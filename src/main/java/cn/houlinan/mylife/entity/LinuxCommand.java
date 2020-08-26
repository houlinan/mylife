package cn.houlinan.mylife.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @className :LinuxCommand
 * @DESC : linux 命令
 * @Author :hou.linan
 * @date :2020/8/26 14:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "linuxcommand", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class LinuxCommand  {

    @Id
    @Column(name = "id" ,length = 64)
    @ApiModelProperty(value = "主键ID" , hidden = true )
    private String id ;

    @ApiModelProperty(value = "主标题" , name = "title" , example = "1")
    @Column(name = "title", length = 100 )
    String title ;

    @ApiModelProperty(value = "操作描述" , name = "desc" , example = "1")
    @Column(name = "commdesc", length = 300 )
    String commDesc ;

    @ApiModelProperty(value = "操作命令" , name = "value" , example = "1")
    @Column(name = "commvalue", length = 500 )
    String commValue ;


}
