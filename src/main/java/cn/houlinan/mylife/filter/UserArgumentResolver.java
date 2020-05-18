package cn.houlinan.mylife.filter;

import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.context.UserContext;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    UserRepository userRepository;


    //判断参数类型是否是支持的，支持则范返回true
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == User.class;
    }

    //处理参数，相当于初始化参数
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory
    ) throws Exception {
        //参数处理逻辑
        //1,获得response
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        //2,获取token
        String paramToken = request.getParameter(UserConstant.USER_TOKEN_NAME);
        String openId = request.getParameter("operId");
        String cookieToken = getCookieValue(request, UserConstant.USER_TOKEN_NAME);
        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(openId)) {
            throw new Exception("请登录");
        }
        if (CMyString.isEmpty(paramToken)) paramToken = cookieToken;
        log.info("获取到的用户token = " + paramToken);
        String userId = redisOperator.get(UserConstant.SESSION_LOGIN_USER + ":" + paramToken);

        User user = userRepository.findUserById(userId);
        if(user == null ){
            user = userRepository.findUserByOpenId(openId);
            paramToken = openId ;
        }

        if(user == null ) throw new Exception("请登录");

        UserContext.setUser(user);
        //延时redis中的时间
        redisOperator.set(UserConstant.SESSION_LOGIN_USER + ":" + paramToken , user.getId() , 1000*60*30);

        return user ;
    }

    //遍历cookie获得需要的cookie（保存用户token的cookie的value，也就是token）
    private String getCookieValue(HttpServletRequest request, String cookieName) {

        Cookie[] cookies = request.getCookies();
        if(cookies ==null || cookies.length == 0)  return null ;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }

        }
        return null;
    }
}