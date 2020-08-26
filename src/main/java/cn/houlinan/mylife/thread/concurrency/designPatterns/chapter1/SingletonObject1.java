package cn.houlinan.mylife.thread.concurrency.designPatterns.chapter1;

/**
 * @className :SingletonObject1
 * @description : 单例设计默认
 * @Author :hou.linan
 * @date :2020/7/21 10:19
 */
public class SingletonObject1 {

    /**
     * 单例设计模式无法懒加载
     * */

    private static final SingletonObject1 singletonObject1 = new SingletonObject1();

    private SingletonObject1(){}

    public SingletonObject1 getSingletonObject1(){
        return singletonObject1 ;
    }


}
