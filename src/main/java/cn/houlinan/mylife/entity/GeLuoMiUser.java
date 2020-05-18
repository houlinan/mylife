package cn.houlinan.mylife.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.Serializers;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DESC：格洛米用户表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/1
 * Time : 20:23
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "geluomiuser", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
public class GeLuoMiUser extends BaseUserEntity {

    @Transient
    @ApiModelProperty(value = "用户登录token" , name = "userToken" , example = "")
    @Column(name = "usertoken", length = 20)
    private String userToken ;

//    @NotNull(message = "请填写您的初始体重")
    @ApiModelProperty(value = "初始体重" , name = "initWeight" , example = "110")
    @Column(name = "initweight", length = 20)
    private Double initWeight;


    @ApiModelProperty(value = "参赛时间" , name = "entryTime" , example = "2020-02-02")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "entrytime")
    private Date entryTime ;

    @ApiModelProperty(value = "参赛体重" , name = "entryWeight" , example = "110")
    @Column(name = "entryweight", length = 20)
    private Double entryWeight ;

    @ApiModelProperty(value = "参赛腰围" , name = "entryWaistline" , example = "110")
    @Column(name = "entrywaistline", length = 20)
    private Double entryWaistline;

//    @NotBlank(message = "请填写您使用的产品")
    @ApiModelProperty(value = "使用产品" , name = "useProduct" , example = "瘦瘦包")
    @Column(name = "useproduct", length = 20)
    private String useProduct ;



}
