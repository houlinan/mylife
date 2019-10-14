package cn.houlinan.mylife.service;

import cn.houlinan.mylife.DTO.PhotoAlbumVO;
import cn.houlinan.mylife.constant.PhotoConstant;
import cn.houlinan.mylife.entity.PhotoAlbum;
import cn.houlinan.mylife.entity.Team;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.PhotoAlbumRepository;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/22
 * Time : 10:16
 */
@Service
@Slf4j
public class PhotoAlbumService {

    @Autowired
    Sid sid ;

    @Autowired
    PhotoAlbumRepository photoAlbumRepository ;

    public PhotoAlbum createPhotoAlbum(PhotoAlbum photoAlbum , User user){

        if(photoAlbum.getId() == null ){
            String sidStr = sid.nextShort() ;
            photoAlbum.setCrTime(new Date());
            photoAlbum.setId(sidStr);
            photoAlbum.setTeamid(user.getTeamid());
            photoAlbum.setPath(PhotoConstant.PHOTOALBUM_ROOT_PATH + sidStr);
            photoAlbum.setFromUserId(user.getId());
            if(!CMyString.isEmpty(photoAlbum.getPassword())) photoAlbum.setIsHasPwd(1);
        }

        photoAlbumRepository.save(photoAlbum);

        return photoAlbum ;
    }

    public List<PhotoAlbum> findAppAlubmByUser(String userId){
        List<PhotoAlbum> findResult = photoAlbumRepository.findPhotoAlbumsByFromUserId(userId);

        findResult.forEach( e -> {
            e.setPassword("");
            e.setFromUserId("");
            e.setPath("");
//            e.setAlbumLabel(Arrays.asList(e.getAlbumLabel().split(",")).toString());
        });
        return findResult ;
    }


}
