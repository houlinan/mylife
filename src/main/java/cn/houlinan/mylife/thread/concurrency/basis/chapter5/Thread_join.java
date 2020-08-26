package cn.houlinan.mylife.thread.concurrency.basis.chapter5;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/8
 * Time : 9:38
 */
public class Thread_join  {

    public static void main(String[] args) throws InterruptedException{
        Thread t1 = new Thread(() ->{
            IntStream.range(1 , 1000).forEach( i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
        });
        Thread t2= new Thread(() ->{
            IntStream.range(1 , 1000).forEach( i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Optional.of("all if task finish done").ifPresent(System.out::println);
        IntStream.range(1 , 1000).forEach( i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }
}
