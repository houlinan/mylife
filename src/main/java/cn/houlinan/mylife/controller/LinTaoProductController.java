package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.DTO.PhotoVOResult;
import cn.houlinan.mylife.constant.HPConstant;
import cn.houlinan.mylife.constant.LinLinLin_LinTaoConstent;
import cn.houlinan.mylife.entity.*;
import cn.houlinan.mylife.entity.primary.repository.HPProductPicListRepository;
import cn.houlinan.mylife.entity.primary.repository.HPProductRepository;
import cn.houlinan.mylife.entity.primary.repository.HPShopKindRepository;
import cn.houlinan.mylife.entity.primary.repository.HPShopRepository;
import cn.houlinan.mylife.service.KindService;
import cn.houlinan.mylife.service.PhotoService;
import cn.houlinan.mylife.service.common.MyPage;
import cn.houlinan.mylife.service.common.PrimaryBaseService;
import cn.houlinan.mylife.utils.BeanValidator;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import com.google.common.collect.Maps;
import com.sun.media.jfxmedia.logging.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * DESC：林琳琳美鞋店
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/11/28
 * Time : 9:43
 */
@RequestMapping("/linlinlin/product")
@Controller
@Slf4j
@Api(value = "鹤品商品相关类", tags = "鹤品商品相关类")
public class LinTaoProductController {

    @Autowired
    HPProductRepository hpProductRepository;

    @Autowired
    Sid sid;

    @Autowired
    HPShopRepository hpShopRepository;

    @Autowired
    PhotoService photoService;

    @Autowired
    HPProductPicListRepository hpProductPicListRepository;

    @Autowired
    PrimaryBaseService primaryBaseService;

    @Autowired
    HPShopKindRepository hpShopKindRepository;

    @Autowired
    KindService kindService;

    @ResponseBody
    @PostMapping("/save")
    @ApiOperation(value = "保存鹤品商品", notes = "保存鹤品商品。支持update")
    public HHJSONResult saveHPProduct(HPProduct hpProduct, User user,
                                      @ApiParam(value = "上传文件", required = false)
                                      @RequestParam(name = "productHeadPicFile", required = false) MultipartFile productHeadPic) {


        if (CMyString.isEmpty(hpProduct.getId()))
            hpProduct.setId(sid.nextShort());


//        if(CMyString.isEmpty(hpProduct.getShopId()))
//            return HHJSONResult.errorMsg("所属店铺信息为空");

        BeanValidator.check(hpProduct);

//        String shopId = "linlinlin-LinTao";

        hpProduct.setShopId(LinLinLin_LinTaoConstent.SHOP_ID);
        String headPicPath = HPConstant.HP_SHOP_PICS_PATH + LinLinLin_LinTaoConstent.SHOP_ID + File.separator +
                LinLinLin_LinTaoConstent.FOLDER_NAME + File.separator;

        //处理方面图片的上传
        photoService.uploadPicForHP(productHeadPic, headPicPath, true, "https://www.houlinan.cn/mylife/img");
        hpProduct.setProductHeadPic(headPicPath);

        log.info("林涛美鞋店商品保存" + hpProduct.toString());

        return HHJSONResult.ok(hpProductRepository.save(hpProduct));
    }

    @PostMapping("/saveProductPics")
    @ResponseBody
    public HHJSONResult saveProductPics(@RequestParam(value = "productId", required = false) String productId
            , @RequestParam(name = "productPics", required = false) MultipartFile[] productPics, User user) {

        if (CMyString.isEmpty(productId)) return HHJSONResult.errorMsg("传入商品Id为空");
        HPProduct hpProductById = hpProductRepository.findHPProductById(productId);
        if (hpProductById == null) return HHJSONResult.errorMsg("没有找到相应商品");


        for (int i = 0; i < productPics.length; i++) {
            MultipartFile currFile = productPics[i];
            String currPicId = sid.nextShort();
            String picPath = HPConstant.HP_SHOP_PICS_PATH + LinLinLin_LinTaoConstent.SHOP_ID + File.separator +
                    LinLinLin_LinTaoConstent.FOLDER_NAME + File.separator + productId + File.separator;

            //处理方面图片的上传
            String httpAddress = photoService.uploadPicForHP(currFile, picPath, true, "https://www.houlinan.cn/mylife/img");

            HPProductPicList picList = new HPProductPicList();
            picList.setPicSrc(httpAddress);
            picList.setProductId(productId);
            picList.setUploadUserId(user.getId());
            picList.setId(sid.nextShort());

            hpProductPicListRepository.save(picList);

        }
        return HHJSONResult.ok();
    }


