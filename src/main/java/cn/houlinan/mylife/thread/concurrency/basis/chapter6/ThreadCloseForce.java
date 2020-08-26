package cn.houlinan.mylife.thread.concurrency.basis.chapter6;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/9
 * Time : 16:41
 */
public class ThreadCloseForce {

    public static void main(String[] args) {
        ThreadService threadService = new ThreadService();
        long start = System.currentTimeMillis();
        threadService.execute(() ->{
//            while (true){
//                //执行处理逻辑
//            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadService.shutdown(10_000);
        long end = System.currentTimeMillis();
        System.out.println(end - start );

    }

}
