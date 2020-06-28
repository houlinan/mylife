package cn.houlinan.mylife.thread.concurrency.chapter4;

import org.springframework.beans.factory.annotation.Value;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 11:15
 */
public class DeamonThread {

    public static void main(String[] args) {
      Thread t  = new Thread(){
          @Override
          public void run() {
              super.run();
          }
      };
    }
}
