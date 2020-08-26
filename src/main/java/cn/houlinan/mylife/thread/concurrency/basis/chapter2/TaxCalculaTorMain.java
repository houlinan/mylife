package cn.houlinan.mylife.thread.concurrency.basis.chapter2;

/**
 * DESC：地方税率实现
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/6/18
 * Time : 15:17
 */
public class TaxCalculaTorMain {

    public static void main(String[] args) {
       /* TaxCalaculator t1 = new TaxCalaculator(10000d , 2000d){
            @Override
            public double calcTax(){
                return getSalary()*0.1 + getBonus()*0.15;
            }
        } ;
        double calculate = t1.calculate();
        System.out.println("计算后的工资是 :" + calculate);*/

        TaxCalaculator t1 = new TaxCalaculator(10000d , 2000d , (s , b) ->s*0.1 + b*0.15 );
//        CalculatorStrategy calculatorStrategy = new CalculatorStrategyImpl();
//        t1.setCalculatorStrategy(calculatorStrategy);
//        t1.setCalculatorStrategy( (s , b) ->s*0.1 + b*0.15);
        System.out.println(t1.calculate());

    }


}
