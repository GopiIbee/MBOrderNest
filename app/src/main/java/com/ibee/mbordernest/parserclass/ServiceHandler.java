
package com.ibee.mbordernest.parserclass;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class ServiceHandler {

//    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    //photo upload
   // private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpeg");

//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

//    String s = null;
//    public String url = null;

    OkHttpClient client = new OkHttpClient();

    public ServiceHandler() {

    }

    public String callToServer(String url, int method, JSONObject jsonobject,String token) throws IOException {
        Response response = null;
        if (method == POST) {

            FormEncodingBuilder formbody = new FormEncodingBuilder();
            JSONArray array = jsonobject.names();

            for (int i = 0; i < array.length(); i++) {
                try {
                    formbody.add(array.getString(i), jsonobject.getString(array.getString(i)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Request request = new Request.Builder()
                    .url(url)

                    .post(formbody.build())
                    .build();

            response = client.newCall(request).execute();
        } else if (method == GET) {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();
        }
        return response.body().string();

    }
}
