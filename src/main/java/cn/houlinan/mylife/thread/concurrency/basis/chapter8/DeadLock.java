package cn.houlinan.mylife.thread.concurrency.basis.chapter8;

/**
 * DESC：死锁
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/10
 * Time : 10:48
 */
public class DeadLock {

    private OtherService otherService;
    private final Object LOCK = new Object() ;
    DeadLock( OtherService otherService){
        this.otherService = otherService ;
    }

    public void m1(){
        synchronized (LOCK){
            System.out.println(" m1 =========");
            otherService.s1();
        }
    }

    public void m2(){
        synchronized (LOCK){
           System.out.println(" m2 =========");
        }
    }
}
