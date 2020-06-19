package cn.houlinan.mylife.thread.concurrency.chapter3;

/**
 * DESC：线程的创建和构造方法1
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 16:16
 */
public class CreateThread4 {
    private static int count = 0;

    public static void main(String[] args) {

        Thread t = new Thread(null, new Runnable() {
            @Override
            public void run() {
                try {
                    add(0);
                } catch (Error e) {
                    e.printStackTrace();
                    System.out.println(count);
                }
            }
            private void add(int a) {
                ++count;
                add(a);
            }
        }, "", 1 << 24);
        t.start();
    }


}
