package cn.houlinan.mylife.thread.concurrency.designPatterns.chapter2;

import cn.houlinan.mylife.entity.Type;
import cn.hutool.core.util.StrUtil;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @className :WaitSet
 * @DESC :WaitSet
 * @Author :hou.linan
 * @date :2020/7/21 11:28
 */
public class WaitSet {

    private static final  Object OBJECT = new Object() ;

    /**
     * 1.所有对象都会有一个wait set,用来存放， 调用了该对象的wait方法之后， 进入block线程状态
     * 2.线程被notify之后，不一定立即得到执行
     * 3.线程从wait set中被唤醒的顺序不一定是FIFO ;
     * 4.线程被唤醒后，必须重新获取锁，获取线程的执行权后，会执行之前wait之后的代码（程序计数器会记住之前的执行代码）
     * */

    public static void main(String[] args) {
        IntStream.rangeClosed( 1 , 10).forEach( i ->{
            new Thread(i+""){
                @Override
                public void run() {
                    synchronized (OBJECT){
                        try {
                            Optional.of(StrUtil.format(
                                    "{} 线程已经进入了waitset队列" , Thread.currentThread().getName()
                            )) .ifPresent(System.out::println);
                            OBJECT.wait();
                            Optional.of(StrUtil.format(
                                    "{} 线程已经结束了waitset队列" , Thread.currentThread().getName()
                            )) .ifPresent(System.out::println);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });
    }


}
