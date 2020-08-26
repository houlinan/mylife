package cn.houlinan.mylife.thread.concurrency.basis.chapter5;

import java.util.stream.IntStream;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/8
 * Time : 9:38
 */
public class Thread_join2 {

    public static void main(String[] args) throws InterruptedException{

        Thread t1 = new Thread(() ->{
           System.out.println("thread start ...");
           try {
               Thread.sleep(10_000);
           }catch (Exception e){
                e.printStackTrace();
           }
            System.out.println("thread end  ...");
        });
        t1.start();
        t1.join(100 ,10);
        System.out.println("start main thread");
        IntStream.range(1 , 1000).forEach( i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }
}
