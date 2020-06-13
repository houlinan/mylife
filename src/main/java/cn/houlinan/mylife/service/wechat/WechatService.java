package cn.houlinan.mylife.service.wechat;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/13
 * Time : 11:26
 */
@Service
@Slf4j
public class WechatService {

    @Value("${wechat.appId}")
    private String appId ;

    @Value("${wechat.secret}")
    private String secret ;

    public  String getToken() throws MalformedURLException, IOException, ProtocolException {
        String path = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

        String s = HttpUtil.get(path + "&appid=" + appId + "&secret=" + secret);
        JSONObject result = JSONObject.fromObject(s);

        return result.getString("access_token");
    }
}
