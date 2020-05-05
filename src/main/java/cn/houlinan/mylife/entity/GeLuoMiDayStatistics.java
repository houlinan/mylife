package cn.houlinan.mylife.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.Serializers;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * DESC：格洛米日统计表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/1
 * Time : 20:50
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "geluomidaystatistics", indexes = {
        @Index(name = "id", columnList = "id")
})
@Builder
public class GeLuoMiDayStatistics extends BaseEntity {

    @ApiModelProperty(value = "统计时间" , name = "statisticsTime" , example = "2020-02-02")
    @Column(name = "statisticstime")
    private String statisticsTime ;

    @ApiModelProperty(value = "参赛时间" , name = "statisticsDate" , example = "2020-02-02")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "statisticsdate")
    @Ignore
    private Date statisticsDate ;

    @ApiModelProperty(value = "早上体重" , name = "morningWeight" , example = "110")
    @Column(name = "morningweight", length = 20)
    private Double morningWeight ;

    @ApiModelProperty(value = "晚上体重" , name = "nightWeight" , example = "110")
    @Column(name = "nightweight", length = 20)
    private Double nightWeight;

    @ApiModelProperty(value = "早上腰围" , name = "morningWaistline" , example = "110")
    @Column(name = "morningwaistline", length = 20)
    private Double morningWaistline;

    @ApiModelProperty(value = "晚上腰围" , name = "nightWaistline" , example = "110")
    @Column(name = "nightwaistline", length = 20)
    private Double nightWaistline;

    @ApiModelProperty(value = "格洛米用户登录名称" , name = "geluomiUserName" , example = "houlinan@qq.com")
    @Column(name = "geluomiusername", length = 100)
    private String geluomiUserName ;

    @ApiModelProperty(value = "格洛米用户昵称" , name = "geluomiUserNikeName" , example = "houlinan")
    @Column(name = "geluomiusernikename", length = 100)
    private String geluomiUserNikeName ;

}
