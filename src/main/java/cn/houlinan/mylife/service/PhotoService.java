package cn.houlinan.mylife.service;

import cn.houlinan.mylife.DTO.PhotoVOResult;
import cn.houlinan.mylife.constant.PhotoConstant;
import cn.houlinan.mylife.entity.*;
import cn.houlinan.mylife.entity.primary.repository.PhotoRepository;
import cn.houlinan.mylife.service.common.MyPage;
import cn.houlinan.mylife.service.common.PrimaryBaseService;
import cn.houlinan.mylife.utils.DateUtil;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import com.google.common.collect.Maps;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/9/22
 * Time : 10:36
 */
@Service
@Slf4j
public class PhotoService {

    @Autowired
    Sid sid;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PrimaryBaseService primaryBaseService;


    @Value("${upload.img.https.root.address}")
    private String rootAddress ;

    /**
     * DESC: 上传图片工具类 for wx客户端
     *
     * @author hou.linan
     * @date: 2018/9/6 17:08
     * @param: [files, uploadPathDB, waterMsg]
     * @return: java.lang.String
     */

    public Photo uploadPic(MultipartFile files, PhotoAlbum photoAlbum, boolean onlySaveZipFile) {
        return uploadPic(files, photoAlbum.getPath(), photoAlbum.getId(),
                photoAlbum.getFromUserId(), photoAlbum.getTeamid(), onlySaveZipFile);
    }



    public Photo uploadPic(MultipartFile files, String path, String photoAlbumId,
                           String fromUserId, Long teamId, boolean onlySaveZipFile
//                           PhotoAlbum photoAlbum
    ) {


        String uploadTime = DateUtil.format(DateUtil.now(), "yyyy/MM/dd");

        String finalPath = path + File.separator + photoAlbumId + File.separator;


        String finalFileName = saveFileToPath(files, finalPath);

        //设置文件最终路径
        String finalFilePath = finalPath + finalFileName;

        String fileAddress = finalPathToAddress(finalFilePath);

        String currPhoto600Path = getNewFileName(finalFilePath, true);
        currPhoto600Path = finalPath + currPhoto600Path;


        String file600Address = finalPathToAddress(currPhoto600Path);

        //处理文件
        File faceFile = new File(finalFilePath);

        if (faceFile.getParentFile() != null || !faceFile.getParentFile().isDirectory()) {
            faceFile.getParentFile().mkdirs();
        }

        //压缩文件
        Long fileSize = zipWidthHeightImageFile(finalFilePath, currPhoto600Path, 10f);
//                    //添加水印
//                    addWaterMark(finalFilePath, finalFilePath, waterMsg);


        Photo photo = Photo.builder()
                .originalName(files.getOriginalFilename())
                .PicMLName(finalFileName)
                .filePath(finalFilePath)
                .fromPhotoAlbumId(photoAlbumId)
                .fromUserId(fromUserId)
                .uploadTime(uploadTime)
                .fileSize(fileSize)
                .file600Path(currPhoto600Path)
                .file600Address(file600Address)
                .fileAddress(fileAddress)
                .build();

        photo.setId(sid.nextShort());
        photo.setCrTime(new Date());
        photo.setTeamid(teamId);
//                    photoRepository.save(photo);
        if (onlySaveZipFile) {
            File file = new File(finalFilePath);
            if (file.exists()) file.delete();
        }
        return photo;

    }
    public String finalPathToAddress(String finalPath){
        return finalPath.replace("C:\\mylifeDatas\\",rootAddress )
        .replace(PhotoConstant.USERPHOTOALBUMNAME + "\\" , "").replaceAll("\\\\" , "/");
    }

    public static void main(String[] args) {
        String str = "https://www.houlinan.cn/mylife/img/userPhotoAlbum/1910137ZW0PN1T7C\\1910137ZW0PN1T7C\\UP20191013194921361949.jpg";
        System.out.println(str.replaceAll("\\\\" , "/"));
    }

    public String saveFileToPath(MultipartFile files, String path) {

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null) {

                String fileName = files.getOriginalFilename();
                String newFileName = getNewFileName(fileName, false);
                if (!StringUtils.isEmpty(fileName)) {

                    //设置文件最终路径
                    String finalFilePath = path + newFileName;
                    log.info("finalFilePath ==" + finalFilePath);


                    //处理文件
                    File faceFile = new File(finalFilePath);

                    if (faceFile.getParentFile() != null || !faceFile.getParentFile().isDirectory()) {
                        faceFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(faceFile);
                    inputStream = files.getInputStream();


                    //重要！重要！重要！
                    //将文件保存到本地
                    IOUtils.copy(inputStream, fileOutputStream);
                    return newFileName;
                }
            } else {
                log.info("用户传入的用户头像文件为空");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("上传文件失败");
            return null;
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException w) {

                }
            }
        }

        return null;
    }


    /**
     * DESC:    压缩文件
     *
     * @author hou.linan
     * @date: 2018/9/6 9:29
     * @param: [oldFilePath 原文件路径
     * , newFilePath 压缩后文件路径 ,
     * quality 压缩质量]
     * @return: java.lang.String
     */
    public static Long zipWidthHeightImageFile(String oldFilePath, String newFilePath, float quality) {
        File oldFile = new File(oldFilePath);
        if (oldFile == null) {
            return 0l;
        }
        //处理压缩文件的比例
        double fileLength = oldFile.length() / 1024 / 1024;
        double compressionRatio = 0.8;

        if (fileLength > 5)
            compressionRatio = 0.02;
        else if (fileLength > 4)
            compressionRatio = 0.04;
        else if (fileLength > 2)
            compressionRatio = 0.07;
        else if (fileLength > 1.2)
            compressionRatio = 0.1;


        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);

            //处理文件压缩比例
            int imageWidth = (int) (((BufferedImage) srcFile).getWidth() * compressionRatio);
            int imageHeight = (int) (((BufferedImage) srcFile).getHeight() * compressionRatio);

            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, imageWidth, imageHeight, null);

            /** 压缩之后临时存放位置 */
            FileOutputStream out = new FileOutputStream(new File(newFilePath));

