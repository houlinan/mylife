package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.Config;

import java.io.Serializable;

public interface ConfigRepository extends BaseJpaRepository<Config, Serializable> {

    Config findConfigByCKey(String cKey);
}
