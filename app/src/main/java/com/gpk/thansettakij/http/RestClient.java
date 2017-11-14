package com.gpk.thansettakij.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.gpk.thansettakij.constant.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Adisorn on 8/31/2017.
 */


public class RestClient {
    private AsyncHttpClient client;
    public RestClient(Context context) {
        client = new AsyncHttpClient();
        client.setTimeout(3*60*1000);
    }

    public void get(String url , RequestParams requestParams , final RestClientListener clientListener){
//        url = Constant.ROOT_URL + url;
        Log.d("DEV" , "url : " + url);
        client.get(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.d("DEV" , "response : " + new String(responseBody));
                clientListener.onSuccess(new String(responseBody));
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getBoolean("status")) {
//                        clientListener.onSuccess(new String(responseBody));
//                    }
//                    else {
//                        clientListener.onFailure(jsonObject.getString("message"));
//                    }
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                clientListener.onFailure(new String(responseBody));
                clientListener.onFailure((responseBody != null) ? new String(responseBody) : "");
                Log.d("DEV" , "response : " + new String(responseBody));
            }
        });
    }

    public void post(String url , RequestParams requestParams , final Class classModelResponse , final RestClientListener clientListener){
        Log.d("DEV" , "url : " + url);
        url = Constant.ROOT_URL + url;

        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.d("DEV" , "response : " + new String(responseBody));
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        clientListener.onSuccess(new String(responseBody));
                    }
                    else {
                        clientListener.onFailure(jsonObject.getString("message"));
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                clientListener.onFailure((responseBody != null) ? new String(responseBody) : "");
            }
        });
    }
    private <T> T convertJsonModel(Class<T> model , String json){
        Gson gson = new Gson();
        return gson.fromJson(json , model);
    }


    public interface RestClientListener{
        public void onSuccess(String json);
        public void onFailure(String message);
    }
}
