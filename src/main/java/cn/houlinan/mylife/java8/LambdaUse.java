package cn.houlinan.mylife.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

/**
 * @className :LambdaUse
 * @DESC : Lambda 用法
 * @Author :hou.linan
 * @date :2020/7/23 14:58
 */
public class LambdaUse {

    /**
     * predicate [  predɪkət  ]<T>
     */
    private static List<Apple> filter(List<Apple> appleList, Predicate<Apple> proces) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : appleList) {
            if (proces.test(apple)) result.add(apple);
        }
        return result;
    }

    /**
     * predicate<T>
     */
    private static List<Apple> filterWeight(List<Apple> appleList, IntPredicate proces) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : appleList) {
            if (proces.test(apple.getWeight())) result.add(apple);
        }
        return result;
    }

    /**
     * predicate<T>
     */
    private static List<Apple> filterBi(List<Apple> appleList, BiPredicate<Integer, String> proces) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : appleList) {
            if (proces.test(apple.getWeight(), apple.getColor())) result.add(apple);
        }
        return result;
    }


    /***
     * Consumer
     * */
    private static void simpleConsumer(List<Apple> appleList, Consumer consumer) {
        for (Apple apple : appleList) {
            consumer.accept(apple);
        }
    }

    private static void simpleBiConsumer(String a, List<Apple> appleList, BiConsumer<Apple, String> consumer) {
        for (Apple apple : appleList) {
            consumer.accept(apple, a);
        }
    }


    /**
     * Function
     */
    public static String testFunction(Apple apple, Function<Apple, String> function) {
        return function.apply(apple);
    }

    public static Apple testFunction(String color, int weight, BiFunction<String, Integer, Apple> function) {
        return function.apply(color, weight);
    }


    public static void main(String[] args) {
        /***
         * 线程相关
         * */
        /*
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };
        Runnable r2 = () -> System.out.println("hello");
        process(r1);
        process(r2);
        process(() -> System.out.println("hello"));
        */
        List<Apple> apples = Arrays.asList(new Apple("green", 150), new Apple("green", 160),
                new Apple("red", 150), new Apple("red", 150));


        /***
         *  Predicate
         */
        List<Apple> green = filter(apples, (apple -> apple.getColor().equals("green")));
        System.out.println(green);

        List<Apple> weight = filterWeight(apples, w -> w > 100);
        System.out.println(weight);

        List<Apple> filterBi = filterBi(apples, (w, c) -> w > 120 && c.equals("green"));
        System.out.println(filterBi);

        /***
         * Consumer
         * */

        simpleConsumer(apples, a -> System.out.println(a));
        simpleBiConsumer("xxx", apples, (apple, s) -> System.err.println(s + apple.getColor()));

        /**
         * Function
         * */
        String functionResult1 = testFunction(new Apple("green", 100), Apple::toString);
        System.err.println(functionResult1);

        IntFunction<Double> fun = value -> value * 10.0d;
        System.out.println(fun.apply(1000));

        Apple apple = testFunction("red", 100, (c, w) -> new Apple(c, w));
        System.out.println(apple);

        Apple green1 = createApple(() -> new Apple("green", 160));
        System.out.println(green1);
    }

    private static Apple createApple(Supplier<Apple> supplier) {
        return supplier.get();
    }

    private static void process(Runnable runnable) {
        runnable.run();
    }


}
