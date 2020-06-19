package cn.houlinan.mylife.thread.concurrency.chapter3;

import java.util.Arrays;

/**
 * DESC：线程的创建和构造方法1
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 16:16
 */
public class CreateThread3 {
    private static int count = 0 ;

    public static void main(String[] args) {

        try {
            add(0);
        }catch (Error e){
            e.printStackTrace();
            System.out.println(count);
        }

    }
    private static void add(int a ){
        ++count ;
        add(a);
    }



}
