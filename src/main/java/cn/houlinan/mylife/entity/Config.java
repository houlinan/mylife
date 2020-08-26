package cn.houlinan.mylife.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @className :LinuxCommand
 * @DESC : 配置类
 * @Author :hou.linan
 * @date :2020/8/26 14:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "config", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
@Entity
public class Config {

    @Id
    @Column(name = "id" ,length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID" , hidden = true )
    private int id ;

    @ApiModelProperty(value = "键" , name = "ckey" , example = "1")
    @Column(name = "ckey", length = 300 )
    String cKey ;

    @ApiModelProperty(value = "值" , name = "cvalue" , example = "1")
    @Column(name = "cvalue", length = 100 )
    String cValue ;

    @ApiModelProperty(value = "备注" , name = "cdesc" , example = "1")
    @Column(name = "cdesc", length = 500 )
    String cDesc ;


}
