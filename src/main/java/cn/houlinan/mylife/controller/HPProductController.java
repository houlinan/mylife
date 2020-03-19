package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.constant.HPConstant;
import cn.houlinan.mylife.entity.*;
import cn.houlinan.mylife.entity.primary.repository.HPProductRepository;
import cn.houlinan.mylife.entity.primary.repository.HPShopKindRepository;
import cn.houlinan.mylife.entity.primary.repository.HPShopRepository;
import cn.houlinan.mylife.service.PhotoService;
import cn.houlinan.mylife.service.common.MyPage;
import cn.houlinan.mylife.service.common.PrimaryBaseService;
import cn.houlinan.mylife.utils.BeanValidator;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/11/28
 * Time : 9:43
 */
@RequestMapping("/hp/product")
@Controller
@Slf4j
@Api(value = "鹤品商品相关类", tags = "鹤品商品相关类")
public class HPProductController {

    @Autowired
    HPProductRepository hpProductRepository;

    @Autowired
    HPShopKindRepository hpShopKindRepository;

    @Autowired
    Sid  sid ;

    @Autowired
    HPShopRepository hpShopRepository ;

    @Autowired
    PhotoService photoService;

    @Autowired
    PrimaryBaseService primaryBaseService;


    @ResponseBody
    @PostMapping("/save")
    @ApiOperation(value = "保存鹤品商品"  , notes = "保存鹤品商品。支持update")
    public HHJSONResult saveHPProduct(HPProduct hpProduct , User user,
                                      @ApiParam(value = "上传文件",required = false)
                                      @RequestParam(name = "productHeadPic" , required = false) MultipartFile productHeadPic ){

        log.info( "" + hpProduct.toString());

        if(CMyString.isEmpty(hpProduct.getId()) )
            hpProduct.setId(sid.nextShort());


        if(CMyString.isEmpty(hpProduct.getShopId()))
            return HHJSONResult.errorMsg("所属店铺信息为空");

        BeanValidator.check(hpProduct);

        String shopId = hpProduct.getShopId() ;

        HPShop hpShopById = hpShopRepository.findHPShopById(shopId);

        if(hpShopById ==  null )  return HHJSONResult.errorMsg("没有找到改店铺的信息");
        if(!"admin".equals(user.getUserName()) && !user.getId().equals(hpShopById.getAdminUserId())) return HHJSONResult.errorMsg("店铺并不属于你");

        //处理方面图片的上传
        photoService.copyFileToPath(productHeadPic , HPConstant.HP_SHOP_PICS_PATH + hpProduct.getId());

        return HHJSONResult.ok( hpProductRepository.save(hpProduct));
    }

//    public HHJSONResult uploadPicsForProduct(
//            @ApiImplicitParam(name = "shopName", value = "店铺名称", required = true, dataType = "String",
//                    paramType="query" ,defaultValue = "一个很好听的店铺"),
//            User user,
//            @ApiParam(value = "上传文件",required = false)
//            @RequestParam(name = "productPics" , required = false) MultipartFile productPics
//    ){
//        return HHJSONResult.ok();
//    }


    @RequestMapping("/getShopProductsByKindId")
    @ResponseBody
    public HHJSONResult getShopProductsByKindId(@RequestParam(name = "shopId",required = false)String kindId,
                                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize ,
                                                @RequestParam(name = "pageNum", defaultValue = "1") int pageNum ,
                                                @RequestParam(name = "searchValue" ,defaultValue = "")String searchValue)throws Exception{

        HPShopKind hpShopKindById = hpShopKindRepository.findHPShopKindById(kindId);
        if(hpShopKindById == null ) return HHJSONResult.errorMsg("没有找到对应分类信息");

        String selectSql = " FROM hpproduct WHERE ROOTKINDID = '" + kindId +"'" ;
        if(CMyString.isEmpty(searchValue)){
            selectSql += " AND rootkindid LIKE '%" + searchValue + "%'";
        }

        MyPage<HPProduct> photoMyPage = primaryBaseService.executeSqlByPage("SELECT * " + selectSql, "SELECT COUNT(1) " + selectSql,
                Maps.newHashMap(), pageNum, pageSize, HPProduct.class);


        return HHJSONResult.ok(photoMyPage);
    }


}
