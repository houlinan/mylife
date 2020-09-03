package cn.houlinan.mylife.filter;

/**
 * @className :StartService
 * @DESC :
 * @Author :hou.linan
 * @date :2020/9/3 14:57
 */

import cn.houlinan.mylife.constant.LinuxConstant;
import cn.houlinan.mylife.entity.primary.repository.LinuxCommandRepository;
import cn.houlinan.mylife.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法
 * 通过设置Order的value来指定执行的顺序
 */
@Component
@Order(value = 1)
@Slf4j
public class StartService implements ApplicationRunner {

    @Autowired
    RedisOperator redisOperator ;

    @Autowired
    LinuxCommandRepository linuxCommandRepository ;

    private static void logStartServiceMethod(String msg){
        log.info("StartService 程序启动执行命令 -------  执行 ：【"  +msg + "】  已经执行完毕！！！！！！！！！！！" );
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //将linux的命令添加到redis中
        redisOperator.set(LinuxConstant.LINUX_ALL_DATA_KEY  , JSONArray.fromObject(linuxCommandRepository.findAll()).toString());
        logStartServiceMethod("将linux的命令添加到redis中");
    }
}
