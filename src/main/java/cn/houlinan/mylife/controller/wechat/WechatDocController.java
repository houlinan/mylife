package cn.houlinan.mylife.controller.wechat;

import cn.houlinan.mylife.DTO.WechatDocument;
import cn.houlinan.mylife.service.wechat.WechatService;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.OkhttpUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/13
 * Time : 11:23
 */
@RestController
@RequestMapping("/wecharDoc")
@Slf4j
@Api(value = "微信稿件相关controller", tags = "微信稿件相关controller")
public class WechatDocController {

    @Autowired
    WechatService wechatService ;

    @GetMapping("/getContentList")
    @ApiOperation(value = "获取微信文章列表", notes = "获取微信文章列表")
    public HHJSONResult getContentList(@RequestParam(name= "pageIndex" ,defaultValue ="0")int pageIndex,
                                @RequestParam(name= "pageSize" ,defaultValue ="10")int pageSize
    ) throws IOException {

        String token = wechatService.getToken();
        String path = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + token;

        Map<String , Object > params = new HashMap<>();
        params.put("type", "news"); // news表示图文类型的素材，具体看API文档
        params.put("offset", pageIndex * pageSize);
        params.put("count", pageSize);
        String s = OkhttpUtil.doPostJSON(path , JSONObject.fromObject(params).toString());
        JSONObject result = JSONObject.fromObject(s);

        try{
            List<WechatDocument> documentList = new LinkedList<>();
            JSONArray jsonArray = result.getJSONArray("item");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject currItem =  jsonArray.getJSONObject(i);
                JSONArray jsonArray1 = currItem.getJSONObject("content").getJSONArray("news_item");
                for (int a = 0; a < jsonArray1.size(); a++) {
                    String  o =  jsonArray1.getString(a);
                    WechatDocument wechatDocument = JSONUtil.toBean(o, WechatDocument.class);
//                    wechatDocument.setContent();
                    documentList.add(wechatDocument);
                }
            }
            return HHJSONResult.ok(documentList);
        }catch (Exception e){

        }
        return HHJSONResult.errorMsg("获取稿件列表失败");
    }



}
