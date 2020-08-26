package cn.houlinan.mylife.thread.concurrency.basis.chapter13;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @className :SimpleThreadPool
 * @description :线程池
 * @Author :hou.linan
 * @date :2020/7/17 9:57
 */
public class SimpleThreadPool extends Thread {

    private int size;

    private final int queueSize;

//    private final static int DEFAULT_SIZE = 10 ;

    private final static int DEFAULT_TASK_QUEUE_SIZE = 2000;

    //名字 自增添加
    private static volatile int seq;
    //线程名字前缀
    private static final String THREAD_PREFIX = "SIMPLE_THREAD_POOL_";
    //线程组
    private final static ThreadGroup THREADGROUP = new ThreadGroup("poolGroup");

    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    private static volatile boolean destroy = false;

    private int min;
    private int max;
    private int active;

    private static final int MIN_DEFAULT = 4;
    private static final int ACTIVE_DEFAULT = 8;
    private static final int MAX_DEFAULT = 12;

    private final DiscardPolicy discardPolicy;

    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("请求添加数量已超线程池数量上限，请稍后重试");
    };

    public SimpleThreadPool() {
        this(MIN_DEFAULT, ACTIVE_DEFAULT, MAX_DEFAULT, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int min, int active, int max, int queueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        inti();
    }

    private void inti() {
        for (int i = 0; i < this.min; i++) {
            createWorkTash();
        }
        this.size = min;
        this.start();
    }

    public void submit(Runnable runnable) {
        if (destroy) {
            throw new IllegalArgumentException("线程已经关闭，无法继续添加任务");
        }
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > queueSize) {
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    @Override
    public void run() {
        while (!destroy) {
            System.out.printf("线程池中# MIN:%d  ,ACTIVE:%d , MAX:%d , CURRENT:%d , QUEUE_SIZE:%d\r\n",
                    this.min, this.active, this.max, this.size, TASK_QUEUE.size());
            try {
                Thread.sleep(100);
                if (TASK_QUEUE.size() > active && size < active) {
                    for (int i = this.min; i < this.active; i++) {
                        createWorkTash();
                    }
                    this.size = active;
                } else if (TASK_QUEUE.size() > max && size < max) {
                    for (int i = this.active; i < this.max; i++) {
                        createWorkTash();
                    }
                    this.size = max;
                }
                synchronized (THREAD_QUEUE) {
                    if (TASK_QUEUE.isEmpty() && size > active) {
                        int releaseSize = size - active;
                        for (Iterator<WorkerTask> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            if (releaseSize <= 0) {
                                break;
                            }
                            WorkerTask next = it.next();
                            if (next.getTaskState() != TaskState.BLOCKED) {
                                next.close();
                                next.interrupt();
                                it.remove();
                                releaseSize--;
                            }
                        }
                        size = active;
                        System.out.println("这里将线程的数量调整为active");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createWorkTash() {
        WorkerTask workerTask = new WorkerTask(THREADGROUP, THREAD_PREFIX + (seq++));
        workerTask.start();
        THREAD_QUEUE.add(workerTask);
    }

    public void shutdown() {
        while (!TASK_QUEUE.isEmpty()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (THREAD_QUEUE) {
            int initVal = THREAD_QUEUE.size();
            while (initVal > 0) {
                for (WorkerTask workerTask : THREAD_QUEUE) {
                    if (workerTask.getTaskState() == TaskState.BLOCKED) {
                        workerTask.interrupt();
                        workerTask.close();
                        initVal--;
                    } else {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        this.destroy = true;
        System.err.println("线程池内的工作已经全部关闭");

    }

    public int getSize() {
        return size;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public boolean isDestroy() {
        return this.destroy;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getActive() {
        return active;
    }

    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    public static class DiscardException extends RuntimeException {
        DiscardException(String message) {
            super(message);
        }
    }

    public interface DiscardPolicy {
        void discard() throws DiscardException;
    }

    private static class WorkerTask extends Thread {
        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup threadGroupg, String name) {
            super(threadGroupg, name);
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        @Override
        public void run() {
            OUTER:
            // 标签写法(标记法)，如果wait出现异常， 直接跳转到指定的标签位置，再次循环
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break OUTER;
                        }
                    }//获取第一个
                    runnable = TASK_QUEUE.removeFirst();
                }
                if (runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }

    public static void syso(int a) {
        System.out.println(a);
    }

    public static void main(String[] args) {
//        SimpleThreadPool simpleThreadPool = new SimpleThreadPool(6 , 10  ,DEFAULT_DISCARD_POLICY );
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool();
        IntStream.rangeClosed(0, 40).forEach(i -> {
            simpleThreadPool.submit(() -> {
//                System.out.println(" -------------------------------------------------------- ");
                System.out.println("【" + i + "】当前线程：" + Thread.currentThread().getName() + "正在工作。。 ");
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println("【"+i+"】当前线程：" + Thread.currentThread().getName() + "结束工作。。 ");
//                System.out.println(" -------------------------------------------------------- ");
            });
        });

        try {
            Thread.sleep(5_000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        simpleThreadPool.shutdown();
//        simpleThreadPool.submit(() -> System.out.println(1132));
    }


}
