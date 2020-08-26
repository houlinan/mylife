package cn.houlinan.mylife.thread.concurrency.basis.chapter9;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/10
 * Time : 17:26
 */
public class ProduceConsumerVersion2 {

    private int i = 0 ;

    private final Object LOCK = new Object() ;
    private volatile  boolean isProduced = false ;

    public void produce(){
        synchronized (LOCK){
            if(isProduced){
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                i++ ;
                System.out.println("P -> " +i );
                LOCK.notify();
                isProduced = true ;
            }
        }
    }

    public void consume(){
        synchronized (LOCK){
            if(isProduced){
                System.out.println("C -> " + i);
                LOCK.notify();
                isProduced = false ;
            }else{
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProduceConsumerVersion2 pc =  new ProduceConsumerVersion2();

        new Thread(){
            @Override
            public void run() {
                while (true)
                    pc.produce();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                while (true)
                    pc.consume();
            }
        }.start();
    }




}
