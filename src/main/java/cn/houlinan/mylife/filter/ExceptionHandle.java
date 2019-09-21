package cn.houlinan.mylife.filter;


import cn.houlinan.mylife.utils.HHJSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DESC：异常处理类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/7/2
 * Time : 9:29
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public HHJSONResult handle(Exception e) {
        log.info("进入error");


        e.printStackTrace();
        return  HHJSONResult.errorMsg(e.getMessage());
    }

}
