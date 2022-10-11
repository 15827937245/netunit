package com.llthx.netunit;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class NetUnitClient implements InvocationHandler {
    private NetUnitInterface unitInterface;
    private String TAG = "NetUnitClient";

    private NetUnitClient(NetUnitInterface unitInterface){
        this.unitInterface = unitInterface;
    }

    public static NetUnitInterface getNetUnitClient(NetUnitInterface unitInterface){
        return (NetUnitInterface)Proxy.newProxyInstance(NetUnitInterface.class.getClassLoader()
                ,new Class[]{NetUnitInterface.class}
                , new NetUnitClient(unitInterface));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG,method.getName() + ", Start");

        Object result = method.invoke(unitInterface, args);

        Log.d(TAG,method.getName() + ", End");
        return result;
    }

}
