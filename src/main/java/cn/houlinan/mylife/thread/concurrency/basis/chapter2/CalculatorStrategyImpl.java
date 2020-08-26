package cn.houlinan.mylife.thread.concurrency.basis.chapter2;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 15:26
 */
public class CalculatorStrategyImpl implements CalculatorStrategy {
    @Override
    public double calculate(double salary, double bonus) {
        return  salary*0.1 + bonus*0.15;
    }
}