    @GetMapping("/findProductById")
    @ResponseBody
    public HHJSONResult findProductById(@RequestParam(name = "productId", required = false) String productId, User user) {

        if (CMyString.isEmpty(productId)) return HHJSONResult.errorMsg("传入商品id为空");

        HPProduct hpProductById = hpProductRepository.findHPProductById(productId);
        if (hpProductById == null) return HHJSONResult.errorMsg("没有找到相应商品");

        //查询商品相关图片
        List<HPProductPicList> hpProductPicListsByProductId = hpProductPicListRepository.findHPProductPicListsByProductId(productId);

        JSONObject jsonObject = JSONObject.fromObject(hpProductById);

        jsonObject.put("picList", JSONArray.fromObject(hpProductPicListsByProductId));

        return HHJSONResult.ok(jsonObject.toString());
    }

    @GetMapping("/queryProducts")
    @ResponseBody
    public HHJSONResult queryProduct(@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex,
                                     @RequestParam(name = "pageSize", defaultValue = "15") int pageSize) throws Exception {

        String sql = " FROM hpproduct WHERE shopid = '" + LinLinLin_LinTaoConstent.SHOP_ID + "'";

        MyPage<HPProduct> photoMyPage = primaryBaseService.executeSqlByPage("SELECT * " + sql, "SELECT COUNT(1) " + sql,
                Maps.newHashMap(), pageIndex, pageSize, HPProduct.class);

        return HHJSONResult.ok(photoMyPage);
    }

    @PostMapping("/saveProduct")
    @ResponseBody
    public HHJSONResult saveProduct(
            @RequestParam(name = "productPic", required = false) MultipartFile productPic,

            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "desc", required = false) String desc,
            @RequestParam(name = "yuanjia", required = false) double yuanjia,
            @RequestParam(name = "xianjia", required = false) double xianjia,
            @RequestParam(name = "kucun", required = false) String kucun,
            @RequestParam(name = "parentId", required = false) String parentId,
            @RequestParam(name = "yunfei", required = false) String yunfei,
            @RequestParam(name = "id", required = false , defaultValue = "") String id
            , User user) throws Exception {

        if (CMyString.isEmpty(title)) throw new Exception("商品名称为空");
        if (CMyString.isEmpty(parentId)) throw new Exception("商品分类为空");
        if (productPic == null) throw new Exception("商品图片必传");


        //保存数据
        HPProduct build = new HPProduct( );

        if(!CMyString.isEmpty(id)){
            HPProduct hpProductById = hpProductRepository.findHPProductById(id);
            if(hpProductById == null ) return HHJSONResult.errorMsg("没有找到要修改的商品");
        }else{
            build.setId(sid.nextShort());
        }

        build.setProductHeadPic("");
        build.setShopId(LinLinLin_LinTaoConstent.SHOP_ID);
        build.setTitle(title);
        build.setFreight(yunfei + "");
        build.setStockNumber(Integer.valueOf(kucun));
        build.setOrgPrice(yuanjia);
        build.setPrice(xianjia);
        build.setProductDesc(desc);
        build.setKindDN(kindService.getKindDN(parentId).get("name"));
        build.setRootKindId(kindService.getKindDN(parentId).get("id"));
        hpProductRepository.save(build);


        String picPath = HPConstant.HP_SHOP_PICS_PATH + LinLinLin_LinTaoConstent.SHOP_ID + File.separator +
                LinLinLin_LinTaoConstent.FOLDER_NAME + File.separator + build.getId() + File.separator;
        //处理方面图片的上传
        String httpAddress = photoService.uploadPicForHP(productPic, picPath, true, "https://www.houlinan.cn/mylife/img/");

        build.setProductHeadPic(httpAddress);
        hpProductRepository.save(build);

        return HHJSONResult.ok(build);
    }


    public String getFirstKindId(String currId){
        HPShopKind hpShopKindById = hpShopKindRepository.findHPShopKindById(currId);
        if(hpShopKindById == null)  return "";

        return  "";
    }






}
