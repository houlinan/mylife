java8 新特性
> 1.lambda表达式相应方法

- Predicate boolean test(T t)  // Predicate -> 传入一个T ,返回boolean类型
- Comsumer accept(T t) // Comsumer -> 传入一个T  ，无返回（void）
- Function<T , R> // Function -> R apply(T) - 传入T ，返回 R 
- Supplier<T> // Supplier -> T get(); -无参数，返回T 

> 2.Stream 相关 
+ .filter(Predicate<T>)  // 接收一个Predicate的boolean表达式，不符合结果的过滤掉
+ .distinct() // 无参数 去重
+ .skip(int n) // 截断集合， 跳过前面的n个 
+ .limit(int n) //截断集合，取前面的n个
+ .map(Function<R, T>)  //处理集合数据，返回新的集合
+ .flatmap flat(扁平化)
- .findAny() //随便拿一个，返回Optional 对象，如果返回值中没有元素，抛出异常，
- .findFirst() // 拿第一个 ， 返回Optional 对象
~~~
String[] str = { "hello" , "world"} ;
//                {h,e,l,l,o} , {w,o,r,l,d}        h,e,l,l,o,w,o,r,l,d
Arrays.stream(str).map(a -> a.split("")).flatMap(Arrays::stream);
~~~
- .allMatch(redicate predicate) //是否全部符合条件，返回boolean类型返回值
- .anyMatch() //任意一个符合条件 
- .noneMatch() //都不符合条件
~~~ 
boolean b = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 3, 6, 7}).allMatch(i -> i > 0);
~~~
-  .reduce(inaryOperator op) //聚合函数，传入biFunction<R,T>
![reduce流程示意图](https://www.houlinan.cn/img/Stream-reduce.png)
~~~
//求和
int reduce = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 3, 6, 7}).reduce(0, (a, j) -> a + j);
int reduce = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 3, 6, 7}).reduce(Integer::sum).getAsInt();

~~~


> 3.Optional对象
### 1.创建Optional的三种方式
~~~
Optional<Insurance> insurance = Optional.<Insurance>empty();
Optional<Insurance> insurance = Optional.of(new Insurance());
Optional<Insurance> insurance = Optional.ofNullable(null);
- insurance.get();
~~~
### 2.Optional相关API
- .orElseGet(Insurance::new); //获取optional的对象值，没有的话返回默认
- .orElse(Object obj)  // Optional对象对象非空，返回正常结果，如果Optional对象为空，返回obj默认的对象；
- .orElseThrow(RuntimeException::new); 
- .orElseThrow(() -> new RuntimeException("no have element"));//有的话就获取，没有的话就抛出异常
- .filter(Predicate predicate) //和stream一样 ， 传入 predicate对象，返回相关过滤后的结果
- .map(e -> e.getName()); //传入Function<T,R> 对象返回t的optional封装对象 
~~~
Optional<String> s = insurance.map(e -> e.getName());
~~~
- .isPresent(); //判断是否存在，返回boolean类型
- .ifPresent(Consumer< T> consumer); // 如果存在就使用consumer方法  // 处理函数相应方法 // 函数推倒
- .flatMap(Consumer< T> consumer) //扁平化map获取，不会返回Optional<Optional<T>> 对象， 可递归获取下级结构
~~~
private static String getInsuranceNameByOptional(Person persion){
    return  Optional.ofNullable(person)
        .flatMap(Person::getCar).flatMap(Car::getInsurance)
        .map(Insurance::getName).orElse("unknown");
}
~~~

> 4.Collector 相关
###聚合
- .collect(Collectors.toList())
###分组
~~~
//将集合中的所有元素 根据某个值进行分组，类似MultiValueMap
private static Map<String ,List<Apple>> groupByAppleColor(List<Apple> appleList){
    return appleList.stream().collect(Collectors.groupingBy(Apple::getColor));
}
~~~

###Collector相关API
- 获取平均值 .averagingNumber(.averagingDouble 、.averagingInt 、.averagingLong)
~~~
SimpleStream.menu.stream().collect(Collectors.averagingDouble(Dish::getCalories));
~~~
- .collectingAndThen // 执行一个方法之后再执行一个function函数
~~~
Optional.ofNullable(SimpleStream.menu.stream()
                .collect(Collectors.collectingAndThen(Collectors.averagingInt(Dish::getCalories) , a -> "平均值为： ->  " +a )))
                .ifPresent(System.out::println);
                
//返回值结果不能修改
 SimpleStream.menu.stream().filter(d -> d.getType().equals(1))
             .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
