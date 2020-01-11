package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.HPProductPicList;
import cn.houlinan.mylife.entity.HPShopKind;

import java.io.Serializable;
import java.util.List;

/**
 * DESC：种类jpa
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/12/31
 * Time : 13:15
 */
public interface HPShopKindRepository  extends BaseJpaRepository<HPShopKind, Serializable> {
    HPShopKind findHPShopKindById(String id);

    List<HPShopKind> findHPShopKindsByParentKindId(String parentKindId);

    List<HPShopKind> findHPShopKindsByShopId(String shopId);
}
