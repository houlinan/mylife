package cn.houlinan.mylife.constant;

import cn.houlinan.mylife.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/19
 * Time : 10:46
 */
@Controller
@RequestMapping("/test")
@RestController
public class Test {

    @Autowired
    RedisOperator redisOperator;

    @RequestMapping("/redis/set")
    public void testSet(){
        redisOperator.set("1" , "22222" , 10000);
    }
}
