package cn.houlinan.mylife.thread.concurrency.basis.chapter12;

import cn.hutool.core.util.StrUtil;

import java.util.Optional;

/**
 * @className :ThreadGroupCreate
 * @description :
 * @Author :hou.linan
 * @date :2020/7/16 15:53
 */
public class ThreadGroupCreate {

    public static void main(String[] args) {
        ThreadGroup tg1 = new ThreadGroup( "TG1");
        Thread t1 = new Thread(tg1 ,"t1"){
            @Override
            public void run() {
                while (true){
                    try {
                          Optional.of(StrUtil.format("name = {}",getThreadGroup().getName() ))
                                              .ifPresent(System.out::println);
                        Optional.of(StrUtil.format("getParent = {}",getThreadGroup().getParent().getName() ))
                                .ifPresent(System.out::println);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

}
