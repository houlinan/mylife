package cn.houlinan.mylife.thread.concurrency.designPatterns.chapter1;

/**
 * @className :SingletonObject6
 * @description : 单例设计模式  使用Holder 方式
 * @Author :hou.linan
 * @date :2020/7/21 10:56
 */
public class SingletonObject6 {
    private SingletonObject6(){}

    /**
     * 这里使用内部类方式， 类中的static修饰的只会在调用的时候加载，并且只加载一次
     * */
    private static class InstanceHolder{
        private final static SingletonObject6 instance = new SingletonObject6();
    }
    public static SingletonObject6 getInstance(){
        return InstanceHolder.instance;
    }

}
