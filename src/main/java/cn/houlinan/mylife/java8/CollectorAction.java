package cn.houlinan.mylife.java8;


import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static cn.houlinan.mylife.java8.SimpleStream.menu;
import static java.util.stream.Collectors.toList;

/**
 * @className :CollectorAction
 * @DESC :
 * @Author :hou.linan
 * @date :2020/7/31 16:37
 */
public class    CollectorAction {

    public static void main(String[] args) {
//        testSummarizing();
        System.out.println("*******************************");
        testSumming();
    }
    private static void testToConcurrentMap(){
        ConcurrentMap<String, Integer> collect = menu.stream().collect(Collectors.toConcurrentMap(Dish::getName, Dish::getCalories));
    }

    private static void testToCollector(){
        LinkedList<Dish> collect = menu.stream().collect(Collectors.toCollection(LinkedList::new));
    }

    private static void testSumming(){
        System.out.println(
                menu.stream().collect(Collectors.summingDouble(Dish::getCalories))
        );
        System.out.println(
                menu.stream().map(Dish::getCalories).mapToInt(Integer::intValue).sum()
        );

    }

    private static void trestPartitioningBy2(){
        System.out.println(
                menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian ,Collectors.averagingInt(Dish::getCalories)))
        );
    }

    private static void trestPartitioningBy(){
        System.out.println(
            menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian))
        );
    }


    public static void testAveragingDouble(){
        menu.stream().collect(Collectors.averagingDouble(Dish::getCalories));
    }

    public static void testCollectingAndThen(){
        Optional.ofNullable(menu.stream()
                .collect(Collectors.collectingAndThen(Collectors.averagingInt(Dish::getCalories) , a -> "平均值为： ->  " +a )))
                .ifPresent(System.out::println);

        //返回值结果不能修改
        menu.stream().filter(d -> d.getType().equals(1))
                .collect(Collectors.collectingAndThen(toList(), Collections::unmodifiableList));
    }

    public static int testCounting(){
        return  menu.stream().collect(Collectors.counting()).intValue();
    }

    private static void testGroupIngByFunctionAndCollector(){
        TreeMap<Dish.Type, Long> collect = menu.stream().collect(Collectors.groupingBy(Dish::getType, TreeMap::new, Collectors.counting()));
    }
    public static void testSummarizing(){
        System.out.println(menu.stream().collect(Collectors.summarizingDouble(Dish::getCalories)));
    }

    private static void testGroupingByConcurrentWithFunction(){
        System.out.println("testGroupingByConcurrentWithFunction");
        System.out.println(menu.stream().collect(Collectors.groupingByConcurrent(Dish::getType)));
    }

    private static void testGroupingByConcurrentWithFunctionAndCollector(){
        System.out.println("testGroupingByConcurrentWithFunctionAndCollector");
        System.out.println(menu.stream().collect(Collectors.groupingByConcurrent(Dish::getType , Collectors.averagingInt(Dish::getCalories))));
    }
    private static void testGroupingByConcurrentWithFunctionAndSupplierAndCollector(){
        System.out.println("testGroupingByConcurrentWithFunctionAndSupplierAndCollector");
        ConcurrentSkipListMap<Dish.Type, Double> collect = menu.stream()
                .collect(Collectors.groupingByConcurrent(Dish::getType, ConcurrentSkipListMap::new, Collectors.averagingInt(Dish::getCalories)));
        System.out.println(collect);

    }

    private static void testJoin(){
        System.out.println(menu.stream().map(Dish::getName).collect(Collectors.joining(",")));
    }
    private static void testJoin2(){
        System.out.println(menu.stream().map(Dish::getName).collect(Collectors.joining("," ,"namnes ["  ,"]")));
    }

    private static  void testMapping(){
        System.out.println(menu.stream().collect(Collectors.mapping(Dish::getName , Collectors.joining(","))));
    }

    private static void testMaxBy(){
        System.out.println(menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
    }

}
