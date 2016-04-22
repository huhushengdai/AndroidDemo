package com.windy.tool.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * author: wang
 * time: 2016/1/12
 * description:
 */
public class BaseApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //chrome调试     chrome浏览器中输入：chrome://inspect
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
