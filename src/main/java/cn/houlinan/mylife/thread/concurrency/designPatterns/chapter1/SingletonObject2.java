package cn.houlinan.mylife.thread.concurrency.designPatterns.chapter1;

/**
 * @className :SingletonObject2
 * @description :
 * @Author :hou.linan
 * @date :2020/7/21 10:23
 */
public class SingletonObject2 {


    private static  SingletonObject2 singletonObject ;

    private SingletonObject2(){};

    public SingletonObject2 getSingletonObject (){
        /***
         * 单例模式添加懒加载， 但是会出现一个问题，
         * 多线程并发访问进入判断 if object =? null 的时候，
         * 放弃cpu执行权，另外线程进入后new出，第一个线程再次获取cpu执行权的时候，还会new一个
         * */
        if(singletonObject == null ) return new SingletonObject2();
        return singletonObject ;
    }

}
