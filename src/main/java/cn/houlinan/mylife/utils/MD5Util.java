/*
 *	History				Who				What
 *  2016-1-12			shao.rong			Created.
 */
package cn.houlinan.mylife.utils;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class MD5Util {

    private final static Logger logger = LoggerFactory.getLogger(MD5Util.class);


    public static final String md5ForPhp(String txt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(txt.getBytes("GBK"));    //问题主要出在这里，Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
            StringBuffer buf = new StringBuffer();
            for (byte b : md.digest()) {
                buf.append(String.format("%02x", b & 0xff));
            }
            return buf.toString();
        } catch (Exception e) {
            logger.warn("MD5forPHP加密异常。", e);
            return null;
        }
    }

    public static final String encodeMD5Hex(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes("UTF-8"));
            return Hex.encodeHexString(md.digest());
        } catch (Exception e) {
            logger.warn("MD5加密异常。", e);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(encodeMD5Hex("mty1553136776267Kw9d$wx[NNL^$9qdK"));
    }

}
