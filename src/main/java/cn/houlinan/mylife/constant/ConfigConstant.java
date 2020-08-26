package cn.houlinan.mylife.constant;

import cn.houlinan.mylife.entity.Config;
import cn.houlinan.mylife.entity.primary.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @className :ConfigConstant
 * @DESC :
 * @Author :hou.linan
 * @date :2020/8/26 22:27
 */
@Component
public class ConfigConstant {

    @Autowired
    ConfigRepository configRepository ;

    static final String ALLOW_ADD_LINUX_COMMAND_USERNAMES = "allow_add_linux_command_usernames" ;

    public List<String> getAllowAddLinuxCommandUserNameList(){
        Config configByCKey = configRepository.findConfigByCKey(ALLOW_ADD_LINUX_COMMAND_USERNAMES);
        if(configByCKey == null) return new ArrayList<>();
        return Arrays.asList(configByCKey.getCValue().split(","));
    }

}
