package cn.houlinan.mylife.service;

import cn.houlinan.mylife.entity.HPShopKind;
import cn.houlinan.mylife.entity.primary.repository.HPShopKindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/1/22
 * Time : 23:00
 */
@Service
public class KindService {

    @Autowired
    HPShopKindRepository hpShopKindRepository;

    /**
     *DESC:获取种类所属全路径  以   1#2#3 形式
     *@param:  kindId
     *@return:  java.lang.String
     *@author hou.linan
     *@date:  2020/1/22 18:25
     */
    public Map<String , String > getKindDN(String kindId)throws Exception{

        Map<String , String > result = new HashMap<>();
        String kindName = "";
        String kindResultId = "";

        HPShopKind hpShopKindById = hpShopKindRepository.findHPShopKindById(kindId);
        if(hpShopKindById == null ) throw new Exception("获取所属路径失败，没有找到改种类信息");

        if(hpShopKindById.getKindLevel() == 0 ) {
            kindName = hpShopKindById.getKindName();
            kindResultId = hpShopKindById.getId();
            result.put("name" , kindName);
            result.put("id" , kindResultId);
            return result ;
        }

        String currKindName = hpShopKindById.getKindName();

        HPShopKind hpShopKindById1 = hpShopKindRepository.findHPShopKindById(hpShopKindById.getParentKindId());

        if(hpShopKindById1 == null ) throw new Exception("获取所属路径失败");

        if(hpShopKindById1.getKindLevel() == 0){
            kindName = hpShopKindById1.getKindName()  + "#" + currKindName ;
            kindResultId = hpShopKindById1.getId() ;
            result.put("name" , kindName);
            result.put("id" , kindResultId);
            return result ;
        }

        HPShopKind hpShopKindById2 = hpShopKindRepository.findHPShopKindById(hpShopKindById1.getParentKindId());

        if(hpShopKindById2 == null ) throw new Exception("获取所属路径失败");

        kindName =  hpShopKindById2.getKindName() + "#" +   hpShopKindById1.getKindName()  + "#"  + currKindName;
        kindResultId = hpShopKindById2.getId();

        result.put("name" , kindName);
        result.put("id" , kindResultId);
        return result ;


    }

}
