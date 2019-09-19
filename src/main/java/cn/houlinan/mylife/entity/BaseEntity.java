package cn.houlinan.mylife.entity;

import cn.houlinan.mylife.listener.BaseEntityUpdateListener;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(BaseEntityUpdateListener.class)
public abstract class BaseEntity implements Serializable {

    /**
     * serialVersionUID : TODO
     */
    private static final long serialVersionUID = -8786648212853130762L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @Column(name = "id" ,length = 64)
    @ApiModelProperty(value = "用户主键ID" , hidden = true)
    private String id ;

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


    @Column(name = "teamid")
    private String teamid;
}
