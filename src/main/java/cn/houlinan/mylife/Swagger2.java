package cn.houlinan.mylife;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * DESC：swagger2
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/3
 * Time : 9:39
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    /**
     * @Description:swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     */

//        // 为swagger添加header参数可供输入
//        ParameterBuilder userTokenHeader = new ParameterBuilder();
//        ParameterBuilder userIdHeader = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        userTokenHeader.name("headerUserToken").description("userToken")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).build();
//        userIdHeader.name("headerUserId").description("userId")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).build();
//        pars.add(userTokenHeader.build());
//        pars.add(userIdHeader.build());

//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//                .apis(RequestHandlerSelectors.basePackage("com.trs.wx.controller"))
//                .paths(PathSelectors.any()).build()
//                .globalOperationParameters(pars);
//

        @Bean
        public Docket createRestApi() {
            return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()

//            return new Docket(DocumentationType.SWAGGER_2)
//                    .apiInfo(apiInfo())
//                    .pathMapping("/")
//                    .select() // 选择那些路径和api会生成document
//                    .apis(RequestHandlerSelectors.any())// 对所有api进行监控
//                    //不显示错误的接口地址
//                    .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
//                    .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
//                    .build();
                    .apis(RequestHandlerSelectors.basePackage("cn.houlinan.mylife.controller"))
                    .paths(PathSelectors.any()).build();
        }

    /**
     * @Description: 构建 api文档的信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置页面标题
                .title("myLife.api接口文档")
                // 设置联系人
                .contact(new Contact("侯立男", "http://www.houlinan.cn", "houlinan@vip.qq.com"))
                // 描述
                .description("mylife api 1.0接口测试地址")
                // 定义版本号
                .version("1.0").build();
    }



}
