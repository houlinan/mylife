package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.GetParametersUtileByInput;
import cn.houlinan.mylife.utils.HHJSONResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/11/15
 * Time : 10:36
 */
@RequestMapping("/wcm")
@Controller
public class WCMUtilsController {

    @RequestMapping("/createWCMTest")
    @ResponseBody
    public HHJSONResult createWCMTest(@RequestParam(name = "data" , required = false) String data  ,
                                      @RequestParam(name = "userName" , defaultValue = "playboy@163.com" )String userName) {



        JSONObject format = GetParametersUtileByInput.format(data);
//        String a = toPrettyFormat(format.toString()) ;
        String newForMat =  StringEscapeUtils.escapeJson(format.toString());

        String methodname = "";
        String[] strsaa = data.split("\n");
        for (int i = 0; i < strsaa.length; i++) {
            Object o = strsaa[i];
            String[] strsa = o.toString().split(":");
            if(!CMyString.isEmpty(strsa[0]) &&( "methodname".toUpperCase().equals(strsa[0]) || "methodname".equals(strsa[0]) )){
                methodname = strsa[1].trim();

                methodname = methodname.substring(0 , 1 ).toUpperCase()  + methodname.substring(1 , methodname.length());


                String testStr = "public void test"+methodname.trim()+"() throws Exception {\n" +

                        "\t\tUser loginUser = User.findByName(\"" + userName + "\");\n" +
                        "\t\tContextHelper.clear();\n" +
                        "\t\tContextHelper.initContext(loginUser);\n" +
                        "\t\tString str =\n\n\n\t\t\"" +newForMat + "\";\n\n\n\n"+
                        "\t\tJSONObject jsonObject = JSONObject.fromObject(str);\n" +
                        "\t\tMap<String, String> mapJson = JSONObject.fromObject(jsonObject);\n" +
                        "\t\tHashMap hParameters = new HashMap<>();\n" +
                        "\t\thParameters.putAll(mapJson);\n" +
                        "\t\tSystem.out.println(hParameters.toString());\n" +
                        "\t\thParameters.remove(\"serviceid\");\n" +
                        "\t\thParameters.remove(\"methodname\");\n" +
                        "\t\tJSPRequestProcessor processor = new JSPRequestProcessor();\n" +
                        "\t\tObject object = (Object) processor.excute(mapJson.get(\"SERVICEID\") + \"\", \"\" + mapJson.get(\"METHODNAME\"),\n" +
                        "\t\t\t\thParameters);\n" +
                        "\t\tSystem.err.println(WCMJSONHelper.toJSON(object));\n" +
                        "\t\tThread.sleep(3000);\n" +
                        "\t\t}";


                System.out.println(testStr);
                return HHJSONResult.ok(testStr);
            }
        }

        return HHJSONResult.errorMsg("错误了");
    }
    public static String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    @GetMapping("/testFormat")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "测试数据", required = true, dataType = "String", paramType = "path")
    })
    @ApiOperation(value = "wcm测试用例前端参数转换成json数据", notes = "wcm测试用例前端参数转换成json数据接口")
    public JSONObject WCMTestFormatJSONUtils(@RequestParam(name = "data", required = false) String data) {


        System.out.println(data);
        JSONObject format = GetParametersUtileByInput.format(data);

        return format;
    }


    //通过controller返回html界面
    @RequestMapping("/test")
    public String testJumpPage() {
        return "test";
    }

    //通过controller返回html界面
    @RequestMapping("/index")
    public String indexJumpPage() {
        return "index";
    }

    //通过controller返回html界面
    @RequestMapping("/formatParam")
    public String formatParamPage() {
        return "formatParam";
    }


    @RequestMapping("/getAllParam")
    @ResponseBody
    public String getAllParam(HttpServletRequest request) {
        Enumeration enu = request.getParameterNames();
        JSONObject result = new JSONObject();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            result.put(paraName, request.getParameter(paraName));
        }
        String resultStr = formatJSON(result.toString());
        System.out.println(resultStr);
        return resultStr;
    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    private String formatJSON(String s) {
        int level = 0;
        //存放格式化的json字符串
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int index = 0; index < s.length(); index++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = s.charAt(index);
//          System.out.println(s.charAt(index));

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
//                System.out.println("123"+jsonForMatStr);
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }


}