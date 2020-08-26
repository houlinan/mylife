package cn.houlinan.mylife.thread.concurrency.basis.chapter10;

import cn.hutool.core.util.StrUtil;

import java.util.Optional;

/**
 * DESC：程序主动挂掉后通知并释放资源后关闭
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/7/16
 * Time : 14:37
 */
public class ExitCapture {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
              Optional.of(StrUtil.format(" 程序出现异常情况 ，准备挂掉，挂掉前先准备释放资源"))
                                  .ifPresent(System.out::println);
              notifyAndRelease();
        }));
        int i = 0 ;
        while (true){
            try{
                Thread.sleep(1_000);
                  Optional.of(StrUtil.format("程序正在工作"))
                                      .ifPresent(System.out::println);
                  i++  ;
            }catch (Exception e){
                e.printStackTrace();
            }
            if(i > 20 )throw new RuntimeException("出现异常！！");
        }
    }

    private static void notifyAndRelease(){
          Optional.of(StrUtil.format("准备释放并通知"))
                              .ifPresent(System.out::println);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
          Optional.of(StrUtil.format("释放成功"))
                              .ifPresent(System.out::println);
    }



}
