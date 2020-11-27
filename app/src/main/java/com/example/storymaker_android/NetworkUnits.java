package com.example.storymaker_android;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUnits {
    // Класс который осуществляет запросы на серверную часть

    private static final String URL_API = "http://lexamok.beget.tech/api/index_android.php"; //адрес api сайта

    public static String getResponseFromURL(String request_json) {

        OkHttpClient client = new OkHttpClient(); // обьект для

        String json = request_json;

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(URL_API)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "Error in NetworkUnits/getResponseFromURL";
    }

}
