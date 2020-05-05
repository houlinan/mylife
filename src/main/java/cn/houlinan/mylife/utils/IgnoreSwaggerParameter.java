package cn.houlinan.mylife.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/2
 * Time : 10:02
 */
// swagger忽略的参数
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface  IgnoreSwaggerParameter {
}
