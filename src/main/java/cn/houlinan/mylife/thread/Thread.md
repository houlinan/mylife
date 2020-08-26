#Thread 基础
> 1.java应用程序的main函数是一个线程， 是被jvm启动时调用， 线程名字叫main

> 2.实现一个线程， 必须创建Thread实例，override run 方法，并且调用.start 方法

> 3.在jvm启动后，实际上有多个线程，但是至少有一个非守护线程

> 4.当你调用一个线程start方法的时候， 此时至少有两个线程，一个是调用你的线程， 还有一个执行run方法的线程

> 5.线程的生命周期分为 new 、runnable 、 running 、 block 、 termate

> 6.创建线程对象Thread ， 默认有一个线程名， 从0开始， 以 Thread-Numner 命名；

> 7.如果在构造Thread没有传递或没有复写Theand的run（） 。该Thread将不会调用任何东西， 如果传递了Runnable接口的实例，
或者复写了Theand的run方法，则会执行该方法的逻辑单元（逻辑代码）

> 8.如果构造线程对象时，未传入ThreadGroup Thread 会默认父线程的ThreadGroup 做为该线程的ThreadGroup，
此时子线程和父线程将会在同一个ThreadGroup上； 

> 9.构造Thread的时候， 传入stackSize代表该线程占用的stack大小，如果没有指定stacksize大小， 默认是0 .0代表着
会被忽略，该参数会被JVI(虚拟机) 函数去使用 
* 需要注意：该参数会在一些平台有效，在有些平台无效1

> 10.守护线程：Daemon：守护线程，会跟随父线程结束。   非守护线程（默认），父线程结束之后，依旧运行。通过T.setDaemon(true)设为守护线程。
* 注意，只有在start之前设置才生效。

> 11.方法中使用synchronized 关键字的粒度越小越好，

>12.静态方法使用synchronized 关键字的锁对象是 class对象， 非静态synchronized锁对象是this

>13.线程1 占用锁A 去拿锁B资源， 并且同时 线程2 占用锁B 去拿锁A资源 。 有可能会产生死锁情况， 
* 死锁情况window机器可以通过jconsole 命令查询线程情况， linux 或者window 可以通过命令  
* -> jps
* -> jstack pid
* Found 1 deadlock.
* 命令查询线程死锁情况  
> 14.sheep和wait 的区别
* sleep 是Thread的方法 ， 而wait是Object的方法
* sleep 不会释放啊 monitor(Lock) ，但是wait会释放monitor并且增加的一个等待队列（waitSet）中
* 使用sleep的时候不需要定义一个synchronized ， 但是使用wait需要
* 使用sleep的时候不需要被唤醒，但是wait需要(notify)；

> 15.程序出现意外关闭可以在关闭前执行一些操作（通知操作等），使用Runtime相关方法
```  
Runtime.getRuntime().addShutdownHook(new Thread(() ->{ }));
```
> 16.线程中是无法抛出异常的， 因为线程的run方法签名不允许抛出异常

> 17.获取线程中的异常可以使用  
```
t1.setUncaughtExceptionHandler((thread ,e ) ->{
     System.out.println(e);
     System.out.println(thread);
 });
```                         
                         
                         
 -------------------------------------------------------------------------------
#线程池相关
> 1.线程池需要：
* 任务队列（提交任务放到待处理的队列中） 
* 拒绝策略 -> 性能保护（抛出异常、直接丢弃、阻塞、临时队列）  
* init 初始大小
* active 活跃数量
* MAX 线程池最大承载数，不能一味的扩大   min <= active <= max  ,
 
                          
 -------------------------------------------------------------------------------
 #多线程设计模式
 > 1.单例设计模式
 
 > 2.waitSet 相关
* 所有对象都会有一个wait set,用来存放， 调用了该对象的wait方法之后， 进入block线程状态
* 线程被notify之后，不一定立即得到执行
* 线程从wait set中被唤醒的顺序不一定是FIFO ; 
* 线程被唤醒后，必须重新获取锁，获取线程的执行权后，会执行之前wait之后的代码（程序计数器会记住之前的执行代码）

> 3.volatile 相关
~~~
volatile 关键字
1.保证重排序的是不会把后面的指令放到屏障（告诉CPU和编译器先于这个命令的必须先执行，后于这个命令的必须后执行）前面，也不会把前面的放到后面
2.强制对缓存的修改立刻写入到主内存中
3.如果是写操作，他会导致其他CPU中的缓存失效
~~~
* i = i +1 ; 
```
 cpu1 ：    main memory -> i -> cache i+1 -> cache(e) -> main memory ; 
 cpu2 ：    main memory -> i -> cache i+1 -> cache(e) -> main memory ; 
```
* 一旦一个共享变量被volatile修饰，就具备了两层语义
~~~
1.保证了不同线程间的可见性
2.禁止对其进行重排序，保证了有序性
3.并未保证原子性   
~~~
> 解决数据不一致的问题：
* 1.给数据总线加锁   `会产生串型化 ` 

( cpu和其他的设备通讯都是通过总线(数据总线，地址总线，控制总线)进行通讯的)

* 2.使用CPU高速缓存一致性协议
```
1.当CPU写入数据的时候，如果发现该变量被共享（也就是说，在其他CPU中也存在该变量的副本） ， 就会发出一个信号，通知其他CPU该变量的缓存无效;
2.当其他cpu访问该变量的时候，重新到主内存中获取;
```

#并发编程概念
> 原子性   一个操作或者多个操作，要么都成功，要么都失败 ， 中间不能因为任何的元素中断

> 可见性  多个线程修改变量的值，其他线程需要看到被修改的值

> 有序性 执行的代码需要按照循序执行
~~~ 
Java代码编译过程中，可能会出现重排序的问题，重排序只会保证代码的最终一致性，但是中间过程可能不会出现有序性（volatile ：保证屏障前后的一致性）
//volatile boolean init ;
-----Thread -1 -------
init = true ;            2. 
object = create();       1.

------Thread -2 -------
while(!init){
    sleep(xx);
}
userTheObj(object);
~~~


> java中是如何保证原子性 可见性 有序性

*.原子性 对基本数据类型的变量读取或者赋值，是保证了原子性的，要么都成功，要么都失败，这些操作不可被打断；

*.可见性 使用 volatile关键字保证可见性
(volatile 可以保证一个变量被修改后第一时间修改到主内存中。其他线程读取的时候是直接去主内存中拿数据的，而不是cpu的缓存中 )

*.有序性
happend-before 原则（happend-before relationship）
~~~
1.一个线程内，代码的执行顺序，编写在前面的发生在编写在后面的
2.unLock必须发生在lock之后;
3.volatile修饰的变量，对一个变量的写操作先于该变量的读操作
4.传递规则：操作A先于B ，B先于C ，那么A肯定先于C
5.线程的启动规则：start方法肯定先于该线程的其他操作方法
6.线程的中断规则：interrupt（打断）这个动作，必须发生在捕获该动作之前
7.对象销毁规则：初始化必须发生在finalize之前
8.线程终结规则:所有的操作必须发生在线程死亡之前
~~~

