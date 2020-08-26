package cn.houlinan.mylife.thread.concurrency.basis.chapter6;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/8
 * Time : 14:46
 */
public class ThreadCloseGraceFul {

    private static class Worker extends Thread{
        private volatile boolean start = true ;

        @Override
        public void run() {
            while (start){}
        }

        public void shotdown(){
            this.start = false ;
        }
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker.shotdown();
    }


}
