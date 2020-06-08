package cn.houlinan.mylife.filter;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/30
 * Time : 23:55
 */
import cn.houlinan.mylife.service.SchedulerTaskService.ResetUserErrorTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Slf4j
//@Async //可加在类或方法，开启异步事件的支持
public class SchedulerTask {

    @Autowired
    ResetUserErrorTimeService resetUserErrorTimeService ;


    //cron表达式：每隔5秒执行一次
    @Scheduled(cron = "0 01 00 * * ?")
    public void scheduled(){
        //零点重置用户错误次数
        resetUserErrorTimeService.ResetUserErrorTime();
    }

}
