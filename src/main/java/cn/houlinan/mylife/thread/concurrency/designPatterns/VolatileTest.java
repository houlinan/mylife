package cn.houlinan.mylife.thread.concurrency.designPatterns;

/**
 * @className :VolatileTest
 * @DESC :Volatile 关键字测试
 * @Author :hou.linan
 * @date :2020/7/21 14:20
 */
public class VolatileTest {

    private volatile static int INIT_VALUE = 0 ;
    private static final int MAX_VALUE = 5;

    public static void main(String[] args) {
        new Thread(()->{
            int localValue =INIT_VALUE;
            while (localValue < MAX_VALUE){
                if(localValue != INIT_VALUE ){
                    System.out.println("准备去更新 ，现在的数字是 : " + INIT_VALUE);
                    localValue = INIT_VALUE ;
                }
            }
        } , "reader").start();
        new Thread(()->{
            int localValue =INIT_VALUE;
            while (INIT_VALUE < MAX_VALUE){
                     ++localValue;
                    System.out.println("更新 ，现在的数字是 : " + localValue);
                    INIT_VALUE = localValue ;
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } , "update").start();

    }


}
