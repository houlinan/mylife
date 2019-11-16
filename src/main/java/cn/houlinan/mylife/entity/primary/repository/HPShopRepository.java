package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.HPShop;
import cn.houlinan.mylife.entity.RegistrationCode;

import java.io.Serializable;
import java.util.List;

public interface HPShopRepository extends BaseJpaRepository<HPShop, Serializable> {

     HPShop findHPShopById(String id);

}
