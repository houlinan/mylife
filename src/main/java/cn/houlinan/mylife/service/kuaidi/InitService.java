package cn.houlinan.mylife.service.kuaidi;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/6
 * Time : 12:31
 */
@Component
public class InitService {

    public static  Map<String , String > KuaiDiCodeMap = new HashMap<>();

    public InitService(){
        init();
    }

    public static void init(){
        KuaiDiCodeMap.put("顺丰速运" ,"SF");
        KuaiDiCodeMap.put("百世快递" ,"HTKY");
        KuaiDiCodeMap.put("中通快递" ,"ZTO");
        KuaiDiCodeMap.put("申通快递" ,"STO");
        KuaiDiCodeMap.put("圆通速递" ,"YTO");
        KuaiDiCodeMap.put("韵达速递" ,"YD");
        KuaiDiCodeMap.put("邮政快递包裹" ,"YZPY");
        KuaiDiCodeMap.put("EMS" ,"EMS");
        KuaiDiCodeMap.put("天天快递" ,"HHTT");
        KuaiDiCodeMap.put("京东快递" ,"JD");
        KuaiDiCodeMap.put("优速快递" ,"UC");
        KuaiDiCodeMap.put("德邦快递" ,"DBL");
        KuaiDiCodeMap.put("宅急送" ,"ZJS");
    }


}
