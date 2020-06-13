package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.service.kuaidi.ExpressService;
import cn.houlinan.mylife.service.kuaidi.InitService;
import cn.houlinan.mylife.service.kuaidi.QueryKuaiDiByNumberService;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/6
 * Time : 11:54
 */
@RestController
@Slf4j
@RequestMapping("/kuaidi")
@Api(value = "快递相关接口", tags = "快递相关接口")
public class SelectKuaiDiNumberController {

    @Autowired
    QueryKuaiDiByNumberService queryKuaiDiByNumberService;

    @Autowired
    ExpressService expressService;

    @GetMapping("/queryKuaiDiByNumber")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "expName", value = "快递公司名称", required = true, dataType = "String", paramType = "query", defaultValue = "中通快递"),
            @ApiImplicitParam(name = "expNo", value = "快递单号", required = true, dataType = "String", paramType = "query", defaultValue = "1019917219259032239")
    })
    @ApiOperation(value = "通过快递公司和单号查询", notes = "通过快递公司和单号查询")
    public HHJSONResult sendToMe(@RequestParam(name = "expName", required = false) String expName,
                                 @RequestParam(name = "expNo", required = false) String expNo) throws Exception {

        String orderTracesByJson = expressService.queryExpressByNameAndNumber(expName, expNo);

        return HHJSONResult.ok(JSONUtil.parseObj(orderTracesByJson));
    }


    @GetMapping("/getOrderTracesByJson")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "expNo", value = "快递单号", required = true, dataType = "String", paramType = "query", defaultValue = "1019917219259032239")
    })
    @ApiOperation(value = "通过单号查询", notes = "通过单号查询：只查到一个快递公司，直接返回结果，查到多家。返回多家列表")
    public HHJSONResult getOrderTracesByJson(
            @RequestParam(name = "expNo", required = false) String expNo) throws Exception {
        log.info("正在查询快递单号：【{}】", expNo);
        String orderTracesByJson = queryKuaiDiByNumberService.getOrderTracesByJson(expNo);
        JSONObject result = JSONObject.fromObject(orderTracesByJson);
        log.info("查询快递的结果为:" + JSONUtil.formatJsonStr(orderTracesByJson));
        if (!result.containsKey("Code") || result.getInt("Code") != 100)
            return HHJSONResult.errorMsg("自动查询运单快递公司失败，请手动选择快递公司");
        JSONArray jsonArray = result.getJSONArray("Shippers");

        JSONObject resultJSON = new JSONObject();
        if(jsonArray.size() ==1 ){
            String expCode = jsonArray.getJSONObject(0).getString("ShipperCode");
            String orderResultJson = queryKuaiDiByNumberService.getOrderTracesByJson(expCode, expNo);
            resultJSON.put("resultType" , 0) ;
            resultJSON.put("orderResultJson" , orderResultJson) ;
            return HHJSONResult.ok(resultJSON);
        }
        resultJSON.put("resultType" , 2) ;
        resultJSON.put("orderResultJson" , jsonArray) ;
        return HHJSONResult.ok(resultJSON);
    }

}
