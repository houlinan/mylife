package cn.houlinan.mylife.thread.concurrency.basis.chapter2;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 14:19
 */
public class BankVersion2 {

    public static void main(String[] args) {
        TicketWindowRunnable ticketWindowRunnable =  new TicketWindowRunnable();
        Thread  ticketWindow1 = new Thread(ticketWindowRunnable  ,  "一号窗口");
        Thread  ticketWindow2 = new Thread(ticketWindowRunnable  ,  "二号窗口");
        Thread  ticketWindow3 = new Thread(ticketWindowRunnable  ,  "三号窗口");

        ticketWindow1.start();
        ticketWindow2.start();
        ticketWindow3.start();


    }
}
