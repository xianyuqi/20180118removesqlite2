package com.example.administrator.a20180118removesqlite;

import android.app.Application;

/**
 * Created by Administrator
 * on 2018/1/18.
 */

public class MvpApplication extends Application {
    private static MvpApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        MvpApplication.mApplication = this;
    }


    public static Application getApplication(){
        return mApplication;
    }

}
