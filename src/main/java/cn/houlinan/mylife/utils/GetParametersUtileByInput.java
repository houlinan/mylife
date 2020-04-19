package cn.houlinan.mylife.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * DESC：根据前端请求获取参数转成json
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/5/23
 * Time : 12:49
 */
public class GetParametersUtileByInput {


    /****
     public void testTijiao() throws Exception {

     User loginUser = User.findByName("playboy@163.com");
     loginUser.setExtProperty("GroupId", 2);
     ContextHelper.clear();
     ContextHelper.initContext(loginUser);
     String str =
     ？

     JSONObject jsonObject = JSONObject.fromObject(str);
     Map<String, String> mapJson = JSONObject.fromObject(jsonObject);
     HashMap hParameters = new HashMap<>();
     hParameters.putAll(mapJson);
     System.out.println(hParameters.toString());
     hParameters.remove("serviceid");
     hParameters.remove("methodname");
     JSPRequestProcessor processor = new JSPRequestProcessor();
     Object oReport = (Object) processor.excute(mapJson.get("SERVICEID") + "", "" + mapJson.get("METHODNAME"),
     hParameters);
     System.err.println(WCMJSONHelper.toJSON(oReport));
     }

     */

    public static JSONObject format(String data) {

        JSONObject result = new JSONObject();

        String[] strsaa = data.split("\n");
        for (int i = 0; i < strsaa.length; i++) {
            Object o =  strsaa[i];
            System.out.println(o.toString());

            String[] strsa = o.toString().split(":");

        if (!StringUtils.isEmpty(strsa[0])) {
            StringBuffer sb = new StringBuffer();
            if (strsa.length == 2) {
                sb.append(strsa[1].trim());
            } else {
                for (int a = 1; a < strsa.length; a++) {
                    sb.append(strsa[a].trim()).append(":");
                }
                sb.setLength(sb.length() - 1);
            }
            String cuttValue = strsa[1];
            if (StringUtils.isEmpty(sb.toString())) {
                result.put(strsa[0].trim().toUpperCase(), "");
            } else {
                result.put(strsa[0].trim().toUpperCase(), sb.toString());
            }
        }
        }
        return result;
    }


}
