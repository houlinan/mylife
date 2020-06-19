package cn.houlinan.mylife.thread.concurrency.chapter2;

/**
 * DESC：出票窗口系统
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 11:44
 */
public class TicketWindow extends  Thread{

    private final String name ;

    private static final int MAX = 50 ;

    private static int index = 1 ;

    TicketWindow(String name ){
        this.name = name ;
    }

    @Override
    public void run(){
        while ( index <= MAX){
            System.out.println("柜台" + name + "点击了：" + (index++));
        }
    }
}
