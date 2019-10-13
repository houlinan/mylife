package cn.houlinan.mylife.utils;

import cn.houlinan.mylife.entity.User;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * DESC：上传图片工具类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/30
 * Time : 10:13
 */
@Slf4j
@Component
public class UploadUtils {


    public static String uploadPath ;


    @Value("${upload.path}")
    public void setUploadPath(String path){
        uploadPath = path ;
    }

    /**
     * DESC: 上传图片工具类 for wx客户端
     *
     * @author hou.linan
     * @date: 2018/9/6 17:08
     * @param: [files, uploadPathDB, waterMsg]
     * @return: java.lang.String

    public static String uploadPic(MultipartFile files, String uploadPathDB, String waterMsg) {

        //保存文件的命名空间
        String fileSpace =uploadPath;


//        log.info("文件大小为：{}" + files.length);

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
//        log.info("文件大小 = {} ， 文件的长度为 {}" ,files ,files.length );
        try {
            if (files != null) {

                String fileName = files.getOriginalFilename();
                if (!StringUtils.isEmpty(fileName)) {

                    log.info("用户传入的文件名称为{}", fileName);

                    //TODO : 这里更改了长度 ，应该没问题， 如果有问题再改回来吧
                    fileName = fileName.substring(40, fileName.length());
                    //设置文件最终路径
                    String finalFilePath = fileSpace + uploadPathDB + "\\" + fileName;
                    log.info("finalFilePath ==" + finalFilePath);
                    //数据库文件路径
                    uploadPathDB += ("\\" + fileName);
                    log.info("uploadPathDB ==" + uploadPathDB);
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
                    zipWidthHeightImageFile(finalFilePath, finalFilePath, 10f);
                    //添加水印
                    addWaterMark(finalFilePath, finalFilePath, waterMsg);

                    log.info("文件大小为：{}" + inputStream.available());
                    return uploadPathDB;
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
     */

/*    *//**
     * DESC:    压缩文件
     *
     * @author hou.linan
     * @date: 2018/9/6 9:29
     * @param: [oldFilePath 原文件路径
     * , newFilePath 压缩后文件路径 ,
     * quality 压缩质量]
     * @return: java.lang.String
     *//*
    public static void zipWidthHeightImageFile(String oldFilePath, String newFilePath, float quality) {
        File oldFile = new File(oldFilePath);
        if (oldFile == null) {
            return;
        }
        //处理压缩文件的比例
        double fileLength = oldFile.length() / 1024 / 1024;
        double compressionRatio = 0.8;

        if (fileLength > 5)
            compressionRatio = 0.11;
        else if (fileLength > 4)
            compressionRatio = 0.2;
        else if (fileLength > 2)
            compressionRatio = 0.25;
        else if (fileLength > 1.2)
            compressionRatio = 0.6;


        try {
            *//** 对服务器上的临时文件进行处理 *//*
            Image srcFile = ImageIO.read(oldFile);

            //处理文件压缩比例
            int imageWidth = (int) (((BufferedImage) srcFile).getWidth() * compressionRatio);
            int imageHeight = (int) (((BufferedImage) srcFile).getHeight() * compressionRatio);

            *//** 宽,高设定 *//*
            BufferedImage tag = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, imageWidth, imageHeight, null);

            *//** 压缩之后临时存放位置 *//*
            FileOutputStream out = new FileOutputStream(new File(newFilePath));

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            *//** 压缩质量 *//*
            jep.setQuality(quality, true);
            encoder.encode(tag, jep);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * @param srcImgPath       源图片路径
     * @param tarImgPath       保存的图片路径
     * @param waterMarkContent 水印内容
     */
    public static void addWaterMark(String srcImgPath, String tarImgPath,
                                    String waterMarkContent) {

        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高

            int fontSize = srcImgWidth / 50;
            if (srcImgWidth > 1000) fontSize = srcImgWidth / 40;
            if (srcImgWidth < 500) fontSize = srcImgWidth / 30;


            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);

            g.setColor(new Color(255, 255, 255, 128)); //根据图片的背景设置水印颜色
            g.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));              //设置字体

            //设置水印的坐标
//            int x = srcImgWidth - 2*getWatermarkLength(waterMarkContent, g);
//            int y = srcImgHeight - 2*getWatermarkLength(waterMarkContent, g);
            //分别在三个位置设置水印 1
            int x = (srcImgWidth / 2) - (getWatermarkLength(waterMarkContent, g) / 2);
            int y = srcImgHeight / 2;
            g.drawString(waterMarkContent, x, y);  //画出水印
            g.drawString(waterMarkContent, x, 60);
            g.drawString(waterMarkContent, x, srcImgHeight - 50);
            //
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            // 异常不处理！
        }
    }


    /**
     * DESC: 删除一个文件
     *
     * @author hou.linan
     * @date: 2018/9/6 15:21
     * @param: [uploadPathDB]
     * @return: void
     */
    public static void deleteObjectFile(String uploadPathDB) {
        String filePath = uploadPath + uploadPathDB;
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {
            log.error("删除文件【{}】失败", filePath);
        }
    }


    /**
     * DESC:删除文件所属的文件夹
     *
     * @author hou.linan
     * @date: 2018/9/6 15:07
     * @param: [uploadPathDB]
     * @return: void
     */
    public static void deleteProductFileDirectory(String uploadPathDB) {
        String filePath = uploadPath + uploadPathDB;
        try {
            File file = new File(filePath);
            if (file.exists() && file.getParentFile().getParentFile().isDirectory()) {
                FileUtils.deleteDirectory(file.getParentFile().getParentFile());
            }
        } catch (Exception e) {
            log.error("删除文件【{}】失败", filePath);
        }

    }


    /**
     * DESC:删除用户的所属文件夹的所有文件
     *
     * @author hou.linan
     * @date: 2018/9/6 17:07
     * @param: [user]
     * @return: void
     */
    public static void deleteUser(User user) {
        String userFilePath = uploadPath + File.separator + user.getId();

        try {
            File file = new File(userFilePath);
            if (file.exists() && file.isDirectory()) FileUtils.deleteDirectory(file);
        } catch (Exception e) {
        }

    }

    /**
     * DESC: 获取水印内容的长度
     *
     * @author hou.linan
     * @date: 2018/9/6 15:20
     * @param: [waterMarkContent, g]
     * @return: int
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }






    public static void main(String[] args) {
        //水印字体
//        String srcImgPath = "C:\\Users\\hou.linan\\Desktop\\422.jpg"; //源图片地址
//        String tarImgPath = "C:\\Users\\hou.linan\\Desktop\\d422.jpg"; //待存储的地址
//        String waterMarkContent = "用户@后立男，店铺：嘉丽美容美发用品";  //水印内容                //水印图片色彩以及透明度
//        addWaterMark(srcImgPath, tarImgPath, waterMarkContent);
       // deleteObjectFile("");
        //    zipWidthHeightImageFile
        //                ("G:\\JAVA\\wxFilesForHGXSP\\180904CXH5WHYW94\\1809056F7KM61968\\18090587NG8H2K40\\ProductPic\\st39cu8.Hv7OTtyv6uvS71c063ec3a3c26895c84816da00a9ce8.JPG",
        //                        "C:\\Users\\hou.linan\\Desktop\\1111111.jpg", 10f);

    }
}
