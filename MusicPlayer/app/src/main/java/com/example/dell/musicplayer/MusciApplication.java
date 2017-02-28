package com.example.dell.musicplayer;

import android.app.Application;
import android.content.Context;

/**
 * Created by dell on 2017/1/6.
 */

public class MusciApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext(){
        return  new MusciApplication();
    }
}
