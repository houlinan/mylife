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

        String result;
        String stackTrace = getStackTraceString(e);
        String exceptionType = e.toString();
        String exceptionMessage = e.getMessage();

        log.error(String.format("%s : %s \r\n %s", exceptionType, exceptionMessage, stackTrace));

        return  HHJSONResult.errorMsg(e.getMessage());
    }

    //打印异常堆栈信息
    public static String getStackTraceString(Throwable ex){//(Exception ex) {
        StackTraceElement[] traceElements = ex.getStackTrace();

        StringBuilder traceBuilder = new StringBuilder();

        if (traceElements != null && traceElements.length > 0) {
            for (StackTraceElement traceElement : traceElements) {
                traceBuilder.append(traceElement.toString());
                traceBuilder.append("\n");
            }
        }

        return traceBuilder.toString();
    }

}
