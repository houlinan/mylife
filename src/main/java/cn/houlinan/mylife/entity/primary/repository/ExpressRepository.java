package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.Comment;
import cn.houlinan.mylife.entity.Express;
import cn.houlinan.mylife.entity.User;

import java.io.Serializable;
import java.util.List;

public interface ExpressRepository extends BaseJpaRepository<Express, Serializable> {

    Express findExpressById(String id);
    List<Express>  findExpressesByUserIdOrderByCrTimeDesc(String userId);

    Express findExpressByExpressCodeAndExpressNumberAndUserId(String expressCode , String expressNumber ,String userId);
}
