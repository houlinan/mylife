package cn.houlinan.mylife.thread.concurrency.basis.chapter10;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * DESC：Lock 实现类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/13
 * Time : 14:48
 */
public class BooleanLock implements Lock {

    /***
     * 如果初始值 = true  标识锁对象已经被拿走了
     * 如果初始值 = false  标识锁暂时是闲置的，
     * */
    private boolean initValue ;

    private Collection<Thread> blockedThreadList = new ArrayList<>();

    private Thread currThread ;

    BooleanLock(){
        this.initValue = false ;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while (initValue){
            blockedThreadList.add(Thread.currentThread());
            this.wait();
        }
        blockedThreadList.remove(Thread.currentThread());
        this.initValue = true ;
        this.currThread = Thread.currentThread();
    }

    @Override
    public synchronized void lock(Long mills) throws InterruptedException, TimeoutException {
        if(mills <= 0 ) lock();
        long hasRemaining = mills ;
        long endTime =  System.currentTimeMillis() + mills ;
        while(initValue){
            if(hasRemaining <= 0 ) throw new TimeoutException("超时");
            blockedThreadList.add(Thread.currentThread());
            this.wait(mills);
            hasRemaining = endTime - System.currentTimeMillis() ;
        }
        this.initValue = true ;
        this.currThread = Thread.currentThread() ;
    }

    @Override
    public synchronized void unLock() {
        if(Thread.currentThread() == currThread){
            this.initValue = false ;
            Optional.of(StrUtil.format("{} 线程已经被释放！！" , Thread.currentThread().getName()))
                    .ifPresent(System.out::println);
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockThread() {
        //这里设置list集合不可以修改，如果修改就报错
        return Collections.unmodifiableCollection(blockedThreadList);
    }

    @Override
    public int getBlockSize() {
        return blockedThreadList.size();
    }
}
