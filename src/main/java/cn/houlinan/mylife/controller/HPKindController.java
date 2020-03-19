package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.entity.HPShopKind;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.HPShopKindRepository;
import cn.houlinan.mylife.service.KindService;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.Api;
import javassist.expr.Cast;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
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
    HPShopKindRepository hpShopKindRepository;

    @Autowired
    Sid sid;

    @Autowired
    KindService kindService;

    @ResponseBody
    @RequestMapping("/create")
    public HHJSONResult createKind(User user, HPShopKind hpShopKind) throws Exception{

        String shopId = user.getShopId();

        HPShopKind hpShopKindById = null;

        if (CMyString.isEmpty(hpShopKind.getId())) {
            hpShopKind.setId(sid.nextShort());
            hpShopKindById = new HPShopKind();
            BeanUtils.copyProperties(hpShopKind, hpShopKindById);
        } else {
            hpShopKindById = hpShopKindRepository.findHPShopKindById(hpShopKind.getId());
            if (hpShopKindById != null) {
                BeanUtils.copyProperties(hpShopKind, hpShopKindById);
            } else {
                hpShopKindById = new HPShopKind();
                hpShopKindById = hpShopKind;
            }
        }

        if (CMyString.isEmpty(hpShopKindById.getParentKindId())) {

            hpShopKindById.setParentKindId(shopId);
            hpShopKindById.setKindLevel(0);
        }

        if (CMyString.isEmpty(hpShopKindById.getShopId())) {
            hpShopKindById.setShopId(shopId);
        }

        if (CMyString.isEmpty(hpShopKindById.getShopAdminUserId())) {
            hpShopKindById.setShopAdminUserId(user.getId());
        }

        if (hpShopKindById.getKindLevel() == null) {
            if (!hpShopKindById.getParentKindId().equals(shopId)) {
                HPShopKind hpShopKindById1 = hpShopKindRepository.findHPShopKindById(hpShopKindById.getParentKindId());
                if (hpShopKindById1 == null) return HHJSONResult.errorMsg("没有找到上级分类信息");
                hpShopKindById.setKindLevel(hpShopKindById1.getKindLevel() + 1);
            } else {
                hpShopKindById.setKindLevel(1);
            }

        }

        if(hpShopKindById.getKindLevel() == 0){
            hpShopKindById.setKindDN("#" + hpShopKindById.getId());
        }else{
            hpShopKindById.setKindDN(kindService.getKindDN(hpShopKindById.getId()).get("name"));
        }
        hpShopKindRepository.save(hpShopKindById);


        return HHJSONResult.ok();
    }

    @RequestMapping("/delete")
    public HHJSONResult delete(String id, User user) {

        HPShopKind hpShopKindById = hpShopKindRepository.findHPShopKindById(id);

        if (hpShopKindById == null) return HHJSONResult.errorMsg("没有找到该种类");

        if (!hpShopKindById.getShopAdminUserId().equals(user.getId())) return HHJSONResult.errorMsg("不属于您的种类无法删除");

        hpShopKindRepository.delete(hpShopKindById);

        return HHJSONResult.ok();
    }

    @RequestMapping("/getFirstLevel")
    public HHJSONResult getFirstLevel(String parentId) {

//        String parentId = LinLinLin_LinTaoConstent.SHOP_ID;

        List<HPShopKind> hpShopKindsByParentKindId = hpShopKindRepository.findHPShopKindsByParentKindId(parentId);

        if (ObjectUtils.isEmpty(hpShopKindsByParentKindId)) return HHJSONResult.errorMsg("没有找到任何分类");


        JSONArray resultArray = new JSONArray();
        List<String> nameList = new ArrayList<>();
//        nameList.add("请选择");
        for (int i = 0; i < hpShopKindsByParentKindId.size(); i++) {
            HPShopKind hpShopKind = hpShopKindsByParentKindId.get(i);
            JSONObject object = new JSONObject();
            object.put("id", hpShopKind.getId());
            object.put("title", hpShopKind.getKindName());
            object.put("name", hpShopKind.getKindPinYin());
            object.put("color", hpShopKind.getKindColor());
            object.put("icon", hpShopKind.getIcon());
            resultArray.add(object);
            nameList.add(hpShopKind.getKindName());
        }

        JSONObject result = new JSONObject();
        result.put("nameList", nameList);
        result.put("resultArray", resultArray);

        return HHJSONResult.ok(result);
    }


    @RequestMapping("/getThirdKindList")
    public HHJSONResult getThirdKindList(String shopId) {

        //获取所有

        List<HPShopKind> hpShopKindsByShopId = hpShopKindRepository.findHPShopKindsByShopId(shopId);
        if (hpShopKindsByShopId.size() == 0) return HHJSONResult.errorMsg("您没有创建过任何分类");

        MultiValueMap<String, HPShopKind> multiValueMap = new LinkedMultiValueMap<>();

        hpShopKindsByShopId.stream().forEach(e -> multiValueMap.add(e.getParentKindId(), e));

        List<HPShopKind> hpShopKinds = multiValueMap.get(shopId);
        JSONArray result = new JSONArray();
        JSONObject initPickerProduct = new JSONObject();
        initPickerProduct.put("name" , "请选择");
        initPickerProduct.put("id" , 0 );
        initPickerProduct.put("product" ,new JSONArray());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(initPickerProduct);
        JSONObject initPicker = new JSONObject();
        initPicker.put("name" , "请选择");
        initPicker.put("id" , 0 );
        initPicker.put("dept" ,jsonArray);

        result.add(initPicker);
        result.addAll(makeKindTree(multiValueMap, shopId));
        return HHJSONResult.ok(result);
    }

    private JSONArray makeKindTree(MultiValueMap<String, HPShopKind> multiValueMap, String parentId ) {
        List<HPShopKind> hpShopKinds = multiValueMap.get(parentId);
        JSONArray jsonArray = new JSONArray();
        if (hpShopKinds != null) {
            for (int i = 0; i < hpShopKinds.size(); i++) {
                HPShopKind hpShopKind = hpShopKinds.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", hpShopKind.getKindName());
                jsonObject.put("id", hpShopKind.getId());
                jsonObject.put(getParentKindName(hpShopKind.getKindLevel()), makeKindTree(multiValueMap, hpShopKind.getId()));
                jsonArray.add(jsonObject);
            }
        }

        return jsonArray;
    }

    public String getParentKindName(int kingLevel) {
        String result = "dept";
        switch (kingLevel) {
            case 0:
                result = "dept";
                break;
            case 1:
                result = "product";
                break;
            case 2:
                result = "product";
                break;
        }
        return result;
    }

    @RequestMapping("/getKindTreeByFirstKindId")
    @ResponseBody
    public HHJSONResult getKindTreeByFirstKindId(String kindId , String shopId)throws Exception{

        HPShopKind hpShopKindById = hpShopKindRepository.findHPShopKindById(kindId);
        if(hpShopKindById == null){
            throw new Exception("没有找到您选择的种类");
        }

        //获取所有

        List<HPShopKind> hpShopKindsByShopId = hpShopKindRepository.findHPShopKindsByShopId(shopId);
        if (hpShopKindsByShopId.size() == 0) return HHJSONResult.errorMsg("您没有创建过任何分类");

        MultiValueMap<String, HPShopKind> multiValueMap = new LinkedMultiValueMap<>();

        hpShopKindsByShopId.stream().forEach(e -> multiValueMap.add(e.getParentKindId(), e));

        List<HPShopKind> hpShopKinds = multiValueMap.get(shopId);
        JSONArray result = new JSONArray();
        JSONObject initPickerProduct = new JSONObject();
        initPickerProduct.put("name" , "全部");
        initPickerProduct.put("id" , 0 );
        initPickerProduct.put("product" ,new JSONArray());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(initPickerProduct);
        JSONObject initPicker = new JSONObject();
        initPicker.put("name" , "全部");
        initPicker.put("id" , 0 );
        initPicker.put("dept" ,jsonArray);

        result.add(initPicker);
        result.addAll(makeKindTreeForFirst(multiValueMap, kindId));
        return HHJSONResult.ok(result);
    }


    private JSONArray makeKindTreeForFirst(MultiValueMap<String, HPShopKind> multiValueMap, String parentId ) {
        List<HPShopKind> hpShopKinds = multiValueMap.get(parentId);
        JSONArray jsonArray = new JSONArray();
        if (hpShopKinds != null) {
            for (int i = 0; i < hpShopKinds.size(); i++) {
                HPShopKind hpShopKind = hpShopKinds.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", hpShopKind.getKindName());
                jsonObject.put("id", hpShopKind.getId());
                jsonObject.put(getParentKindNameForFirst(hpShopKind.getKindLevel()), makeKindTree(multiValueMap, hpShopKind.getId()));
                jsonArray.add(jsonObject);
            }
        }

        return jsonArray;
    }

    public String getParentKindNameForFirst(int kingLevel) {
        String result = "dept";
        switch (kingLevel) {
            case 1:
                result = "dept";
                break;
            case 2:
                result = "product";
                break;
        }
        return result;
    }

}
