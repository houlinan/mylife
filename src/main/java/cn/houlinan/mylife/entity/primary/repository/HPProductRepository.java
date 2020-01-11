package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.HPProduct;
import cn.houlinan.mylife.entity.Note;

import java.io.Serializable;
import java.util.List;

public interface HPProductRepository extends BaseJpaRepository<HPProduct, Serializable> {

    HPProduct findHPProductById(String id );

    List<HPProduct> findHPProductsByShopId(String shopId);
}
