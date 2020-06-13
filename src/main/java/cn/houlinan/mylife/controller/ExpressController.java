package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.entity.Express;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.ExpressRepository;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.service.kuaidi.ExpressService;
import cn.houlinan.mylife.service.kuaidi.InitService;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/8
 * Time : 21:15
 */
@RestController
@RequestMapping("/express")
@Slf4j
@Api(value = "快递相关controller", tags = "快递相关controller")
public class ExpressController {

    @Autowired
    Sid sid ;

    @Autowired
    UserRepository userRepository ;

    @Autowired
    ExpressRepository expressRepository ;

    @Autowired
    ExpressService expressService;

    @GetMapping("/saveExpress")
    @ApiOperation(value = "保存快递数据", notes = "保存快递数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", required = false, dataType = "String", paramType = "query", defaultValue = "test"),
            @ApiImplicitParam(name = "expressNumber", value = "快递单号", required = false, dataType = "String", paramType = "query", defaultValue = "2020-02-02"),
            @ApiImplicitParam(name = "expressName", value = "快递公司编号", required = false, dataType = "String", paramType = "query", defaultValue = "2020-02-02")
    })
    public HHJSONResult saveExpress(
            @RequestParam(name = "userName", required = false) String userName,
            @RequestParam(name = "expressName", required = false) String expressName,
            @RequestParam(name = "expressNumber", required = false) String expressNumber , User user) throws Exception {

        if(CMyString.isEmpty(expressNumber)) return HHJSONResult.errorMsg("传入快递单号为空");


        String expCode = InitService.KuaiDiCodeMap.get(expressName);


        User userByUserName = userRepository.findUserByUserName(userName);
        if(userByUserName == null)return HHJSONResult.errorMsg("目标用户信息为空");


        Express expressByExpressCodeAndExpressNumberAndUserId = expressRepository.findExpressByExpressCodeAndExpressNumberAndUserId(expCode, expressNumber, userByUserName.getId());
        if(expressByExpressCodeAndExpressNumberAndUserId != null) return HHJSONResult.errorMsg(StrUtil.format("快递【{}】已经存在，无需再次添加" ,expressNumber) );


        Express express = Express.builder()
                .expressCode(expCode)
                .expressNumber(expressNumber)
                .fromUser(userByUserName)
                .expressName(expressName)
                .userId(userByUserName.getId()).build();

        express.setId(sid.nextShort());
        express.setTeamid(userByUserName.getTeamid());
        express.setTeam(userByUserName.getTeam());

        expressRepository.save(express);
        return HHJSONResult.ok();
    }



    @GetMapping("/getExpressCodeAndUsers")
    @ApiOperation(value = "获取快递公司编码", notes = "获取快递公司编码")
    public HHJSONResult getExpressCodeAndUsers(User user){
        Map<String, String> kuaiDiCodeMap = InitService.KuaiDiCodeMap;
        List<User> usersByTeamid = userRepository.findUsersByTeamid(user.getTeamid());
        List<String> result = new ArrayList<>();

        usersByTeamid.forEach(e -> {
            result.add(e.getUserName());
        });

        List<String> expressList = new ArrayList<>();
        kuaiDiCodeMap.forEach( (k , v) ->{
            expressList.add(k);
        });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("exprNames" , expressList) ;
        jsonObject.put("userNames",result );
        return  HHJSONResult.ok(jsonObject);
    }


    @GetMapping("/queryListByOpenId")
    @ApiOperation(value = "查询当前用户的快递列表", notes = "查询当前用户的快递列表")
    public HHJSONResult queryListByOpenId( User user){

        List<Express> expressesByUserIdOrderByCrTimeDesc = expressRepository.findExpressesByUserIdOrderByCrTimeDesc(user.getId());

        List<String> numberList = new LinkedList<>();
        List<String> expressName = new LinkedList<>();
        expressesByUserIdOrderByCrTimeDesc.forEach(e ->{
            numberList.add(e.getExpressNumber());
            expressName.add(e.getExpressName());
        });
        JSONObject result = new JSONObject();
        result.put("numberList" , numberList);
        result.put("expressName" , expressName);

        return  HHJSONResult.ok(  result);
    }


    @GetMapping("/getUserExpressByNumber")
    @ApiOperation(value = "根据用户数据查询快递结果", notes = "根据用户数据查询快递结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "expressNumber", value = "快递单号", required = false, dataType = "String", paramType = "query", defaultValue = "2020-02-02"),
            @ApiImplicitParam(name = "expressName", value = "快递公司编号", required = false, dataType = "String", paramType = "query", defaultValue = "2020-02-02")
    })
    public HHJSONResult getUserExpressByNumber(
            @RequestParam(name = "expressName", required = false) String expressName,
            @RequestParam(name = "expressNumber", required = false) String expressNumber , User user) throws Exception {


        String expCode = InitService.KuaiDiCodeMap.get(expressName);

        Express express = expressRepository.findExpressByExpressCodeAndExpressNumberAndUserId(expCode, expressNumber, user.getId());
        if(express == null) return HHJSONResult.errorMsg("没有找到对应存储的快递信息");

        return HHJSONResult.ok(expressService.queryExpressByNameAndNumber(express.getExpressName() , express.getExpressNumber()));
    }




}
