/*
 *	History				Who				What
 *  2016年6月17日			Administrator			Created.
 */
package cn.houlinan.mylife.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;


/**
 * Description: <BR>
 * Title: TRS 杭州办事处互动系统（TRSAPP）<BR>
 *
 * @author liu.zhuan
 * @version 1.0
 * @ClassName: SpringContextUtil
 * @Copyright: Copyright (c) TRS北京拓尔思信息技术股份有限公司<BR>
 * @Company: TRS北京拓尔思信息技术股份有限公司杭州办事处(www.trs.com.cn)<BR>
 * @date 2016年6月17日 上午11:20:45
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;
    private static String anotherAppAction;

    @Override
    @SuppressWarnings("static-access")
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        this.context = contex;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static Object getBean(Class<?> classname) {
        return context.getBean(classname);
    }

    public static String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }

    /**
     * @return the anotherAppAction
     */
    public static String getAnotherAppAction() {
        return anotherAppAction;
    }

    /**
     * @param anotherAppAction the anotherAppAction to set
     */
    @SuppressWarnings("static-access")
    public void setAnotherAppAction(String anotherAppAction) {
        this.anotherAppAction = anotherAppAction;
    }

}
