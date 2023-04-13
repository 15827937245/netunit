package com.llthx.netunitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.llthx.netunit.NetUnitClient;
import com.llthx.netunit.NetUnitInterface;
import com.llthx.netunit.NetUnitResponseCallback;
import com.llthx.netunit.OkhttpUnit;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
private NetUnitInterface mNetUnit;
private Handler mHandler = new Handler(Looper.getMainLooper()){
    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        Toast.makeText(getApplicationContext(),msg.obj.toString(), Toast.LENGTH_LONG).show();
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNetUnit = NetUnitClient.getNetUnitClient(new OkhttpUnit());

        if (!mNetUnit.checkPermission(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(mNetUnit.getPermissions(),1000);
            }
        }
    }

    public void getMsg(View view) {
        new Thread(()->{
            try {
                String result = mNetUnit.GET("http://192.168.31.245:8088/test");
                Message message = new Message();
                message.obj = result;
                mHandler.sendMessage(message);
            } catch (Exception e) {
                String result = "异常";
                Message message = new Message();
                message.obj = result;
                mHandler.sendMessage(message);
            }

            String id = "test1";
            String password = "123456";
            JSONObject json = new JSONObject();
            try {
                json.put("id",id);
                json.put("password",password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            mNetUnit.POST("http://192.168.31.245:8088/getUserInfo",json.toString(),new NetUnitResponseCallback() {
//                @Override
//                public void onStart() {
//
//                }
//
//                @Override
//                public void onFailure(String reason) {
//
//                }
//
//                @Override
//                public void onSuccess(String json) {
//                    Log.d("onSuccess","json : " + json);
//
//                    Message message = new Message();
//                    message.obj = json;
//                    mHandler.sendMessageDelayed(message,3000);
//                }
//
//                @Override
//                public void onEnd() {
//
//                }
//            });
//            Message message = new Message();
//            message.obj = result;
//            mHandler.sendMessage(message);
        }).start();
    }
}
