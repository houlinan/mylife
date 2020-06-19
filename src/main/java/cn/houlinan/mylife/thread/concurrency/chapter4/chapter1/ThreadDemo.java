package cn.houlinan.mylife.thread.concurrency.chapter4.chapter1;

import org.springframework.beans.factory.annotation.Value;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 11:15
 */
public class ThreadDemo {

    public static void main(String[] args) {
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }
}
