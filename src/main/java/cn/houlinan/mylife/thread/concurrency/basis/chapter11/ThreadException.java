package cn.houlinan.mylife.thread.concurrency.basis.chapter11;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/16
 * Time : 14:58
 */
public class ThreadException {

    private static final int a = 10 ;
    private static final int b = 0 ;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(3_000l);
                int result = a / b ;
                System.out.println(result);
            } catch (InterruptedException e) {
            }
        });

        t1.setUncaughtExceptionHandler((thread ,e ) ->{
            System.out.println(e);
            System.out.println(thread);
        });
        t1.start();
    }

}
