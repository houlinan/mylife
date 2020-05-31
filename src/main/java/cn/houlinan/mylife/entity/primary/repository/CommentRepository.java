package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.Comment;
import cn.houlinan.mylife.entity.HPProductPicList;
import cn.houlinan.mylife.entity.User;

import java.io.Serializable;
import java.util.List;

public interface CommentRepository extends BaseJpaRepository<Comment, Serializable> {

    List<Comment> findCommentsByCommentUserAndMessageTypeOrderByCrTimeDesc(User user ,int messageType);

    List<Comment> findCommentByParentIdIn(List<String > ids);

    List<Comment> findCommentsByParentIdAndMessageTypeAndProductIdAndHasProcessed(String parentId , int messageType , String productId , int hasProcessed);

    Comment findCommentById(String id);

}
