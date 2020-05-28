package cn.houlinan.mylife.controller;
import java.util.Date;

import cn.houlinan.mylife.entity.HPShop;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.HPShopRepository;
import cn.houlinan.mylife.service.HPShopService;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/11/16
 * Time : 9:40
 */
@RequestMapping("/hp/shop")
@Controller
@Slf4j
@Api(value = "鹤品店铺相关类", tags = "鹤品店铺相关类")
public class HPShopController {

    @Autowired
    HPShopService hpShopService;

    @Autowired
    HPShopRepository hpShopRepository;

    @Autowired
    Sid sid;



    @ResponseBody
    @PostMapping("/createShop")
    @ApiOperation(value = "注册店铺", notes = "注册店铺的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registCode", value = "注册码", required = true, dataType = "String",
                    paramType="query"),
            @ApiImplicitParam(name = "shopName", value = "店铺名称", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "一个很好听的店铺"),
            @ApiImplicitParam(name = "shopDesc", value = "店铺简介", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "一个很好听的店铺简介"),
            @ApiImplicitParam(name = "mobile", value = "店主手机号", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "17625997779"),
            @ApiImplicitParam(name = "address", value = "店铺地址", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "黑龙江省鹤岗市"),
            @ApiImplicitParam(name = "openTime", value = "营业时间", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "朝九晚五"),
            @ApiImplicitParam(name = "headPic", value = "店铺头像", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "http://www.baidu.com"),
            @ApiImplicitParam(name = "admin2RCode", value = "店主二维码", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "http://www.baidu.com/店主"),
    })
    public HHJSONResult createShop(@RequestParam(name = "registCode", required = false) String registCode ,
                                   @RequestParam(name = "shopName", required = false) String shopName ,
                                   @RequestParam(name = "shopDesc", required = false) String shopDesc ,
                                   @RequestParam(name = "mobile", required = false) String mobile ,
                                   @RequestParam(name = "address", required = false) String address ,
                                   @RequestParam(name = "openTime", required = false) String openTime ,
                                   @RequestParam(name = "headPic", required = false) String headPic ,
                                   @RequestParam(name = "admin2RCode", required = false) String admin2RCode ,
                                   User user){

        if(CMyString.isEmpty(registCode)) return HHJSONResult.errorMsg("传入的注册码为空");
        if(CMyString.isEmpty(shopName)) return HHJSONResult.errorMsg("传入的店铺名称为空");
        if(CMyString.isEmpty(mobile)) return HHJSONResult.errorMsg("传入的店铺手机号为空");


        //验证二维码是否可用
        if(!hpShopService.checkRegistCode(registCode)) return HHJSONResult.errorMsg("注册码错误");

        try{

            String[] registDescByDESCode = hpShopService.getRegistDescByDESCode(registCode);
            JSONObject registCodeJson = hpShopService.getRegistCodeJson(registDescByDESCode);
            //解析注册码

            HPShop hpShop = new HPShop();
            hpShop.setShopName(shopName);
            hpShop.setShopDesc(shopDesc);
            hpShop.setMobile(mobile);
            hpShop.setAddress(address);
            hpShop.setOpenTime(openTime);
            hpShop.setHeadPic(headPic);
            hpShop.setAdmin2RCode(admin2RCode);
            hpShop.setIsTop(registCodeJson.getInt("isTop"));
            hpShop.setAdminNum(registCodeJson.getInt("adminNum"));
            hpShop.setProductNum(registCodeJson.getInt("productNum"));
            hpShop.setPicNum(registCodeJson.getInt("picNum"));
            hpShop.setProductOutTime(registCodeJson.getInt("productOutTime"));
            hpShop.setId(sid.nextShort());
            hpShop.setCrTime(new Date());
            hpShop.setUpdateTime(new Date());
            hpShop.setTeamid(0L);
            hpShopRepository.save(hpShop);

            return HHJSONResult.ok(hpShop);
        }catch(Throwable throwable){
            throwable.printStackTrace();
            return HHJSONResult.errorMsg(throwable.getMessage());
        }

    }


    @ResponseBody
    @PostMapping("/updateShop")
    @ApiOperation(value = "修改店铺", notes = "修改店铺的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "String",
                    paramType="query"),
            @ApiImplicitParam(name = "shopDesc", value = "店铺简介", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "一个很好听的店铺简介"),
            @ApiImplicitParam(name = "mobile", value = "店主手机号", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "17625997779"),
            @ApiImplicitParam(name = "address", value = "店铺地址", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "黑龙江省鹤岗市"),
            @ApiImplicitParam(name = "openTime", value = "营业时间", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "朝九晚五"),
            @ApiImplicitParam(name = "headPic", value = "店铺头像", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "http://www.baidu.com"),
            @ApiImplicitParam(name = "admin2RCode", value = "店主二维码", required = true, dataType = "String",
                    paramType="query" ,defaultValue = "http://www.baidu.com/店主"),
    })
    public HHJSONResult updateHPShop( @RequestParam(name = "shopId", required = false) String shopId ,
                                      @RequestParam(name = "shopDesc", required = false) String shopDesc ,
                                      @RequestParam(name = "mobile", required = false) String mobile ,
                                      @RequestParam(name = "address", required = false) String address ,
                                      @RequestParam(name = "openTime", required = false) String openTime ,
                                      @RequestParam(name = "headPic", required = false) String headPic ,
                                      @RequestParam(name = "admin2RCode", required = false) String admin2RCode ,
                                      User user) {

        if (CMyString.isEmpty(shopId)) return HHJSONResult.errorMsg("传入的店铺ID为空");
        if (CMyString.isEmpty(mobile)) return HHJSONResult.errorMsg("传入的店铺手机号为空");

        HPShop hpShopById = hpShopRepository.findHPShopById(shopId);
        if(hpShopById == null ) return HHJSONResult.errorMsg("没有找到对应的店铺信息");
        if(!hpShopById.getAdminUserId().equals(user.getId())) return HHJSONResult.errorMsg("您不是创建者无权修改");


        hpShopById.setShopDesc(shopDesc);
        hpShopById.setMobile(mobile);
        hpShopById.setAddress(address);
        hpShopById.setOpenTime(openTime);
        hpShopById.setHeadPic(headPic);
        hpShopById.setAdmin2RCode(admin2RCode);

        hpShopRepository.save(hpShopById);

        return HHJSONResult.ok(hpShopById);
    }



}
