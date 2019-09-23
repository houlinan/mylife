package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.PhotoAlbum;

import java.io.Serializable;

public interface PhotoAlbumRepository extends BaseJpaRepository<PhotoAlbum, Serializable> {

    PhotoAlbum findPhotoAlbumById(String id);

}
