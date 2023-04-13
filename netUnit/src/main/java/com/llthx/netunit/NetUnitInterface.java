package com.llthx.netunit;

import android.content.Context;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import okhttp3.Callback;

public interface NetUnitInterface {
    String GET(URL url) throws IOException;
    void GET(URL url, NetUnitResponseCallback callback);
    String GET(String url) throws IOException;
    void GET(String url, NetUnitResponseCallback callback);
    String POST(URL url, Map map) throws IOException;
    void POST(URL url, Map map, NetUnitResponseCallback callback);
    String POST(String url, Map map) throws IOException;
    void POST(String url, Map map, NetUnitResponseCallback callback);
    String POST(String url, String json) throws IOException;
    void POST(String url, String json, NetUnitResponseCallback callback);
    void setCacheMaxSize(int cacheMaxSize);
    void setCachePath(String path);
    void init();
    boolean checkPermission(Context context);
    String[] getPermissions();
    <T> T JsonToObject(Class clazz,String json);
    String ObjectToJson(Object o);
}
