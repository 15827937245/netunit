package com.llthx.netunit;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

public abstract class BaseUnit implements NetUnitInterface{
    public static final long DEFAULT_MAX_CACHE_SIZE = 50*1024*1024;
    public static final String DEFAULT_CACHE_PATH = Environment.getExternalStorageDirectory()+"/OkHttp/cache";
    public static String[] NEED_PERMISSION = {"android.permission.INTERNET","android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};

    protected long mMaxSize;
    protected String mCachePath;

    protected int connectTimeout = 10;
    protected File mCacheFile;
    protected Gson mGson;
    private String TAG = "BaseUnit";

    @Override
    public void setCacheMaxSize(int cacheMaxSize) {
        this.mMaxSize = cacheMaxSize;
    }

    @Override
    public void setCachePath(String path) {
        this.mCachePath = path;
    }

    public void init(){
        if (null == mCachePath || mCachePath.isEmpty()) {
            mCachePath = DEFAULT_CACHE_PATH;
        }

        if (0 >= mMaxSize) {
            mMaxSize = DEFAULT_MAX_CACHE_SIZE;
        }

        mCacheFile = new File(mCachePath,"http_cache.txt");
        if (!mCacheFile.exists()){
            if (!mCacheFile.getParentFile().exists()){
                mCacheFile.getParentFile().mkdirs();
            }

            try {
                mCacheFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String[] getPermissions(){
        return NEED_PERMISSION;
    }

    @Override
    public boolean checkPermission(Context context) {
        for (int i = 0; i < NEED_PERMISSION.length; i++) {
            int result = ContextCompat.checkSelfPermission(context,NEED_PERMISSION[i]);

            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }

        Log.d(TAG,"checkPermission, return OK!");

        return true;
    };


    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    @Override
    public String ObjectToJson(Object o) {
        if(null == mGson){
            mGson = new Gson();
        }

        return mGson.toJson(o);
    }

    @Override
    public <T> T JsonToObject(Class clazz, String json) {
        if(null == mGson){
            mGson = new Gson();
        }

        return (T) mGson.fromJson(json,clazz);
    }
}
