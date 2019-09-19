package cn.houlinan.mylife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RequestMapping("/test")
@RestController
public class MylifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MylifeApplication.class, args);
    }



    @RequestMapping("/hello")
    public String hello(){
        return "holle";
    }
}
