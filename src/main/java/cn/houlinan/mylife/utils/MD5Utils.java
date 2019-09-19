package cn.houlinan.mylife.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.net.URLDecoder;
import java.security.MessageDigest;


public class MD5Utils {

	/**
	 * @Description: 对字符串进行md5加密
	 */
	public static String getMD5Str(String strValue)  {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
			return newstr;
		}catch (Exception e ){
			return null ;
		}

	}


    public static final String JSON = "{\"title\":\"专题标题\",\"name\":\"分组名称\",\"ListDesc\":[{\"docId\":\"10000001\",\"docTitle\":\"测试标题1\",\"docChannel\":\"文化\",\"docIssueTime\":\"2019-08-12 11:22:12\"},{\"docId\":\"10000002\",\"docTitle\":\"测试标题2\",\"docChannel\":\"文化\",\"docIssueTime\":\"2019-08-12 11:22:12\"},{\"docId\":\"10000003\",\"docTitle\":\"测试标题3\",\"docChannel\":\"文化\",\"docIssueTime\":\"2019-08-12 11:22:12\"},{\"docId\":\"10000004\",\"docTitle\":\"测试标题4\",\"docChannel\":\"文化\",\"docIssueTime\":\"2019-08-12 11:22:12\"},{\"docId\":\"10000005\",\"docTitle\":\"测试标题5\",\"docChannel\":\"文化\",\"docIssueTime\":\"2019-08-12 11:22:12\"},{\"docId\":\"10000006\",\"docTitle\":\"测试标题6\",\"docChannel\":\"文化\",\"docIssueTime\":\"2019-08-12 11:22:12\"}]}";




    public static void main(String[] args) {

        System.out.println(URLDecoder.decode(JSON));
//		try {
//			String md5 = getMD5Str("imooc");
//			System.out.println(md5);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
