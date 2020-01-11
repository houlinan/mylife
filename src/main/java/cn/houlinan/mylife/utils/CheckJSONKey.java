package cn.houlinan.mylife.utils;

import net.sf.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/12/23
 * Time : 10:25
 */
public class CheckJSONKey {




    public static void main(String[] args) {

        String feibianJSON = "{\"appname\":\"wgmz\",\"data\":[{\"docChnlName\":\"剑河新闻\",\"chnlId\":4056,\"crDept\":\"剑河县⼴播电视台\\\\\",\"crTime\":\"2019-12-14 10:32:30\",\"docPubUrl\":\"\",\"preDate\":null,\"subtitleWords\":\"\",\"crUser\":\"289254287@qq.com\",\"docPubTime\":\"2019-12-16 09:29:00\",\"fgdError\":null,\"pubUser\":\"755344589@qq.com\",\"attachVideo\":null,\"docQuoteRelation\":\"\",\"speechRate\":\"264字/分 钟\",\"docRelTime\":\"2019-12-14 10:32:30\",\"appendixInfo\":\"[]\",\"pubUserDept\":\"剑河县⼴播电视台&gt;\",\"dcUpdateTime\":\"2019-12-16 09:29:01\",\"broadcastingName\":\"\",\"contentStructure\":\"\",\"quote\":null,\"contentDuration\":\"00:00:00\",\"dcUpdateUser\":\"\",\"id\":2717,\"guidedBroadcast\":\"\",\"operUser\":\"755344589@qq.com\",\"modal\":1,\"attachAudio\":null,\"docChannel\":4056,\"hoster\":\"\",\"operTime\":\"2019-1 2-16 09:29:00\",\"docWordsCount\":794,\"totalDuration\":\"00:00:00\",\"author\":\"\",\"broadcastStatus\":null,\"broadcasting\":\"\",\"docSource\":null,\"dubbingStatus\":null,\"fgdInfo\":\"[{\\\"sex\\\": \\\"\\\", \\\"tel\\\": \\\"\\\", \\\"email\\\": \\\"289254287@qq.com\\\", \\\"fgdid\\\": 516599, \\\"mobile\\\": \\\"15186819220\\\", \\\"status\\\": 30, \\\"userid\\\": 1987, \\\"address\\\": \\\"\\\", \\\"fgdtype\\\": 23, \\\"docgenre\\\": \\\"{\\\\\\\"name\\\\\\\":\\\\\\\"⽆体裁\\\\\\\",\\\\\\\"value\\\\ \\\":\\\\\\\"⽆体裁\\\\\\\"}\\\", \\\"nickname\\\": \\\"\\\", \\\"truename\\\": \\\"刘开彬\\\", \\\"username\\\": \\\"289254287@qq.com\\\", \\\"attribute\\\": \\\"COMPANY=剑河 县⼴播电视台&amp;DEPT=剑河县⼴播电视台&amp;GROUPID=\\\", \\\"crgroupid\\\": 0, \\\"fgdcrtime\\\": \\\"2019-12-14 10:32:30\\\", \\\"fgdcruser\\\": \\\"289254287@qq.com\\\", \\\"metadataid\\\": 852915, \\\"userpinyin\\\": \\\"LKB\\\", \\\"isnopayment\\\": 1, \\\"scmmicrocontentid\\\": 0}]\",\"quoteChnlId\":4056,\"scoreStatus\":null,\"specialDoc\":null,\"recId\":901501,\"crUserId\":1987,\"editingDuration\":\"00:00:00\",\"mlfRootId\":0,\"dutyOfﬁcer\":\"\",\"themeFlower\":\"\",\"ztgInfo\":\"[]\",\"originalType\":2,\"docId\":852915,\"blankFlag\":0,\"exAttribute\":\"[{\\\"id\\\": \\\"11\\\", \\\"desc\\\": \\\"素材位置\\\", \\\"name\\\": \\\"scwz\\\", \\\"value\\\": \\\"Y-新闻素材-领导活动-王勇志-12.13王勇志到南明调研\\\", \\\"length\\\": \\\"50\\\", \\\"formType\\\": \\\"input\\\", \\\"enumValue\\\": \\\"\\\", \\\"displayOrder\\\": \\\"9\\\"}]\",\"siteName\":\"剑河⼴播电视 台\",\"introducerDuration\":\"00:00:00\",\"title\":\"王勇志到南明镇宣讲党的⼗九届四中全会精神\",\"content\":\"\",\"ﬂowVersionTime\":\"2019-12-16 09:28:57\",\"abStract\":\"\",\"fromId\":null,\"originDocId\":852910,\"originalChildType\":null,\"attachPic\":null,\"dcCrUser\":\"\",\"tenantId\":97,\"siteId\":417,\"correspondent\":\"\",\"dcCrTime\":\"2019-12-16 09:28:24\",\"videoShooting\":\"\",\"docFirstPubTime\":\"2019-12-16 09:28:23\",\"mrsFlag\":null}],\"msec\":\"1576459741184\",\"encrypting\":\"be3f3442882d77001b4c48e3d24c1ee3\"}";
        String tmyJSON = " {\"appname\":\"wz\",\"data\":[{\"CHANNELID\":\"529\",\"TRUENAME\":\"花花公子\",\"VIDEOLOGO\":\"0\",\"CRUSERDEPT\":\"花花公子\",\"NEWSSOURCES\":\"\",\"BELONGCHANNEL\":{},\"PICSLOGO\":\"0\",\"ISFZG\":\"false\",\"DOCTYPEID\":\"9\",\"WCMMETATABLEBROADCASTID\":\"318\",\"ISCONTAINSENSITIVEWORDS\":\"0\",\"ORIGINMETADATAID\":\"10708\",\"SRCMETADATAID\":\"0\",\"CHNLDESC\":\"小钱广播的栏目\",\"METADATAID\":\"10711\",\"BELONGCHANNEL_DB\":{},\"RELATEDLIVEDOCS\":[],\"METALOGOURL\":{},\"ISCOMMENTED\":\"0\",\"AUDIOLOGO\":\"0\",\"TIMEDSTATUS\":\"0\",\"ISZHENGSHEN\":\"0\",\"POSCHNLID\":\"0\",\"SRCSITEID\":\"348\",\"REFUSESTATUS\":\"0\",\"ORIGINRECID\":\"0\",\"ISTIMINGPUBLISH\":\"0\",\"DOCOLDSTATUS\":\"0\",\"CLIENTEXAMINE\":\"0\",\"DOCOUTUPID\":\"0\",\"DOCKIND\":\"17\",\"DOCORDER\":\"9\",\"AVLABE\":\"0\",\"ACTIONTYPE\":\"0\",\"HIDDEN\":\"0\",\"PUBSTATUS\":\"0\",\"CANCELPUBTIME\":\"\",\"INVALIDTIME\":\"\",\"DOCORDERPRI\":\"0\",\"docChnlName\":\"小钱广播的栏目1558493443785\",\"appendixInfo\":\"花花公子\",\"docPubUrl\":\"\",\"pubUser\":\"\",\"pubUserDept\":\"\",\"dcUpdateUser\":\"\",\"mlfRootId\":\"0\",\"dutyOfficer\":\"0\",\"ztgInfo\":[],\"keyWords\":\"\",\"dcCrTime\":\"2019-12-11 14:51:27\",\"crDept\":\"花花公子\\\\\",\"crTime\":\"2019-12-11 14:51:27\",\"chnlId\":\"529\",\"preDate\":\"\",\"subtitleWords\":\"字幕词\",\"docPubTime\":\"\",\"crUser\":\"playboy@163.com\",\"fgdError\":\"\",\"docQuoteRelation\":[],\"attachVideo\":\"\",\"speechRate\":\"100字/分钟\",\"docRelTime\":\"2019-12-11 14:51:27\",\"htmlContent\":\"<p>312312312313<\\/p><p class=\\\"main_container\\\" style=\\\"color:#206EFB\\\"><span class=\\\"main_title\\\" contenteditable=\\\"false\\\">【正文】<\\/span><\\/p><p><span style=\\\"color:#206EFB\\\">321312321<\\/span><\\/p><p class=\\\"lead_container\\\" style=\\\"color:#58AB5C\\\"><span class=\\\"lead_title\\\" contenteditable=\\\"false\\\">【导语】<\\/span><\\/p><p style=\\\"color:#58AB5C\\\">r<\\/p><p><span style=\\\"color:#58AB5C\\\">12312312432423<\\/span><\\/p><p class=\\\"afterCompile_container\\\" style=\\\"color:#E84F89\\\"><span class=\\\"afterCompile_title\\\" contenteditable=\\\"false\\\">【编后】<\\/span><\\/p><p><span style=\\\"color:#E84F89\\\">234r&#39;we&#39;r&#39;wrwer<\\/span><\\/p><p class=\\\"afterCompile_container\\\" style=\\\"color: rgb(232, 79, 137);\\\"><span class=\\\"afterCompile_title\\\" contenteditable=\\\"false\\\">【编后】<\\/span><\\/p><p style=\\\"color: rgb(232, 79, 137);\\\">rewerewrwerwe<\\/p><p style=\\\"color:#000\\\">【现场口导】<\\/p><p style=\\\"color: rgb(232, 79, 137);\\\"><span style=\\\"color: rgb(0, 0, 0);\\\">rwerwerwerwerwer<\\/span><\\/p><p style=\\\"color:#000\\\">【同期声】<\\/p><p style=\\\"color: rgb(232, 79, 137);\\\"><span style=\\\"color: rgb(0, 0, 0);\\\">rwerwerwe<\\/span><\\/p><p style=\\\"color:#000\\\">【现场音】<\\/p><p style=\\\"color: rgb(232, 79, 137);\\\"><span style=\\\"color: rgb(0, 0, 0);\\\">werwrwerwerwerwer<\\/span><span style=\\\"color: rgb(0, 0, 0);\\\"><\\/span><br/><\\/p>\",\"quote\":\"1\",\"broadcastingName\":\"test163,侯立男1223232323232,king测试\",\"contentStructure\":\"正,导,编,编\",\"contentDuration\":\"00:00:05\",\"guidedBroadcast\":\"\",\"operUser\":\"playboy@163.com\",\"modal\":\"1\",\"docChannel\":\"529\",\"operTime\":\"2019-12-11 15:12:25\",\"attachAudio\":\"\",\"hoster\":\"\",\"docWordsCount\":\"150\",\"totalDuration\":\"00:01:13\",\"author\":\"\",\"broadcastStatus\":\"\",\"broadcasting\":\"28,26,14\",\"docSource\":\"\",\"dubbingStatus\":\"\",\"fgdInfo\":[],\"quoteChnlId\":\"\",\"scoreStatus\":\"\",\"specialDoc\":\"\",\"recId\":\"11056\",\"editingDuration\":\"00:00:58\",\"themeFlower\":\"题花\",\"originalType\":\"2\",\"docId\":\"10711\",\"exAttribute\":[],\"introducerDuration\":\"00:00:09\",\"title\":\"测试推送非编\",\"content\":\"312312312313\\n【正文】\\n321312321\\n【导语】\\nr\\n12312312432423\\n【编后】\\n234r&#39;we&#39;r&#39;wrwer\\n【编后】\\nrewerewrwerwe\\n【现场口导】\\nrwerwerwerwerwer\\n【同期声】\\nrwerwerwe\\n【现场音】\\nwerwrwerwerwerwer\",\"docStatus\":\"1\",\"fromType\":\"\",\"jsonObject\":\"\",\"isPushTopChnl\":\"0\",\"original\":\"1\",\"chnlName\":\"小钱广播的栏目1558493443785\",\"docType\":\"9\",\"flowVersionTime\":\"2019-12-11 14:51:27\",\"abStract\":\"\",\"fromId\":\"\",\"mrsFla\":\"\",\"originDocId\":\"10708\",\"originalChildType\":\"\",\"attachPic\":\"0\",\"dcCrUser\":\"\",\"tenantId\":\"1\",\"siteId\":\"348\",\"correspondent\":\"通讯员\",\"videoShooting\":\"\",\"docFirstPubTime\":\"\"}],\"msec\":\"1555487997766\",\"encrypting\":\"9610182b17e5b1549f7b09cc40832ea5\"}\n";

        JSONObject feibianJSONs = JSONObject.fromObject(feibianJSON);

        JSONObject tmyJSONs = JSONObject.fromObject(tmyJSON);
        JSONObject feibainData = feibianJSONs.getJSONArray("data").getJSONObject(0);
        JSONObject tmyData = tmyJSONs.getJSONArray("data").getJSONObject(0);


        Iterator<String> it = feibainData.keys();
        while(it.hasNext()){
        // 获得key
            String key = it.next();
            if(!tmyData.containsKey(key)){
                System.out.println(key);
            }
        }


    }
}
