package cn.houlinan.mylife.utils;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/22
 * Time : 14:03
 */
@Slf4j
@Component
public class WechatMessageSendUtil {

    @Value("${wechat.send.serverChan.baseHost}")
    private  String serverChanBaseHost ;

    @Value("${wechat.send.serverChan.cdkey}")
    private  String serverChanCDKey ;

    public static String stServerChanBaseHost = "";
    public static String stServerChanCDKey = "";

    @PostConstruct
    public void init() {
        stServerChanBaseHost = serverChanBaseHost;
        stServerChanCDKey = serverChanCDKey ;
    }


    public static JSONObject sendMessageByServerChan(String title , String desp){

        String sendUrl =stServerChanBaseHost + stServerChanCDKey + ".send?" ;

        String s = HttpUtil.get(sendUrl + "text=" + title + "&desp=" + desp);
        log.info("**********************  WechatMessageSendUtil **************************");
        log.info("准备给微信发送消息，发送的url为：" +sendUrl + "text=" + title + "&desp=" + desp );
        log.info("准备给微信发送消息，返回值为：" +UnicodeUtil.unicodeToString(s));
        log.info("********************** *********************** **************************");
        JSONObject jsonObject = JSONObject.fromObject(s);
        if(jsonObject.containsKey("errno") && jsonObject.getInt("errno") == 0 ) {
            jsonObject.put("isSuccess" , true );
            jsonObject.put("message" , "发送成功" );
            return jsonObject;
        }
        jsonObject.put("isSuccess" , false);

        String errmsg = "发送失败" ;
        if(jsonObject.containsKey("errmsg")){
            errmsg = UnicodeUtil.unicodeToString(jsonObject.getString("errmsg"));
        }
        jsonObject.put("message" , errmsg);

        return jsonObject;
    }
    public static void main(String[] args) {
//        sendMessageByServerChan("123" , "123");
    }
}
