package cn.houlinan.mylife.thread.concurrency.chapter1;

import sun.plugin2.message.Message;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 11:15
 */
public class TemplateMethod {
    public final void print(String message ){
        System.out.println("############################");
        wrapPrint(message);
        System.out.println("############################");
    }

    protected  void  wrapPrint(String message){
        System.out.println("@@@@@@@@@@" + message + "@@@@@@@@@@@");
    }

    public static void main(String[] args) {
        TemplateMethod t1 = new TemplateMethod(){
            @Override
            protected void wrapPrint(String message){
                System.out.println("%%%%" + message + "%%%%%");
            }
        };
        t1.print("hello Thread");


        TemplateMethod t2 = new TemplateMethod(){
            @Override
            protected void wrapPrint(String message){
                System.out.println("&&&&&&&&" + message + "&&&&&&&&&&");
            }
        };
        t2.print("hello Thread");
    }
}
