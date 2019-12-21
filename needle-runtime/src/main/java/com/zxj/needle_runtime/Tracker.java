package com.zxj.needle_runtime;

import android.util.Log;

import java.util.Map;

//打点的具体实现
public class Tracker {
    public static void track(Map<String, String> trackMap){
        Log.e("zxj", "track: " + trackMap );

    }
}
