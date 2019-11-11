package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.constant.HPConstant;
import cn.houlinan.mylife.entity.RegistrationCode;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.RegistrationCodeRepository;
import cn.houlinan.mylife.service.HPShopService;
import cn.houlinan.mylife.utils.DesEncryptUtil;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.Name;
import java.util.Date;
import java.util.List;

/**
 * DESC：鹤品工具类controller
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/11/9
 * Time : 14:08
 */
@RequestMapping("/hp/utils")
@Controller
@Slf4j
@Api(value = "鹤品相关工具类", tags = "鹤品相关工具类")
public class HPUtilsController {

    @Autowired
    Sid sid ;

    @Autowired
    RegistrationCodeRepository registrationCodeRepository ;

    @Autowired
    HPShopService hpShopService ;


    @GetMapping("/create100Code")
    @ResponseBody
    public HHJSONResult create100RegistCode(){

        for (int a =  0 ;a < 100 ; a ++){
            RegistrationCode code = new RegistrationCode( );
            code.setId(sid.nextShort());
            code.setCrTime(new Date());
            registrationCodeRepository.save(code);
        }
        return HHJSONResult.ok();
    }


    @GetMapping("/getShopRegistCode")
    @ResponseBody
    /**
     * 注册码分为几个项目
     * 1.是否置顶 ： 0非置顶。1置顶
     * 2.管理员数量  1——2——3——4——5
     * 3.可上传商品数量 20——30——40——50——60——70——80——90——100
     * 4.单品可上传图片数量 2——3——4——5——6——7
     * 5.商品有效时长 5——8——11——永久
     * */

    @ApiImplicitParams({
            @ApiImplicitParam(name = "isTop", value = "是否置顶", required = false, dataType = "int", paramType="query", defaultValue = "0"),
            @ApiImplicitParam(name = "adminNum", value = "管理员数量", required = false, dataType = "int", paramType="query", defaultValue = "1"),
            @ApiImplicitParam(name = "productNum", value = "商品数量", required = false, dataType = "int", paramType="query", defaultValue = "20"),
            @ApiImplicitParam(name = "picNum", value = "可上传图片数量", required = false, dataType = "int", paramType="query", defaultValue = "2"),
            @ApiImplicitParam(name = "productOutTime", value = "商品超时时间", required = false, dataType = "int", paramType="query", defaultValue = "5"),
    })
    @ApiOperation(value = "创建一个店铺注册码" , notes = "创建一个店铺注册码")
    @Transactional
    public HHJSONResult getShopRegistCode(User user ,
         @RequestParam(name = "isTop" , defaultValue = "0")Integer isTop,
         @RequestParam(name = "adminNum" , defaultValue = "1")Integer adminNum ,
         @RequestParam(name = "productNum",defaultValue = "20")Integer productNum,
         @RequestParam(name = "picNum" ,defaultValue = "2")Integer picNum ,
         @RequestParam(name = "productOutTime" ,defaultValue = "5")Integer productOutTime
    )throws Exception{

        if(!"admin".equals(user.getUserName())) return HHJSONResult.errorMsg("您不是超管，无法创建");

        List<RegistrationCode> registrationCodeByIsUserd = registrationCodeRepository.findRegistrationCodesByIsUserdAndIsCreate(0 , 0);

        log.info( "" + registrationCodeByIsUserd.size());

        RegistrationCode registrationCode = registrationCodeByIsUserd.get(0);

        String id = registrationCode.getId();

        String data = id + "_" + isTop + "_" + adminNum + "_" + productNum + "_" + picNum + "_" + productOutTime ;

        String base64Encoded = new String(Base64.encodeBase64(data.getBytes("UTF-8")));
        // 3-3） 对data进行加密处理 并将结果转换成16进制表示
        String dataAfterDESEncode =
                DesEncryptUtil.encryptToHex(base64Encoded.getBytes("UTF-8"), HPConstant.DES_KEY);

        registrationCode.setDesData(dataAfterDESEncode);
        JSONObject attribute = new JSONObject();
        attribute.put("isTop" , isTop );
        attribute.put("adminNum" , adminNum );
        attribute.put("productNum" , productNum) ;
        attribute.put("picNum" , picNum );
        attribute.put("productOutTime" , productOutTime) ;
        registrationCode.setAttribute(attribute.toString());
        registrationCode.setIsCreate(1);
        registrationCodeRepository.save(registrationCode);

        return HHJSONResult.ok(dataAfterDESEncode);
    }

    @GetMapping("/checkRegistCode")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registCode", value = "验证码", required = false, dataType = "String", paramType="query", defaultValue = "1f5cc797b0dfdce2166fa33870c1a55582ce19b657085d8460b6913d2649cb7832b133dab22c1395"),
    })
    @ApiOperation(value = "验证一个注册码" , notes = "验证注册码")
    @Transactional
    public HHJSONResult checkRegistCode(@RequestParam(name = "registCode" , required = false)
                                        String registCode){


        return hpShopService.checkRegistCode(registCode)? HHJSONResult.ok() : HHJSONResult.errorMsg("");

    }

}
