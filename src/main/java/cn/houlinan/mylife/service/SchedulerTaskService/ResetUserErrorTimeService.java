package cn.houlinan.mylife.service.SchedulerTaskService;

import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DESC：每日0时重置用户错误次数
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/31
 * Time : 9:04
 */
@Service
@Slf4j
public class ResetUserErrorTimeService {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    RedisOperator redisOperator;


    public void ResetUserErrorTime(){
        List<User> allUser = userRepository.findAll() ;
        allUser.forEach(user ->{
            log.info("准备执行用户【{}】的重置错误次数操作" , user.getUserName());
            //延时redis中的时间.
            redisOperator.set(UserConstant.RESET_USER_ERROR_TIME + ":" + user.getId() , "0" , 1000*60*30*24);
        });
    }

}
