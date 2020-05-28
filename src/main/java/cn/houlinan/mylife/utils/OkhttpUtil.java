package cn.houlinan.mylife.utils;

import net.sf.json.JSONObject;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/25
 * Time : 14:34
 */
public class OkhttpUtil {

   public static JSONObject sendMultipartHttp(String fileKey , String fileName ,   String filePath)throws Exception{

           OkHttpClient client = new OkHttpClient();
           RequestBody requestBody = new MultipartBody.Builder()
                   .setType(MultipartBody.FORM)
                   .addFormDataPart("language" , "chs")
                   .addFormDataPart("apikey" , "5cbc1fd77788957")
                   .addFormDataPart("isOverlayRequired" , "true")
                   .addFormDataPart("url" , "")
                   .addFormDataPart(fileKey, fileName,
                           RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                   .build();

           Request request = new Request.Builder()
//                   .header("Authorization", "Client-ID " + UUID.randomUUID())
                   .url("https://api.ocr.space/parse/image")
                   .post(requestBody)
                   .build();

           Response response = client.newCall(request).execute();
           if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

           String resultHttp = response.body().string();
            return JSONObject.fromObject(resultHttp);

   }


   public static void main(String[] args)throws Exception {

       System.out.println(OkhttpUtil.sendMultipartHttp("file" , "123.png" ,"C:\\Users\\houlinan\\Desktop\\123.png" ));

   }
}
