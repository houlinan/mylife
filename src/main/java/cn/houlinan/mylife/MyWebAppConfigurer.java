package cn.houlinan.mylife;



import cn.houlinan.mylife.filter.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/29
 * Time : 10:12
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {


    @Value("${upload.path}")
    private String uploadPath ;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");


        registry
                .addResourceHandler("/mylifeDatas/**" )
//                .addResourceLocations(
//                        "classpath:/META-INF/resources/" )
                .addResourceLocations(
                        "file:"+uploadPath);
    }

    @Autowired
    UserArgumentResolver userArgumentResolver;
    @Override
//    参数解析器，被框架回调
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(userArgumentResolver);
    }


}