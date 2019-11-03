package com.bibi.daodao.tool;

import android.util.Log;

public class MyLog {

    public static void i(String tag, String content){
        Log.i(tag, content);
    }

    public static void d(String tag, String content){
        Log.d(tag, content);
    }

    public static void e(String tag, String content){
        Log.e(tag, content);
    }

    public static void w(String tag, String content){
        Log.w(tag, content);
    }
}
