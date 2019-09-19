package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.User;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface UserRepository extends BaseJpaRepository<User, Serializable> {

    User findUserByUserName(String userName);

}
