package cn.houlinan.mylife.thread.concurrency.basis.chapter11;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.Optional;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/16
 * Time : 15:21
 */
public class Test2 {

    public  static void test(){
        Arrays.asList(Thread.currentThread().getStackTrace()).stream()
                .filter( a -> !a.isNativeMethod()).forEach( a ->
                {
                    Optional.of(
                            StrUtil.format("类：{}  --  方法 ：{}  -- 行数：{}" , a.getClassName() , a.getMethodName()  , a.getLineNumber()))
                            .ifPresent(System.out::println);
                }
                );

    }
}
