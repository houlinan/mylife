package cn.houlinan.mylife.utils;

import com.google.common.net.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

public class HttpUtil {
	
	/** 
	* @Title: getIpAddress 
	* @Description: 获取客户端真实IP 
	* @param request
	* @return
	* @throws 
	* @Author：liujian 2018年7月12日 下午4:01:43
	*/
	public static String getIpAddress(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    if (ip.contains(",")) {
	        return ip.split(",")[0];
	    } else {
	        return ip;
	    }
	}
	/** 
	* @Title: checkApiOfPost 
	* @Description: 校验接口是否可用，post请求 
	* @param apiUrl 
	* @return
	* @throws 
	* @Author：liujian 2018年7月20日 上午10:16:53
	*/
	public static boolean checkApiOfPost(String apiUrl) {
		try{
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl,requestEntity,String.class);
            HttpStatus status = responseEntity.getStatusCode();//获取返回状态
            return status.is2xxSuccessful();//判断状态码是否为2开头的
        }catch(Exception e){
            return false; //502 ,500是不能正常返回结果的，需要catch住，返回一个false
        }
	}
	/** 
	* @Title: checkApiOfGet 
	* @Description: 校验接口是否可用，get请求 
	* @param apiUrl 
	* @return
	* @throws 
	* @Author：liujian 2018年7月20日 上午10:16:53
	*/
	public static boolean checkApiOfGet(String apiUrl) {
		try{
			RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl,String.class);
            HttpStatus status = responseEntity.getStatusCode();//获取返回状态
            return status.is2xxSuccessful();//判断状态码是否为2开头的
        }catch(Exception e){
            return false; //502 ,500是不能正常返回结果的，需要catch住，返回一个false
        }
	}







}
