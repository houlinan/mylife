package cn.houlinan.mylife.utils;

import net.sf.json.JSONObject;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit ;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/25
 * Time : 14:34
 */
public class OkhttpUtil {



    private static final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType
            .parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType
            .parse("image/png;charset=utf-8");
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType
            .parse("text/x-markdown; charset=utf-8");


    /**
     * 不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        client.newCall(request).enqueue(responseCallback);
    }

    /**
     * 根据url地址获取数据
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String doGetHttpRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {

            throw new IOException("Unexpected code " + response);
        }
        return response.body().string();
    }

    /**
     * 根据url地址和json数据获取数据
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String doPostJSON1(String url, String json)
            throws IOException {
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(JSON, json)).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {

            throw new IOException("Unexpected code " + response);
        }
        return response.body().string();
    }

    /**
     * 根据url地址和json数据获取数据
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String doPostJSON(String url, String json)
            throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder().url(url).post(body)
                .addHeader("content-type", "application/json").build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {

            throw new IOException("Unexpected code " + response);
        }
        return response.body().string();
    }

   public static void main(String[] args)throws Exception {

       System.out.println(OkhttpUtil.sendMultipartHttp("file" , "123.png" ,"C:\\Users\\houlinan\\Desktop\\123.png" ));

   }


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
}
