package cn.houlinan.mylife.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * DESC：FFmpeg 测试工具类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/6
 * Time : 9:39
 */
public class VideoUtils {

    //执行文件位置
    private String ffmpegExe ;

    public VideoUtils(String ffmpegExe ){
        super();
        this.ffmpegExe = ffmpegExe ;
    }

    /*
    *DESC:通过ffmpeg处理视频基础类
    *@author hou.linan
    *@date:  2018/8/6 9:43
    *@param:
    *@return:
    * ** ffmpeg -i input.mp4 output.avi
    */
    public void baseConvertor(List<String> command ) throws Exception{


            ProcessBuilder builder =  new ProcessBuilder(command);
            Process process = builder.start();
            //获取流 ，这个ErrorStream  就是inputStream流
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader( errorStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader) ;

            String line = "";
            while((line = bufferedReader.readLine()) != null );

            //关闭br
            if(bufferedReader != null ) bufferedReader.close();
            if(inputStreamReader!= null ) inputStreamReader.close();
            if(errorStream!=null ) errorStream.close();


    }


    /*
    *DESC: 将视频和MP3结合成一个视频
    *@author hou.linan
    *@date:  2018/8/6 10:32
    *@param:  [videoInputPath 视频源地址, mp3InputPath 视频添加的MP3地址, seconds  视频时间, videoOutPutPath 输出路径]
    *@return:  void
    */
    public void mergeVideoMp3 (String videoInputPath , String  mp3InputPath , double seconds
            , String  videoOutPutPath )throws  Exception{

        List<String > command = new  ArrayList<String>();
        command.add(ffmpegExe) ;

        command.add("-i");
        command.add(videoInputPath);

        command.add("-i");
        command.add(mp3InputPath);

        command.add("-t");
        command.add(String.valueOf(seconds));

        command.add("-y");
        command.add(videoOutPutPath);

        baseConvertor(command) ;

    }

    /*
    *DESC: 生成视频截图
    *@author hou.linan
    *@date:  2018/8/6 15:52
    *@param:  [videoInputPath, videoOutPutPath]
    *@return:  void
    */
    public void getVideoCover(String videoInputPath , String  videoOutPutPath)throws Exception{
        List<String > command = new  ArrayList<>();
        command.add(ffmpegExe) ;

        command.add("-ss") ;
        command.add("00:00:01");
        command.add("-y");
        command.add("-i");
        command.add(videoInputPath) ;
        command.add("-vframes");
        command.add("1") ;
        command.add(videoOutPutPath);

        baseConvertor(command) ;
    }


    public static void main(String[] args) {

        VideoUtils ff = new VideoUtils("G:\\JAVA\\ffmpeg\\bin\\ffmpeg.exe");
        try {
//            ff.convertor("G:\\JAVA\\ffmpeg\\wx123.mp4" , "G:\\JAVA\\ffmpeg\\wx1234.avi");
            ff.mergeVideoMp3("G:\\JAVA\\ffmpeg\\wx123.mp4" , "G:\\JAVA\\ffmpeg\\pgone.mp3"
                    ,7,"G:\\JAVA\\ffmpeg\\wx12311111111.mp4");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
