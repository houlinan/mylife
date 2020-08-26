package cn.houlinan.mylife.thread.concurrency.basis.chapter9;

import cn.hutool.core.util.StrUtil;

import java.util.*;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/13
 * Time : 10:32
 */
public class CaptureService {

    final private static LinkedList<Control> controlList = new LinkedList<>();

    private static  int maxWorkNumber =  5 ;

    public static void main(String[] args) {

        List<Thread> woker = new ArrayList<>();
        Arrays.asList("m1", "m2", "m3", "m4", "m5", "m6", "m7", "m8", "m9", "m10").stream()
                .map(CaptureService::createCapture).forEach(
                t -> {
                    t.start();
                    woker.add(t);
                });
        woker.stream().forEach( t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Optional.of("所有的线程都已经工作完成").ifPresent(System.out::println);
    }

    public static Thread createCapture(String name) {

        return new Thread(() -> {
            Optional.of(StrUtil.format("当前线程：{}准备开始工作。。。" ,  Thread.currentThread().getName()) )
                    .ifPresent(System.out::println);
            synchronized (controlList){
                while (controlList.size() > maxWorkNumber ){
                    try {
                        controlList.wait();
                    } catch (InterruptedException e) {
                    }
                }
                controlList.addLast(new Control());
            }

            Optional.of(StrUtil.format("当前线程：{}正在工作" , Thread.currentThread().getName())).ifPresent(System.out::println);
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) { }

            synchronized (controlList){
                Optional.of(StrUtil.format("当前线程：{}工作完成。。。。" , Thread.currentThread().getName())).ifPresent(System.out::println);
                controlList.removeFirst();
                controlList.notifyAll();
            }
        }, name);
    }
    private static class Control{}
}
