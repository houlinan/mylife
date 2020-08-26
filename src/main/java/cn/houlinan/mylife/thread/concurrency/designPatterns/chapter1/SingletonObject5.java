package cn.houlinan.mylife.thread.concurrency.designPatterns.chapter1;

/**
 * @className :SingletonObject2
 * @description :
 * @Author :hou.linan
 * @date :2020/7/21 10:23
 */
public class SingletonObject5 {


    /**
     * volatile 关键字：并不能保证原子性，但是会保证内存的可见性、有序性（多个线程看到的数据是同一份）
     *  C++ 中会规定编译器不要去进行排序优化，遵循读的过程中必须是写入完整的数据，
     *
     *  这样的设计模式可以保证 1.产生的是单例数据   2.性能较好  3.懒加载
     * */
    private volatile static SingletonObject5 singletonObject ;

    private SingletonObject5(){};

    public SingletonObject5 getSingletonObject (){
        if(singletonObject == null ){
            synchronized (SingletonObject5.class){
                if(singletonObject == null) return new SingletonObject5() ;
            }
        }
        return singletonObject ;

    }

}
