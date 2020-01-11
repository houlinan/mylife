package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.entity.HPShopKind;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.HPShopKindRepository;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/12/31
 * Time : 13:17
 */
@RequestMapping("/hp/kind")
@RestController
@Slf4j
@Api(value = "鹤品种类相关类", tags = "鹤品种类相关类")
public class HPKindController {

    @Autowired
    HPShopKindRepository hpShopKindRepository  ;

    @Autowired
    Sid sid ;

    @ResponseBody
    @RequestMapping("/create")
    public HHJSONResult createKind(User user, HPShopKind hpShopKind){

        String shopId = user.getShopId() ;

        HPShopKind hpShopKindById = null;

        if(CMyString.isEmpty(hpShopKind.getId())){
            hpShopKind.setId(sid.nextShort());
            hpShopKindById = new HPShopKind ();
            BeanUtils.copyProperties(hpShopKind, hpShopKindById);
        }else{
            hpShopKindById = hpShopKindRepository.findHPShopKindById(hpShopKind.getId());
            if(hpShopKindById != null){
                BeanUtils.copyProperties(hpShopKind, hpShopKindById);
            }else{
                hpShopKindById = new HPShopKind ();
                hpShopKindById = hpShopKind ;
            }
        }

        if(CMyString.isEmpty(hpShopKindById.getParentKindId())){

            hpShopKindById.setParentKindId(shopId);
            hpShopKindById.setKindLevel(0);
        }

        if(CMyString.isEmpty(hpShopKindById.getShopId())){
            hpShopKindById.setShopId(shopId);
        }

        if(CMyString.isEmpty(hpShopKindById.getShopAdminUserId())){
            hpShopKindById.setShopAdminUserId(user.getId());
        }

        if(hpShopKindById.getKindLevel() == null ){
            if(!hpShopKindById.getParentKindId().equals(shopId)) {
                HPShopKind hpShopKindById1 = hpShopKindRepository.findHPShopKindById(hpShopKindById.getParentKindId());
                if(hpShopKindById1 == null) return HHJSONResult.errorMsg("没有找到上级分类信息");
                hpShopKindById.setKindLevel(hpShopKindById1.getKindLevel() + 1 );
            }else {
                hpShopKindById.setKindLevel(1);
            }

        }

        hpShopKindRepository.save(hpShopKindById);


        return HHJSONResult.ok();
    }

    @RequestMapping("/delete")
    public HHJSONResult delete(String id , User user){

        HPShopKind hpShopKindById = hpShopKindRepository.findHPShopKindById(id);

        if(hpShopKindById == null ) return HHJSONResult.errorMsg("没有找到该种类");

        if(!hpShopKindById.getShopAdminUserId().equals(user.getId())) return HHJSONResult.errorMsg("不属于您的种类无法删除");

        hpShopKindRepository.delete(hpShopKindById);

        return HHJSONResult.ok();
    }

    @RequestMapping("/getFirstLevel")
    public HHJSONResult getFirstLevel(String parentId){

//        String parentId = LinLinLin_LinTaoConstent.SHOP_ID;

        List<HPShopKind> hpShopKindsByParentKindId = hpShopKindRepository.findHPShopKindsByParentKindId(parentId);

        if(ObjectUtils.isEmpty(hpShopKindsByParentKindId)) return HHJSONResult.errorMsg("没有找到任何分类");


        JSONArray resultArray = new JSONArray();
        List<String> nameList = new ArrayList<>();
//        nameList.add("请选择");
        for (int i = 0; i < hpShopKindsByParentKindId.size(); i++) {
            HPShopKind hpShopKind =  hpShopKindsByParentKindId.get(i);
            JSONObject object = new JSONObject();
            object.put("id",hpShopKind.getId());
            object.put("title",hpShopKind.getKindName() );
            object.put("name",hpShopKind.getKindPinYin() );
            object.put("color",hpShopKind.getKindColor() );
            object.put("icon",hpShopKind.getIcon() );
            resultArray.add(object);
            nameList.add(hpShopKind.getKindName());
        }

        JSONObject result = new JSONObject();
        result.put("nameList" , nameList);
        result.put("resultArray",resultArray);

        return HHJSONResult.ok(result);
    }


    @RequestMapping("/getThirdKindList")
    public HHJSONResult getThirdKindList(String shopId){

        //获取所有

        List<HPShopKind> hpShopKindsByShopId = hpShopKindRepository.findHPShopKindsByShopId(shopId);
        if(hpShopKindsByShopId.size() == 0 ) return HHJSONResult.errorMsg("您没有创建过任何分类");

        Map<String,List<HPShopKind>> allKindMap = new HashMap<>();
        for (int i = 0; i < hpShopKindsByShopId.size(); i++) {
            ArrayList<HPShopKind> sameParentKind = new ArrayList<>();
            HPShopKind hpShopKind =  hpShopKindsByShopId.get(i);
            if(allKindMap.get(hpShopKind.getParentKindId()) == null){
                sameParentKind.add(hpShopKind);
                allKindMap.put(hpShopKind.getParentKindId() , sameParentKind);
            }else{
                List<HPShopKind> hpShopKinds = allKindMap.get(hpShopKind.getParentKindId());
                hpShopKinds.add(hpShopKind);
                allKindMap.put(hpShopKind.getParentKindId() , hpShopKinds);
            }
        }


        JSONArray result = new JSONArray();
        //遍历第一层
        List<HPShopKind> hpShopKinds = allKindMap.get(shopId);

        for (int i = 0; i < hpShopKinds.size(); i++) {
            HPShopKind hpShopKind =  hpShopKinds.get(i);
            JSONObject currJSONDate = new JSONObject();
            currJSONDate.put("name", hpShopKind.getKindName());
            currJSONDate.put("id", hpShopKind.getId());

            getSecondTree(allKindMap, hpShopKind.getId(),  "dept"  ,  currJSONDate);
            result.add(currJSONDate);
        }

        return HHJSONResult.ok(result);
    }


    public void getSecondTree(Map<String,List<HPShopKind>> allKindMap , String parentId ,  String keyName , JSONObject currJSONDates){
        JSONArray result =  new JSONArray();
        List<HPShopKind> hpShopKinds = allKindMap.get(parentId);
        if(hpShopKinds == null ) return ;
        for (int i = 0; i < hpShopKinds.size(); i++) {
            HPShopKind hpShopKind =  hpShopKinds.get(i);
            JSONObject currJSONDate = new JSONObject();
            currJSONDate.put("name", hpShopKind.getKindName());
            currJSONDate.put("id", hpShopKind.getId());
            getSecondTree( allKindMap,hpShopKind.getId() , "product" ,currJSONDate);
        }
        currJSONDates.put(keyName ,result );
    }

}
