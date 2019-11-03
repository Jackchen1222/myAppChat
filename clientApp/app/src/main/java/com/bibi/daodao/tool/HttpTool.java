package com.bibi.daodao.tool;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.*;

public class HttpTool {
    private static final String TAG = "HttpTool";

    private String urlRoot = "http://daodao.free.idcfengye.com";
    private String register = "register";
    private Context mContext;
    private HttpStateHandler mHttpStateHandler;
    public HttpTool(Context ctx){
        mContext = ctx;
        mHttpStateHandler = new HttpStateHandler();
    }

    public static class HttpStateType{
        public static final int registerSuccess = 100;
        public static final int registerFailed = 101;
        public static final int loginSuccess = 201;
        public static final int loginFailed = 202;
    }

    private class HttpStateHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == HttpStateType.registerSuccess){
                Toast.makeText(mContext, "注册成功!", Toast.LENGTH_LONG).show();
            }else if(msg.what == HttpStateType.registerFailed){
                String reason = (String)msg.obj;
                if(reason.equals("user exist")) {
                    Toast.makeText(mContext, "该ID已存在,请重新输入!", Toast.LENGTH_LONG).show();
                }else if(reason.equals("unqualified")){
                    Toast.makeText(mContext, "不符合注册条件,请重新输入!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, "注册失败!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void registerPost(UserInfo userInfo){
        if(userInfo == null){
            return;
        }

        MediaType JSON = MediaType.parse("application/json;charset=utf-8");

        Map<String,String> map = new HashMap<>();
        map.put("password", userInfo.password);
        map.put("nickname", userInfo.nickname);
        if(userInfo.sex) {
            map.put("sex", "0");
        }else{
            map.put("sex", "1");
        }
        JSONObject jsonObject = new JSONObject(map);
        RequestBody requestBody = RequestBody.create(JSON,jsonObject.toString());

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(urlRoot + "/" + register + "/" )
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
                MyLog.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                try {
                    String jsonData = response.body().string();
                    JSONObject Jobject = new JSONObject(jsonData);
                    String ret = Jobject.getString("result");
                    if(ret.equals("success")) {
                        MyLog.e(TAG, "id=" + Jobject.getString("id"));
                        mHttpStateHandler.sendEmptyMessage(HttpStateType.registerSuccess);
                    }else{
                        MyLog.e(TAG, Jobject.getString("reason"));
                        Message msg = new Message();
                        msg.what = HttpStateType.registerFailed;
                        msg.obj = Jobject.getString("reason");
                        mHttpStateHandler.sendMessage(msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
//                MyLog.i(TAG, response.toString() );
            }
        });
    }

    public void get(String id, String pwd, String nickName){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(urlRoot + "/" + register + "/" )
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
                MyLog.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                MyLog.i(TAG, response.toString() );
                try {
                    String jsonData = response.body().string();
                    JSONObject Jobject = new JSONObject(jsonData);
                    MyLog.e(TAG, Jobject.getString("result"));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

}
