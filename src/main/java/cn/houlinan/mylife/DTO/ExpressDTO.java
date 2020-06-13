package cn.houlinan.mylife.DTO;

import lombok.Data;

import java.util.List;
import cn.houlinan.mylife.DTO.TracesDTO;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/11
 * Time : 21:08
 */
@Data
public class ExpressDTO {

    private Long LogisticCode ;
    private String ShipperCode ;
    private List<TracesDTO> Traces ;
    private int State ;
    private int EBusinessID ;
    private boolean  Success ;

}
