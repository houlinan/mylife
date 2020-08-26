package cn.houlinan.mylife.thread.concurrency.basis.chapter6;

/**
 * DESC：针对创建新线程，执行较长任务，想强制关闭
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/8
 * Time : 15:09
 */
public class ThreadService {

    private Thread executeThread ;

    private boolean isFinished = false ;

    /**
     *DESC：通过线程的方式执行异步任务
    */
    public void execute(Runnable runnable){
        executeThread = new Thread(){
            @Override
            public void run() {
                Thread runner = new Thread(runnable);
                runner.setDaemon(true);
                runner.start();
                try {
                    runner.join();
                    isFinished = true ;
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        };
        executeThread.start();

    }

    /**
     *DESC: 根据毫秒数干掉异步任务
    */
    public void shutdown(long mills){
        long currTime = System.currentTimeMillis();
        while (!isFinished){
            if((System.currentTimeMillis() - mills) >= currTime){
                System.out.println("任务超时，需要打断他！");
                executeThread.interrupt();
                break;
            }
            try{
                executeThread.sleep(1);
            }catch(Exception e){
                System.out.println("执行线程被打断");
                break ;
            }
        }
    }



}
