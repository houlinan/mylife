package cn.houlinan.mylife.thread.concurrency.chapter2;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 15:23
 */
@FunctionalInterface
public interface CalculatorStrategy {

    double calculate(double salary , double bonus);
}
