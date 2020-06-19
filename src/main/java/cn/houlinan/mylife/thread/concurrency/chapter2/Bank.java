package cn.houlinan.mylife.thread.concurrency.chapter2;

/**
 * DESC：银行大厅
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 11:45
 */
public class Bank {
    public static void main(String[] args) {
        TicketWindow t1 = new TicketWindow("柜台1")  ;
        t1.start();

        TicketWindow t2 = new TicketWindow("柜台2")  ;
        t2.start();

        TicketWindow t3 = new TicketWindow("柜台3")  ;
        t3.start();
    }
}
