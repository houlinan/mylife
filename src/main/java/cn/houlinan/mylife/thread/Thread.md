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
