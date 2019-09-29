package cn.houlinan.mylife.service;

import cn.houlinan.mylife.constant.PhotoConstant;
import cn.houlinan.mylife.entity.Photo;
import cn.houlinan.mylife.entity.PhotoAlbum;
import cn.houlinan.mylife.entity.primary.repository.PhotoRepository;
import cn.houlinan.mylife.utils.DateUtil;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

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

    /**
     * DESC: 上传图片工具类 for wx客户端
     *
     * @author hou.linan
     * @date: 2018/9/6 17:08
     * @param: [files, uploadPathDB, waterMsg]
     * @return: java.lang.String
     */
    public Photo uploadPic(MultipartFile files, PhotoAlbum photoAlbum) {

        //保存文件的命名空间
        String fileSpace = photoAlbum.getPath();
        String photoAlbumId = photoAlbum.getId();

//        log.info("文件大小为：{}" + files.length);

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
//        log.info("文件大小 = {} ， 文件的长度为 {}" ,files ,files.length );
        try {
            if (files != null) {

                String fileName = files.getOriginalFilename();
                String newFileName = getNewFileName(fileName, false);
                if (!StringUtils.isEmpty(fileName)) {

                    log.info("用户传入的文件名称为{}", fileName);
                    String uploadTime = DateUtil.format(DateUtil.now(), "yyyy/MM/dd");

                    //设置文件最终路径
                    String finalFilePath = fileSpace + File.separator + photoAlbumId + File.separator + newFileName;
                    log.info("finalFilePath ==" + finalFilePath);

                    String currPhoto600Path = getNewFileName(fileName, true);
                    currPhoto600Path = fileSpace + File.separator + photoAlbumId + File.separator +currPhoto600Path;

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
                    //压缩文件
                    Long fileSize = zipWidthHeightImageFile(finalFilePath, currPhoto600Path, 10f);
//                    //添加水印
//                    addWaterMark(finalFilePath, finalFilePath, waterMsg);

                    log.info("文件大小为：{}" + inputStream.available());

                    Photo photo = Photo.builder()
                            .originalName(fileName)
                            .PicMLName(newFileName)
                            .filePath(finalFilePath)
                            .fromPhotoAlbumId(photoAlbumId)
                            .fromUserId(photoAlbum.getFromUserId())
                            .uploadTime(uploadTime)
                            .fileSize(fileSize)
                            .file600Path(currPhoto600Path)
                            .build();

                    photo.setId(sid.nextShort());
                    photo.setCrTime(new Date());
                    photo.setTeamid(photoAlbum.getTeamid());
                    photoRepository.save(photo);

                    return photo;
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
            compressionRatio = 0.08;
        else if (fileLength > 4)
            compressionRatio = 0.11;
        else if (fileLength > 2)
            compressionRatio = 0.15;
        else if (fileLength > 1.2)
            compressionRatio = 0.40;


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


}
