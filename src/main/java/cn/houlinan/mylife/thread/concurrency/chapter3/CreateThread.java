package cn.houlinan.mylife.thread.concurrency.chapter3;

/**
 * DESC：线程的创建和构造方法1
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 16:16
 */
public class CreateThread {

    public static void main(String[] args) {
        Thread t1 = new Thread();
        Thread t2 = new Thread(){
            @Override
            public void run(){
                System.out.println("---------------");
            }
        };
        t1.start();
        System.out.println(t1.getName());
        t2.start();
        System.out.println(t2.getName());

        Thread t3 = new Thread("my-name T3");

        Thread t4 = new Thread(() -> System.out.println("runnable ..."));
        Thread t5 = new Thread(() -> System.out.println("runnable ..."), "runnalbe - 5 ");

    }



}
