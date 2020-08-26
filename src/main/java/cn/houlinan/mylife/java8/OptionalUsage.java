package cn.houlinan.mylife.java8;

import lombok.Data;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

/**
 * @className :OptionalUsage
 * @DESC :Optional 相关操作
 * @Author :hou.linan
 * @date :2020/7/31 14:45
 */
public class OptionalUsage {


    public static void main(String[] args) {

        Optional<Insurance> empty = Optional.<Insurance>empty();
        Optional<Insurance> insurance = Optional.of(new Insurance());
        Optional<Insurance> o = Optional.ofNullable(null);
        Insurance insurance1 = insurance.orElseGet(Insurance::new);
        Insurance insurance2 = insurance.orElseThrow(RuntimeException::new);
        insurance.orElseThrow(() -> new RuntimeException("no have element"));
        Optional<String> s = insurance.map(e -> e.getName());
//        insurance.ifPresent(Consumer< T> consumer);
    }

}


@Data
class Insurance{

    private String name ;
}
