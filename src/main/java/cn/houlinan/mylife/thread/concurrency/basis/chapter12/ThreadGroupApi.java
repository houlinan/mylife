package cn.houlinan.mylife.thread.concurrency.basis.chapter12;

/**
 * @className :ThreadGroupApi
 * @description :
 * @Author :hou.linan
 * @date :2020/7/17 9:36
 */
public class ThreadGroupApi  {


    public static void main(String[] args) {
        ThreadGroup tg1 = new ThreadGroup( "TG1");
        Thread t1 = new Thread(tg1 ,"t1"){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1_000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        ThreadGroup tg2 = new ThreadGroup( tg1 ,"TG2");
        Thread t2 = new Thread(tg2 ,"t2"){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1_000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        //评估线程组中活跃的数量
        System.out.println(tg1.activeCount());
        //有几个活跃的group
        System.out.println(tg1.activeGroupCount());
        //评估决定这个线程是否有权限改变
        tg1.checkAccess();
        //销毁 只有空的线程组才能销毁
        //tg1.destroy();
        


    }
}
