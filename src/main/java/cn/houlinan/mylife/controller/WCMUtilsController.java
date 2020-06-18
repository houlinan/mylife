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
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/11/15
 * Time : 10:36
 */
@RequestMapping("/wcm")
@Controller
@Slf4j
public class WCMUtilsController {

    @Value("${wcm.forward.address}")
    private String wcmAddress;

    @RequestMapping("/redirectToWcm")
    public void redirectToWcm(HttpServletRequest request, HttpServletResponse res) throws Exception {

        StringBuffer result = new StringBuffer(wcmAddress + "NewMediaPlatform/common/register?");

        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            result.append(paraName).append("=").append(request.getParameter(paraName)).append("&");
        }
        if (result.toString().endsWith("&"))
            result.setLength(result.length() - 1);

//        return "forward:/" + result.toString();
        res.setStatus(302);
        res.sendRedirect(result.toString());
    }

    @RequestMapping("/createWCMTest")
    @ResponseBody
    public HHJSONResult createWCMTest(@RequestParam(name = "data", required = false) String data,
                                      @RequestParam(name = "userName", defaultValue = "playboy@163.com") String userName) {


        JSONObject format = GetParametersUtileByInput.format(data);
//        String a = toPrettyFormat(format.toString()) ;
        String newForMat = StringEscapeUtils.escapeJson(format.toString());

        String methodname = "";
        String[] strsaa = data.split("\n");
        for (int i = 0; i < strsaa.length; i++) {
            Object o = strsaa[i];
            String[] strsa = o.toString().split(":");
            if (!CMyString.isEmpty(strsa[0]) && ("methodname".toUpperCase().equals(strsa[0]) || "methodname".equals(strsa[0]))) {
                methodname = strsa[1].trim();

                methodname = methodname.substring(0, 1).toUpperCase() + methodname.substring(1, methodname.length());


                String testStr = "public void test" + methodname.trim() + "() throws Exception {\n" +

                        "\t\tUser loginUser = User.findByName(\"" + userName + "\");\n" +
                        "\t\tContextHelper.clear();\n" +
                        "\t\tContextHelper.initContext(loginUser);\n" +
                        "\t\tString str =\n\n\n\t\t\"" + newForMat + "\";\n\n\n\n" +
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


                log.info(testStr);
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


        log.info(data);
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

    //通过controller返回html界面
    @RequestMapping("/sqlAddComment")
    public String sqlAddComment() {
        return "sqlAddComment";
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
        log.info(resultStr);
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

    @RequestMapping("/sqlAddCommment1")
    @ResponseBody
    public HHJSONResult sqlAddCommment1(@RequestParam(name = "data", required = false) String data,
                                        @RequestParam(name = "tableName", defaultValue = "playboy@163.com") String tableName) {

        String[] split = data.split("\n");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (s.endsWith(",")) {
                s = s.substring(0, s.length() - 1);
            }
            String field = s.substring(s.indexOf("`") + 1, s.lastIndexOf("`"));
            String comment = "";
            String fieldValue = getFieldValue(field);
            if (!CMyString.isEmpty(fieldValue)) comment = fieldValue;
            result.append("ALTER TABLE " + tableName + " MODIFY COLUMN " + s);
            if (!s.contains("COMMENT")) {
                result.append(" COMMENT '" + comment + "'");
            }
            result.append(" ;\n");
        }
        log.info(data);
        return HHJSONResult.ok(result);
    }


    private String getFieldValue(String name) {

        Map<String, String> map = new HashMap();
        map.put("crtime".toUpperCase(), "创建时间");
        map.put("cruser".toUpperCase(), "创建人");
        map.put("tenantId".toUpperCase(), "租户id");
        map.put("CHANNELID".toUpperCase(), "栏目id");
        map.put("metadataid".toUpperCase(), "元数据id");
        map.put("userId".toUpperCase(), "用户表主键id");
        map.put("operType".toUpperCase(), "操作类型");
        map.put("desc".toUpperCase(), "描述");
        map.put("title".toUpperCase(), "标题");
        map.put("content".toUpperCase(), "正文文本");
        map.put("htmlcontent".toUpperCase(), "html文本");
        map.put("docid".toUpperCase(), "稿件id");
        map.put("DOCTITLE".toUpperCase(), "稿件标题");
        map.put("FROMTYPE".toUpperCase(), "稿件来源");
        map.put("ATTRIBUTE".toUpperCase(), "拓展属性");
        map.put("OBJID".toUpperCase(), "对象id");
        map.put("OBJTYPE".toUpperCase(), "对象类型");
        map.put("FILENAME".toUpperCase(), "文件名称");
        map.put("SITEID".toUpperCase(), "站点id");
        map.put("MediaType".toUpperCase(), "媒体渠道类型");
        map.put("CHNLDESC".toUpperCase(), "栏目描述");
        map.put("STATUS".toUpperCase(), "状态");
        map.put("DOCTYPE".toUpperCase(), "稿件类型");
        return map.get(name.toUpperCase());
    }

}
