package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.GeLuoMiUser;

import java.io.Serializable;

public interface GeLuoMiUserRepository extends BaseJpaRepository<GeLuoMiUser, Serializable> {

    GeLuoMiUser findGeLuoMiUserById(String id);


}
