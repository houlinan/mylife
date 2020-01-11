package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.HPProduct;
import cn.houlinan.mylife.entity.HPProductPicList;

import java.io.Serializable;
import java.util.List;

public interface HPProductPicListRepository extends BaseJpaRepository<HPProductPicList, Serializable> {

    List<HPProductPicList> findHPProductPicListsByProductId(String productId);
}
