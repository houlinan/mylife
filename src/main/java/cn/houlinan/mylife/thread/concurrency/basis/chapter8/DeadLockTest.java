package cn.houlinan.mylife.thread.concurrency.basis.chapter8;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/10
 * Time : 11:28
 */
public class DeadLockTest {
    public static void main(String[] args) {
        OtherService otherService =  new OtherService() ;
        DeadLock deadLock = new DeadLock(otherService);
        otherService.setDeadLock(deadLock);
        new Thread(){
            @Override
            public void run() {
                while (true) deadLock.m1();
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                while (true ) otherService.s2();
            }
        }.start();
    }
}
