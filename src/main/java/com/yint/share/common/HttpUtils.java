package com.yint.share.common;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class HttpUtils {


    public static String sendMapPost(String url, Map<String,String> paramMap){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder fb = new FormBody.Builder();
        for (String s : paramMap.keySet()) {
            fb.add(s,paramMap.get(s));
        }
        RequestBody body = fb.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendJsonPost(String url, String params){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, params);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
