package cn.houlinan.mylife.thread.concurrency.designPatterns.chapter1;

/**
 * @className :SingletonObject2
 * @description :
 * @Author :hou.linan
 * @date :2020/7/21 10:23
 */
public class SingletonObject3 {


    private static SingletonObject3 singletonObject ;

    private SingletonObject3(){};

    /**
     * 使用synchronized 关键字可以解决多线程产生多实例的问题 ，
     * 但是使用synchronized 关键字会让多线程变成‘串’型化。影响服务性能
     * */
    public synchronized SingletonObject3 getSingletonObject (){
        if(singletonObject == null ) return new SingletonObject3();
        return singletonObject ;
    }

}