//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /** 压缩质量 */
            jep.setQuality(quality, true);
//            encoder.encode(tag, jep);
            ImageIO.write(tag, "jpeg", out);

            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return oldFile.length();
    }

    /**
     * DESC:获取一个pm自定义的文件名称
     *
     * @return: java.lang.String
     * @author hou.linan
     * @date:
     */
    public static String getNewFileName(String orgFileName, boolean get600Name) {

        String orgFileNameSuffix = orgFileName.substring(orgFileName.lastIndexOf("."));
//        System.out.println(orgFileName);
        String currentTimeMillis = System.currentTimeMillis() + "";
        currentTimeMillis = currentTimeMillis.substring(7);

        if (get600Name)
            return PhotoConstant.FILE_NAME_PREFIX + DateUtil.format(DateUtil.now(), "yyyyMMddHHmmss") + currentTimeMillis + "_600" + orgFileNameSuffix;


        return PhotoConstant.FILE_NAME_PREFIX + DateUtil.format(DateUtil.now(), "yyyyMMddHHmmss") + currentTimeMillis + orgFileNameSuffix;

    }

    public MyPage<PhotoVOResult> findPhotoByAlbumId(User user , String albumId , int pageSize , int pageNum) throws Exception
    {
        String sql = " FROM PHOTO WHERE FROMUSERID = '" + user.getId() + "' AND FROMPHOTOALBUMID = '" + albumId + "'";


        MyPage<Photo> photoMyPage = primaryBaseService.executeSqlByPage("SELECT * " + sql, "SELECT COUNT(1) " + sql,
                Maps.newHashMap(), pageNum, pageSize, Photo.class);

        List<Photo> content = photoMyPage.getContent();
        List<PhotoVOResult> results = new ArrayList<>( );
        content.stream().forEach(e -> copyBeanToPhotoResult(e , results)) ;
        MyPage<PhotoVOResult> resultsPage = new MyPage<>();
        BeanUtils.copyProperties(photoMyPage  , resultsPage);
        resultsPage.setContent(results);

        return resultsPage;
    }


    public java.util.List<PhotoVOResult> copyBeanToPhotoResult(Photo photo , List<PhotoVOResult> results ){
        PhotoVOResult result = new PhotoVOResult( );
        BeanUtils.copyProperties(photo ,result );
        results.add(result);
        return results ;
    }

    public void copyFileToPath(MultipartFile file , String path){
        if(!ObjectUtils.isEmpty(file)){
            String fileName = saveFileToPath(file , path);
            String finalFilePath = path + File.separator + fileName ;
            String currPhoto600Path = getNewFileName(finalFilePath, true);
            currPhoto600Path = path + currPhoto600Path;
            zipWidthHeightImageFile(finalFilePath, currPhoto600Path, 10f);
        }
    }



    /**
     *DESC: 保存鹤品图片，这个方法仅仅返回压缩后的图片访问地址
     *@param:  files 文件
     *@Param: finalPath 最终路径
     *@Param: onlySaveZipFile 是否仅仅保存压缩后的文件
     *@return:  java.lang.String
     *@author hou.linan
     *@date:  2019/12/22 18:59
    */
    public String uploadPicForHP(MultipartFile files, String finalPath, boolean onlySaveZipFile
     , String shopRootAddress) {


        String uploadTime = DateUtil.format(DateUtil.now(), "yyyy/MM/dd");


        String finalFileName = saveFileToPath(files, finalPath);

        //设置文件最终路径
        String finalFilePath = finalPath + finalFileName;

        String fileAddress = finalPathToAddress(finalFilePath);

        String currPhoto600Path = getNewFileName(finalFilePath, true);
        currPhoto600Path = finalPath + currPhoto600Path;


        String file600Address = finalPathToAddressForH(currPhoto600Path , shopRootAddress);

        //处理文件
        File faceFile = new File(finalFilePath);

        if (faceFile.getParentFile() != null || !faceFile.getParentFile().isDirectory()) {
            faceFile.getParentFile().mkdirs();
        }

        //压缩文件
        Long fileSize = zipWidthHeightImageFile(finalFilePath, currPhoto600Path, 10f);
//                    //添加水印
//                    addWaterMark(finalFilePath, finalFilePath, waterMsg);

        if (onlySaveZipFile) {
            File file = new File(finalFilePath);
            if (file.exists()) file.delete();
        }
        return file600Address ;

    }

    public String finalPathToAddressForH(String finalPath,String shopRootAddresss){
        return finalPath.replace("C:\\mylifeDatas\\",shopRootAddresss )
//                .replace(PhotoConstant.USERPHOTOALBUMNAME + "\\" , "")
                .replaceAll("\\\\" , "/");
    }

}
