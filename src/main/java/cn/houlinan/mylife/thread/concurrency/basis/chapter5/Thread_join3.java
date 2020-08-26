package cn.houlinan.mylife.thread.concurrency.basis.chapter5;


import cn.hutool.core.util.StrUtil;

import java.util.Optional;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/8
 * Time : 9:38
 */
public class Thread_join3 {

    public static void main(String[] args) throws InterruptedException{
        long startTime = System.currentTimeMillis();
        Thread t1 = new Thread (new CaptureRunnable("c1" , 10_000));
        Thread t2 = new Thread (new CaptureRunnable("c2" , 20_000));
        Thread t3 = new Thread (new CaptureRunnable("c3" , 5_000));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        long endTime = System.currentTimeMillis();
        Optional.of(StrUtil.format("已经全部结束，开始时间：{} ， 结束时间：{}  . 总共用时：{}"  ,
                startTime ,endTime , endTime-startTime)).ifPresent(System.out::println);

    }
}
class CaptureRunnable implements  Runnable{
    private String machineName ;
    private long spendTime ;
    CaptureRunnable(String machineName ,long spendTime ){
        this.machineName = machineName ;
        this.spendTime = spendTime ;
    }
    @Override
    public void run() {
        try{
            Thread.sleep(spendTime);
            System.out.println(machineName + " 已经完成");
        }catch (Exception e){
        }
    }
    public String getResul(){
        return machineName + "  已经完成";
    }
}
