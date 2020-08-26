package cn.houlinan.mylife.thread.concurrency.designPatterns.chapter1;

import java.util.stream.IntStream;

/**
 * @className :SingletonObject7
 * @description : 使用枚举的方式创建单例对象
 * @Author :hou.linan
 * @date :2020/7/21 11:08
 */
public class SingletonObject7 {

    private SingletonObject7(){}

    private enum Singleton{
        INSTANCE ;

        private final SingletonObject7 instance ;

        Singleton(){
            instance = new SingletonObject7();
        }

        public SingletonObject7 getInstance(){
            return instance ;
        }
    }

    public static  SingletonObject7 getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    public static void main(String[] args) {
        IntStream.rangeClosed( 1, 100 ).forEach( i ->{
            new Thread(i+""){
                @Override
                public void run() {
                    System.out.println(SingletonObject7.getInstance());
                }
            }.start();
        });
    }


}
