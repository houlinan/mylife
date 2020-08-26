package cn.houlinan.mylife.thread.concurrency.basis.chapter10;

import cn.hutool.core.util.StrUtil;

import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/13
 * Time : 16:53
 */
public class LockTest {

    public static void main(String[] args) {
        final BooleanLock booleanLock = new BooleanLock() ;
        Stream.of("t1" , "t2" , "t3" , "t4").forEach(t ->{
            new Thread(() -> {
                try {
                    booleanLock.lock(1000L);
                    Optional.of(StrUtil.format("{}线程抢到了锁，这里准备开始工作" , Thread.currentThread().getName())).
                            ifPresent(System.out::println);
                    work();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                      Optional.of(StrUtil.format(Thread.currentThread().getName() + "超时出现异常了"))
                                          .ifPresent(System.out::println);
                } finally {
                    booleanLock.unLock();
                }
            } , t).start();
        });
    }
    private static void work() throws InterruptedException{
        Optional.of(StrUtil.format("{} 线程 正在工作中。。。。" , Thread.currentThread().getName()))
                .ifPresent(System.out::println);
        Thread.sleep(40_000);
    }
}
