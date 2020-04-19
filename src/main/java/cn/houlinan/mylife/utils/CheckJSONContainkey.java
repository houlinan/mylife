package cn.houlinan.mylife.utils;

import net.sf.json.JSONObject;

import java.util.Iterator;

/**
 * DESC：验证两个json中不包含的key
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/4/10
 * Time : 15:08
 */
public class CheckJSONContainkey {

    public static void main(String[] args) {

        //key比较的多的json
        String jsonAll = "{\n" +
                "\"SUBTITLEWORDS\" : \"\",\n" +
                "\"TOTALDURATION\" : \"00:00:00\",\n" +
                "\"FGD_AUTHINFO\" : [\n" +
                "   {\n" +
                "   \"ID\" : \"8950\",\n" +
                "   \"UNAME\" : \"liu@qq.com\",\n" +
                "   \"USERNAME\" : \"柳林海\",\n" +
                "   \"COMPANY\" : \"微博测试\",\n" +
                "   \"DEPT\" : \"微博测试\",\n" +
                "   \"EMAIL\" : \"liu@qq.com\",\n" +
                "   \"POST\" : \"\",\n" +
                "   \"PRESS\" : \"\",\n" +
                "   \"TELEPHONE\" : \"\",\n" +
                "   \"MOBILEPHONE\" : \"15158920090\",\n" +
                "   \"ADDRESS\" : \"\",\n" +
                "   \"FGDSFZ\" : \"\",\n" +
                "   \"BANKACCOUNT\" : \"\",\n" +
                "   \"BANKCARDNUMBER\" : \"\",\n" +
                "   \"TYPE\" : \"23\"\n" +
                "   }\n" +
                "   ],\n" +
                "\"CHANNELID\" : \"9793\",\n" +
                "\"TENANTID\" : \"331\",\n" +
                "\"TRUENAME\" : \"houlinan\",\n" +
                "\"ORIGINAL\" : \"1\",\n" +
                "\"FLOWVERSIONTIME\" : \"2020-04-09 18:07:58\",\n" +
                "\"VIDEOLOGO\" : \"0\",\n" +
                "\"CRUSERDEPT\" : \"微博测试\",\n" +
                "\"NEWSSOURCES\" : \"\",\n" +
                "\"HTMLCONTENT\" : \"<p>测试标识</p>\",\n" +
                "\"SUMMARY\" : \"\",\n" +
                "\"ORIGINALTYPE\" : \"2\",\n" +
                "\"CORRESPONDENT\" : \"\",\n" +
                "\"BELONGCHANNEL\" : {},\n" +
                "\"WEBSITEDESC\" : \"属性测试\",\n" +
                "\"CONTENTDURATION\" : \"00:00:00\",\n" +
                "\"DOCTYPE\" : \"文稿\",\n" +
                "\"SPEECHRATE\" : \"300字/分钟\",\n" +
                "\"ISFZG\" : \"false\",\n" +
                "\"DOCTYPEID\" : \"9\",\n" +
                "\"BROADCASTINGNAME\" : \"\",\n" +
                "\"QUOTECHNLID\" : \"9793\",\n" +
                "\"SRCMETADATAID\" : \"0\",\n" +
                "\"METADATAID\" : \"891419\",\n" +
                "\"BELONGCHANNEL_DB\" : {},\n" +
                "\"ISCOMMENTED\" : \"0\",\n" +
                "\"CRUSER\" : \"weibo@qq.com\",\n" +
                "\"AUDIOLOGO\" : \"0\",\n" +
                "\"EDITINGDURATION\" : \"00:00:00\",\n" +
                "\"FGD_EDITINFO\" : [\n" +
                "\n" +
                "   ],\n" +
                "\"QUOTE\" : \"1\",\n" +
                "\"CRTIME\" : \"2020-01-19 18:25:15\",\n" +
                "\"OPERTIME\" : \"\",\n" +
                "\"DOCPUBTIME\" : \"\",\n" +
                "\"CONTENTSTRUCTURE\" : \"\",\n" +
                "\"MEDIATYPEID\" : \"15\",\n" +
                "\"WEBSITEID\" : \"925\",\n" +
                "\"CRDEPT\" : \"微博测试\",\n" +
                "\"BROADCASTING\" : [\n" +
                "\n" +
                "   ],\n" +
                "\"CHNLID\" : \"9793\",\n" +
                "\"DOCWORDSCOUNT\" : \"4\",\n" +
                "\"PICSLOGO\" : \"0\",\n" +
                "\"PUBLISHCHANNEL\" : \"tv\",\n" +
                "\"CONTENT\" : \"测试标识\",\n" +
                "\"BROADCASTINGTIME\" : \"\",\n" +
                "\"ISCONTAINSENSITIVEWORDS\" : \"0\",\n" +
                "\"EXATTRIBUTE\" : [{\"id\":\"18\",\"name\":\"aabb\",\"desc\":\"bbaa\",\"formType\":\"select\",\"length\":\"20\",\"enumValue\":\"111,222,333\",\"displayOrder\":\"3\",\"value\":\"\"},{\"id\":\"17\",\"name\":\"tryabc\",\"desc\":\"试试94423\",\"formType\":\"select\",\"length\":\"20\",\"enumValue\":\"111\",\"displayOrder\":\"2\",\"value\":\"\"},{\"id\":\"16\",\"name\":\"try\",\"desc\":\"试试944\",\"formType\":\"input\",\"length\":\"50\",\"enumValue\":\"\",\"displayOrder\":\"1\",\"value\":\"\"}],\n" +
                "\"ORIGINMETADATAID\" : \"891410\",\n" +
                "\"SITEID\" : \"925\",\n" +
                "\"CHNLDESC\" : \"我欲成仙，快乐齐天\",\n" +
                "\"BLANKFLAG\" : \"0\",\n" +
                "\"THEMEFLOWER\" : \"\",\n" +
                "\"INTRODUCERDURATION\" : \"00:00:00\",\n" +
                "\"CHNLNAME\" : \"12345\",\n" +
                "\"METALOGOURL\" : {},\n" +
                "\"RELATEDLIVEDOCS\" : [\n" +
                "\n" +
                "   ],\n" +
                "\"QUOTECHNLDESC\" : \"我欲成仙，快乐齐天\",\n" +
                "\"TITLE\" : \"测试标识\",\n" +
                "\"PREDATE\" : \"\"\n" +
                "}";


        //key比较少的json
        String json = "{\n" +
                "\"FGD_EDITINFO\" : [\n" +
                "\n" +
                "   ],\n" +
                "\"QUOTE\" : \"0\",\n" +
                "\"CRTIME\" : \"2020-04-10 14:25:46\",\n" +
                "\"OPERTIME\" : \"\",\n" +
                "\"DOCPUBTIME\" : \"\",\n" +
                "\"FGD_AUTHINFO\" : [\n" +
                "   {\n" +
                "   \"ID\" : \"8820\",\n" +
                "   \"UNAME\" : \"weibo@qq.com\",\n" +
                "   \"USERNAME\" : \"houlinan\",\n" +
                "   \"COMPANY\" : \"微博测试\",\n" +
                "   \"DEPT\" : \"微博测试\",\n" +
                "   \"EMAIL\" : \"weibo@qq.com\",\n" +
                "   \"POST\" : \"\",\n" +
                "   \"PRESS\" : \"\",\n" +
                "   \"TELEPHONE\" : \"\",\n" +
                "   \"MOBILEPHONE\" : \"17655656565\",\n" +
                "   \"ADDRESS\" : \"地址\",\n" +
                "   \"FGDSFZ\" : \"\",\n" +
                "   \"BANKACCOUNT\" : \"\",\n" +
                "   \"BANKCARDNUMBER\" : \"\",\n" +
                "   \"TYPE\" : \"23\"\n" +
                "   }\n" +
                "   ],\n" +
                "\"TENANTID\" : \"331\",\n" +
                "\"CHANNELID\" : \"9793\",\n" +
                "\"TRUENAME\" : \"houlinan\",\n" +
                "\"ORIGINAL\" : \"1\",\n" +
                "\"MEDIATYPEID\" : \"15\",\n" +
                "\"WEBSITEID\" : \"925\",\n" +
                "\"CRDEPT\" : \"微博测试\",\n" +
                "\"BROADCASTING\" : [\n" +
                "\n" +
                "   ],\n" +
                "\"FLOWVERSIONTIME\" : \"2020-04-10 14:25:29\",\n" +
                "\"VIDEOLOGO\" : \"0\",\n" +
                "\"CRUSERDEPT\" : \"微博测试\",\n" +
                "\"CHNLID\" : \"9793\",\n" +
                "\"NEWSSOURCES\" : \"\",\n" +
                "\"HTMLCONTENT\" : \"<p>2342342</p>\",\n" +
                "\"DOCWORDSCOUNT\" : \"7\",\n" +
                "\"SUMMARY\" : \"\",\n" +
                "\"ORIGINALTYPE\" : \"2\",\n" +
                "\"BELONGCHANNEL\" : {},\n" +
                "\"WEBSITEDESC\" : \"属性测试\",\n" +
                "\"PICSLOGO\" : \"0\",\n" +
                "\"PUBLISHCHANNEL\" : \"tv\",\n" +
                "\"CONTENT\" : \"在另外数据表中\",\n" +
                "\"DOCTYPE\" : \"文稿\",\n" +
                "\"ISFZG\" : \"false\",\n" +
                "\"DOCTYPEID\" : \"9\",\n" +
                "\"ISCONTAINSENSITIVEWORDS\" : \"0\",\n" +
                "\"EXATTRIBUTE\" : [{\"id\":\"18\",\"name\":\"aabb\",\"desc\":\"bbaa\",\"formType\":\"select\",\"length\":\"20\",\"enumValue\":\"111,222,333\",\"displayOrder\":\"3\",\"value\":\"\"},{\"id\":\"17\",\"name\":\"tryabc\",\"desc\":\"试试94423\",\"formType\":\"select\",\"length\":\"20\",\"enumValue\":\"111\",\"displayOrder\":\"2\",\"value\":\"\"},{\"id\":\"16\",\"name\":\"try\",\"desc\":\"试试944\",\"formType\":\"input\",\"length\":\"50\",\"enumValue\":\"\",\"displayOrder\":\"1\",\"value\":\"\"}],\n" +
                "\"ORIGINMETADATAID\" : \"888710\",\n" +
                "\"SRCMETADATAID\" : \"0\",\n" +
                "\"CHNLDESC\" : \"我欲成仙，快乐齐天\",\n" +
                "\"BLANKFLAG\" : \"0\",\n" +
                "\"METADATAID\" : \"892242\",\n" +
                "\"BELONGCHANNEL_DB\" : {},\n" +
                "\"CHNLNAME\" : \"12345\",\n" +
                "\"RELATEDLIVEDOCS\" : [\n" +
                "\n" +
                "   ],\n" +
                "\"METALOGOURL\" : {},\n" +
                "\"ISCOMMENTED\" : \"0\",\n" +
                "\"QUOTECHNLDESC\" : \"我欲成仙，快乐齐天\",\n" +
                "\"CRUSER\" : \"weibo@qq.com\",\n" +
                "\"TITLE\" : \"434\",\n" +
                "\"AUDIOLOGO\" : \"0\",\n" +
                "\"PREDATE\" : \"\"\n" +
                "}";

        JSONObject allJSON = JSONObject.fromObject(jsonAll);
        JSONObject jsonObject = JSONObject.fromObject(json);

        Iterator keys = allJSON.keys();

        while (keys.hasNext()) {
            Object key = keys.next();
            String keyStr = (key).toString().toUpperCase() ;
            if(!jsonObject.containsKey(keyStr)) System.out.println(keyStr);
        }


    }






}
