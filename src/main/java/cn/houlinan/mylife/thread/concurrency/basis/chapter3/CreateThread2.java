package cn.houlinan.mylife.thread.concurrency.basis.chapter3;

import java.util.Arrays;

/**
 * DESC：线程的创建和构造方法1
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 16:16
 */
public class CreateThread2 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() ->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println(threadGroup.activeCount());

        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        Arrays.asList(threads).forEach(System.out::println);
//        for(Thread th :threads) {
//            System.out.println(th);
//        }


    }



}
