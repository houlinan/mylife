package cn.houlinan.mylife.utils;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.*;

/**
 * @className :SvgImageConver
 * @DESC :
 * @Author :hou.linan
 * @date :2020/8/24 14:36
 */
public class SvgImageConver {


    public static void main(String[] args) throws Exception{
        String code = "<svg xmlns=\"http://www.w3.org/2000/svg\" x=\"0px\" y=\"0px\" viewBox=\"0 0 47 12\" style=\"vertical-align: middle; max-width: 100%;\" width=\"100%\"><g>\n" +
                "\t<path d=\"M44.7,12H13.3c-2.1,0-3.7-1.6-3.7-3.6s1.7-3.6,3.7-3.6h8.3c1.1,0,2-0.9,2-1.9s-0.9-1.9-2-1.9H0V0h21.5c1.6,0,3,1.3,3,2.9   s-1.3,2.9-3,2.9h-8.3c-1.5,0-2.8,1.2-2.8,2.7s1.2,2.7,2.8,2.7h31.4V12z\" fill=\"rgb(41,176,128)\"></path>\n" +
                "</g><g>\n" +
                "\t<path d=\"M47,12H15.6c-2.1,0-3.7-1.6-3.7-3.6s1.7-3.6,3.7-3.6h8.2c1.1,0,2-0.9,2-1.9s-0.9-1.9-2-1.9H2.3V0h21.5c1.6,0,3,1.3,3,2.9   s-1.3,2.9-3,2.9h-8.3c-1.5,0-2.8,1.2-2.8,2.7s1.2,2.7,2.8,2.7H47V12z\" fill=\"rgb(41,176,128)\"></path>\n" +
                "</g></svg>";
        String path  = "C:\\Users\\houlinan\\Desktop\\123.jpg";
        convertToPng(code  , path ) ;
    }

    /**
     * @param svgCode     svg代码
     * @param pngFilePath 保存的路径
     * @throws IOException         io异常
     * @throws TranscoderException svg代码异常
     * @Description: 将svg字符串转换为png
     * @Author:
     */
    public static void convertToPng(String svgCode, String pngFilePath) throws IOException, TranscoderException {

        File file = new File(pngFilePath);

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            convertToPng(svgCode, outputStream);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param svgCode      svg代码
     * @param outputStream 输出流
     * @throws TranscoderException 异常
     * @throws IOException         io异常
     * @Description: 将svgCode转换成png文件，直接输出到流中
     */
    public static void convertToPng(String svgCode, OutputStream outputStream) throws TranscoderException, IOException {
        try {
            byte[] bytes = svgCode.getBytes("UTF-8");
            PNGTranscoder t = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));
            TranscoderOutput output = new TranscoderOutput(outputStream);
            t.transcode(input, output);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}