~~~
- .counting() //获取集合元素个数
~~~
SimpleStream.menu.stream().collect(Collectors.counting()).intValue();
~~~
- groupingBy(Function<T,R>) // 根据function函数分组
~~~
//将集合中的所有元素 根据某个值进行分组，类似MultiValueMap
private static Map<String ,List<Apple>> groupByAppleColor(List<Apple> appleList){
                //  根据苹果颜色分组
    return appleList.stream().collect(Collectors.groupingBy(Apple::getColor));
}

//根据类型分组， 并输出分组后每组数据的元素geshu
Map<Dish.Type,Double> result = 
    SimpleStream.menu.stream().collect(Collectors.groupingBy(Dish::getType , Collectors.counting()));
                     
// 将上面的返回值由map 转换成treeMap ,
TreeMap<Dish.Type, Long> collect = 
     SimpleStream.menu.stream().collect(Collectors.groupingBy(Dish::getType ,TreeMap::new ,Collectors.counting()));                
~~~
- .summarizingDouble 计算简单的数字函数（数量，总数 ，最大，最小，平均）
~~~
SimpleStream.menu.stream().collect(Collectors.summarizingDouble(Dish::getCalories))
-- DoubleSummaryStatistics{count=9, sum=4200.000000, min=120.000000, average=466.666667, max=800.000000}
~~~
- .groupingByConcurrent(Dish::getType) //根据xxx分组， 返回一个ConcurrentHashMap对象  而不是返回之前的hashMap
~~~
//HashMap 不是线程安全的 而 ConcurrentHashMap是线程安全的 
menu.stream().collect(Collectors.groupingByConcurrent(Dish::getType))
~~~
- ..groupingByConcurrent(根据xxxx分组， 每组根据xxx计算平均值);
~~~
//根据类型分组 并且算出每组卡路里的平均值
Collectors.groupingByConcurrent(Dish::getType , Collectors.averagingInt(Dish::getCalories)))
~~~
- .groupingByConcurrent(根据xxxx复制， 返回xxxx数据类型的数据 , 每组根据xxx计算平均值 )
~~~
//SkipListMap  是跳表式结构， redis 主要数据存储类型就是skipMap ， 读写数据会非常的快
Collectors.groupingByConcurrent(Dish::getType, ConcurrentSkipListMap::new, Collectors.averagingInt(Dish::getCalories))
~~~
- .join(分隔符)
~~~
menu.stream().map(Dish::getName).collect(Collectors.joining(","))
// syso -> pork,beaf,chicken,french fries,rice,season fruit,pizza,prawns,salmon
~~~

- .join(分隔符 ,结果前缀， 结果后缀)
~~~
menu.stream().map(Dish::getName).collect(Collectors.joining("," ,"namnes ["  ,"]"))
// syso -> namnes [pork,beaf,chicken,french fries,rice,season fruit,pizza,prawns,salmon]
~~~
- .mapping(function，分隔符) ; //使用join需要先map 获取到单字符之后分割， 使用mapping可以直接对stream使用
~~~
menu.stream().collect(Collectors.mapping(Dish::getName , Collectors.joining(",")))
~~~
- .maxBy()  //获取最大值
~~~
menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)))
~~~
- .minBy()  //获取最小值
~~~
menu.stream().collect(Collectors.minBy(Comparator.comparingInt(Dish::getCalories)))
~~~
- .partitioningBy(boolean);//根据布尔值返回数据，符合条件的装入true集合 ，不符合的添加到false集合
~~~
menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian))
~~~
- .partitioningBy(boolean , Collectors运算) //根据boolean值条件进行原酸，结果进行二次条件运算
~~~
menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian ,Collectors.averagingInt(Dish::getCalories)))
// syso -> {false=530.0, true=387.5}
~~~
- .summingDouble( XXXX )  获取总数
~~~
menu.stream().collect(Collectors.summingDouble(Dish::getCalories))
menu.stream().map(Dish::getCalories).mapToInt(Integer::intValue).sum()
~~~
- .toCollection(LinkedList::new) //将结果转成目标list类型
~~~
LinkedList<Dish> collect = menu.stream().collect(Collectors.toCollection(LinkedList::new));
~~~
- .toConcurrentMap(Dish::getName, Dish::getCalories) //将对象处理返回ConcurrentMap ，设定返回值的key和value
~~~
ConcurrentMap<String, Integer> collect = menu.stream().collect(Collectors.toConcurrentMap(Dish::getName, Dish::getCalories));
~~~

> Collector 原理讲解
1.Collector接口
~~~
// T 原始集合数据  ;A 累家器 ; R 最终返回的值
public interface Collector<T, A, R> {
    Supplier<A> supplier();
    BiConsumer<A, T> accumulator();
    BinaryOperator<A> combiner();
    Function<A, R> finisher();
    Set<Collector.Characteristics> characteristics();
}
~~~