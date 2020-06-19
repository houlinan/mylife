package cn.houlinan.mylife.thread.concurrency.chapter2;

import lombok.Data;

/**
 * DESC：税务计算器
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 15:12
 */
@Data
public class TaxCalaculator {

    private final double salary ;

    private final double bonus ;

    private CalculatorStrategy calculatorStrategy ;

    TaxCalaculator( double salary ,double bonus ,  CalculatorStrategy calculatorStrategy  ){
        this.salary = salary ;
        this.bonus = bonus ;
        this.calculatorStrategy = calculatorStrategy;
    }

    //base
    protected double calcTax(){
        return calculatorStrategy.calculate(salary , bonus);
    }

    public double calculate(){
        return this.calcTax();
    }


}
