package cn.houlinan.mylife.java8;

import java.util.function.Consumer;

/**
 * @className :MethodReference
 * @DESC :方法推倒
 * @Author :hou.linan
 * @date :2020/7/23 16:55
 */
public class MethodReference {


    public static void main(String[] args) {

        /**
         * Consumer 使用
         * */
        Consumer<String> consumer = (s) -> System.out.println(s) ;
        useConsumer(consumer , "abc");
        useConsumer(s -> System.out.println(s)  , "abc") ;
        useConsumer(System.out::println , "abc");


        

    }

    private static <T> void useConsumer(Consumer<T> consumer  ,T t){
        consumer.accept(t);
        consumer.accept(t);
    }

}
