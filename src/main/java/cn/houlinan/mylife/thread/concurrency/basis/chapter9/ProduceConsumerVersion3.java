package cn.houlinan.mylife.thread.concurrency.basis.chapter9;

import java.util.stream.Stream;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/10
 * Time : 17:26
 */
public class ProduceConsumerVersion3 {

    private int i = 0;

    private final Object LOCK = new Object();
    private volatile boolean isProduced = false;

    public void produce() {
        synchronized (LOCK) {
            while (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            System.out.println("P -> " + i);
            LOCK.notifyAll();
            isProduced = true;
        }
    }

    public void consume() {
        synchronized (LOCK) {
            while (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("C -> " + i);
                LOCK.notifyAll();
                isProduced = false;
            }
        }
    }

    public static void main(String[] args) {
        ProduceConsumerVersion3 pc = new ProduceConsumerVersion3();

        Stream.of("P1","P2", "P3").forEach( a -> {
            new Thread(a) {
                @Override
                public void run() {
                    while (true)
                        pc.produce();
                }
            }.start();
        });

        Stream.of("C1","C2", "C3").forEach( a -> {
            new Thread(a) {
                @Override
                public void run() {
                    while (true)
                        pc.consume();
                }
            }.start();
        });

    }


}
