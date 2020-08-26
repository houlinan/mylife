package cn.houlinan.mylife.java8;

import cn.hutool.core.util.StrUtil;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @className :AppleFilter
 * @DESC :
 * @Author :hou.linan
 * @date :2020/7/23 14:03
 */
public class FilterApple {

    @FunctionalInterface
    public interface AppleFilter {
        boolean filter(Apple apple);
    }

    public static class FilterGreenAnd160Weight implements AppleFilter {

        @Override
        public boolean filter(Apple apple) {
            return "green".equals(apple.getColor()) && 160 == apple.getWeight();
        }
    }

    public static class FilterRedAnd150Weight implements AppleFilter {

        @Override
        public boolean filter(Apple apple) {
            return "red".equals(apple.getColor()) && 150 == apple.getWeight();
        }
    }

    public static List<Apple> filterApple(List<Apple> appleList, AppleFilter appleFilter) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : appleList) {
            if (appleFilter.filter(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Apple> apples = Arrays.asList(new Apple("green", 150), new Apple("green", 160),
                new Apple("red", 150), new Apple("red", 150));

        System.out.println(filterApple(apples, new FilterGreenAnd160Weight()));

        //lambda 使用 @FunctionalInterface ， 如果接口有且仅有一方法，并且只有一个参数，可以直接使用
        System.err.println(
                filterApple(apples , apple -> "green".equals(apple.getColor()))
        );

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        new Thread(()->System.out.println(Thread.currentThread().getName())).start();

    }


}
