package cn.houlinan.mylife.java8;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @className :SimpleStream
 * @DESC : stream 实例
 * @Author :hou.linan
 * @date :2020/7/27 17:07
 */
public class SimpleStream {

    public final static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beaf", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    public static void main(String[] args) {
          Optional.of(StrUtil.format(getDishNamesByStream(menu).toString()))
                              .ifPresent(System.out::println);

          String[] str = { "hello" , "world"} ;
          //                    {h,e,l,l,o} , {w,o,r,l,d}        h,e,l,l,o,w,o,r,l,d
          Arrays.stream(str).map(a -> a.split("")).flatMap(Arrays::stream);

        boolean b = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 3, 6, 7}).allMatch(i -> i > 0);
        int reduce = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 3, 6, 7}).reduce(Integer::sum).getAsInt();

    }

    private static List<String> getDishNamesByStream (List<Dish> menu){
        List<String> collect = menu.stream() // 获取stream
                .filter(d -> d.getCalories() > 300)  // 过滤 > 300
                .sorted(comparing(Dish::getCalories)) // 排序
                .map(Dish::getName) // 获取名称
                .collect(Collectors.toList()); // 返回list
        return collect ;
    }



}

class Dish {

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        super();
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        MEAT, FISH, OTHER;
    }
}
