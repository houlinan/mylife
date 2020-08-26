package cn.houlinan.mylife.thread.concurrency.designPatterns.chapter1;

/**
 * @className :SingletonObject2
 * @description :
 * @Author :hou.linan
 * @date :2020/7/21 10:23
 */
public class SingletonObject4 {


    private static SingletonObject4 singletonObject ;

    private SingletonObject4(){};

    /**
     * 这里使用double check方式，解决多线程并发和synchronized 字性能的问题
     * 但是也会有一个问题：如果类中有大量的gei set方法，可能会出现 空指针异常的情况
     * 因为java的重排序问题，如果一个线程进入判断 == null 返回new object对象后
     * 另外一个线程马上进入返回currObject对象，但是类中的get  set方法还没有初始出完成，
     * 使用时会出现空指针异常
     * */
    public  SingletonObject4 getSingletonObject (){
        if(singletonObject == null ){
            synchronized (SingletonObject4.class){
                if(singletonObject == null) return new SingletonObject4() ;
            }
        }
        return singletonObject ;

    }

}
