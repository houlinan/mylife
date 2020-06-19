package cn.houlinan.mylife.thread.concurrency.chapter1;

import lombok.extern.slf4j.Slf4j;

/**
 * DESC：读取数据库和写磁盘
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 10:34
 */
@Slf4j
public class TryConcurrency {

    public static void main(String[] args) {
        Thread t = new Thread("read thread"){
            @Override
            public void run(){
                readFromDb();
            }
        };
    }

    private static void readFromDb(){
        //读写数据库
        try {
            Thread.sleep(10L*1000);
        }catch (Exception e){

        }
    }


}
