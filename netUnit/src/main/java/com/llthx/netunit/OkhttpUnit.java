package com.llthx.netunit;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUnit extends BaseUnit{
    private String TAG = "OkhttpUnit";


    private OkHttpClient client;

    public OkhttpUnit(){
        super();
        init();
    }

    public void init() {
        super.init();

        client = new OkHttpClient.Builder()
                .cache(new Cache(mCacheFile,mMaxSize))
                .build();
    }

    private Request getRequest(String url){
        return new Request.Builder()
                .url(url)
                .build();
    }

    private Request getRequest(String url,String json){
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }


    private String doGet(String url){
        return execute(getRequest(url));
    }

    private void doGet(String url,NetUnitResponseCallback callback){
        enqueue(getRequest(url),new MyCallback(callback));
    }

    private String doPost(String url,String json){
        return execute(getRequest(url, json));
    }

    private void doPost(String url,String json,NetUnitResponseCallback callback){
        enqueue(getRequest(url, json),new MyCallback(callback));
    }

    private String execute(Request request){
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "NA";
    }

    private void enqueue(Request request, Callback callback){
        client.newCall(request).enqueue(callback);
    }

    @Override
    public String GET(URL url) {
        return doGet(url.getPath());
    }

    @Override
    public void GET(URL url, NetUnitResponseCallback callback) {
        doGet(url.getPath(),callback);
    }

    @Override
    public String GET(String url) {
        return doGet(url);
    }

    @Override
    public void GET(String url, NetUnitResponseCallback callback) {
        doGet(url, callback);
    }

    @Override
    public String POST(URL url, Map map) {
        return doPost(url.getPath(),map.toString());
    }

    @Override
    public void POST(URL url, Map map, NetUnitResponseCallback callback) {
        doPost(url.getPath(),map.toString(),callback);
    }

    @Override
    public String POST(String url, Map map) {
        return doPost(url,map.toString());
    }

    @Override
    public void POST(String url, Map map, NetUnitResponseCallback callback) {
        doPost(url,map.toString(),callback);
    }

    @Override
    public String POST(String url, String json) {
        return doPost(url,json);
    }

    @Override
    public void POST(String url, String json, NetUnitResponseCallback callback) {
        doPost(url, json, callback);
    }

    class MyCallback implements Callback{
        private NetUnitResponseCallback mNetUnitCallback;
        MyCallback(NetUnitResponseCallback netUnitCallback){
            this.mNetUnitCallback = netUnitCallback;
            mNetUnitCallback.onStart();
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            Log.d(TAG,"MyCallback,onFailure");

            mNetUnitCallback.onFailure("????????????");
            mNetUnitCallback.onEnd();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            Log.d(TAG,"MyCallback,onResponse");

            String result = response.body().string();
            Log.d(TAG,"MyCallback,onResponse:result : " + result);

            //result???null?????????""??????????????????????????????????????????????????????
            if (result == null || result.toString().trim().equals("")) {
                mNetUnitCallback.onFailure("????????????");
                return;
            }

            try {
                JSONObject resultObj = new JSONObject(result);
                if (resultObj.has("code") && resultObj.has("msg")) {

                    Log.d(TAG,"MyCallback,onResponse: onSuccess");
                    mNetUnitCallback.onSuccess(result);
                }else{
                    //???????????????????????????????????????
                    Log.d(TAG,"MyCallback,onResponse: onFailure");
                    mNetUnitCallback.onFailure("???????????????????????????????????????");
                }
            } catch (Exception e) {
                Log.d(TAG,"MyCallback,onResponse: error");
                mNetUnitCallback.onFailure("????????????");
            }finally {
                mNetUnitCallback.onEnd();
            }
        }
    }
    
}
