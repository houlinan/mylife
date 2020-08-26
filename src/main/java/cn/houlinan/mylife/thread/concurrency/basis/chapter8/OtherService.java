package cn.houlinan.mylife.thread.concurrency.basis.chapter8;

/**
 * DESC：其他人提供的service
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/10
 * Time : 10:48
 */
public class OtherService {
    private final Object LOCK = new Object() ;
    private DeadLock deadLock ;
    public void s1() {
        synchronized (LOCK){
            System.out.println("s1 ========== ");
        }
    }

    public void s2() {
        synchronized (LOCK){
            System.out.println("s2 ========== ");
            deadLock.m2();
        }
        LOCK.notify();
    }

    public void setDeadLock(DeadLock deadLock) {
        this.deadLock = deadLock;
    }
}
