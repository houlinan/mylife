package cn.houlinan.mylife.thread.concurrency.chapter2;

/**
 * DESC：实现Runnable  接口
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 14:15
 */
public class TicketWindowRunnable implements Runnable {

    private int index = 0 ;
    private final static int MAX = 50  ;

    @Override
    public void run() {
        while (index <= MAX){
            System.out.println(Thread.currentThread() + " 的号码是：" + (index ++ ));
        }
    }
}
