package cn.houlinan.mylife.service.kuaidi;

import cn.houlinan.mylife.DTO.ExpressDTO;
import cn.houlinan.mylife.DTO.TracesDTO;
import cn.houlinan.mylife.utils.JsonMapper;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/10
 * Time : 22:12
 */
@Service
@Slf4j
public class ExpressService {

    @Autowired
    QueryKuaiDiByNumberService queryKuaiDiByNumberService;

    public String queryExpressByNameAndNumber(String expName , String expNo)throws Exception{
        String expCode = InitService.KuaiDiCodeMap.get(expName);
        log.info("正在查询快递公司：【{}】  快递公司code：【{}】快递单号：【{}】", expName, expCode, expNo);
        String orderTracesByJson = queryKuaiDiByNumberService.getOrderTracesByJson(expCode, expNo);


        return JSONObject.fromObject(orderTracesByJson).toString();
    }


}
