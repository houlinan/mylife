package cn.houlinan.mylife.thread.concurrency.basis.chapter10;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

/**
 * DESC：锁接口
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/13
 * Time : 14:41
 */
public interface Lock {

    class TimeOutException extends Exception{
        public TimeOutException(String message){
            super(message);
        }
    }

    void lock() throws InterruptedException ;

    void lock(Long mills) throws InterruptedException , TimeoutException ;

    void unLock();

    //查看排队线程
    Collection<Thread> getBlockThread();

    //查看排队线程数量
    int getBlockSize() ;

}
