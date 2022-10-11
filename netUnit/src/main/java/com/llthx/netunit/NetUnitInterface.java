package com.llthx.netunit;

import java.net.URL;
import java.util.Map;

import okhttp3.Callback;

public interface NetUnitInterface {
    String GET(URL url);
    void GET(URL url, NetUnitResponseCallback callback);
    String GET(String url);
    void GET(String url, NetUnitResponseCallback callback);
    String POST(URL url, Map map);
    void POST(URL url, Map map, NetUnitResponseCallback callback);
    String POST(String url, Map map);
    void POST(String url, Map map, NetUnitResponseCallback callback);
    String POST(String url, String json);
    void POST(String url, String json, NetUnitResponseCallback callback);
    void setCacheMaxSize(int cacheMaxSize);
    void setCachePath(String path);
    void init();
    boolean checkPermission();
    String[] getPermissions();
    <T> T JsonToObject(Class clazz,String json);
    String ObjectToJson(Object o);
}
