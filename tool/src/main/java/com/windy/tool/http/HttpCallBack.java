package com.windy.tool.http;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * author: wang
 * time: 2015/12/15
 * description:
 * OkHttp回调接口
 */
public abstract class HttpCallBack implements Callback{
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(Request request,final IOException e) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                try {
                    onFail(e.getMessage());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResponse(final Response response) throws IOException {
        final String json = response.body().string();
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                try {
                    onSuccess(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public abstract void onSuccess(String response) throws Exception;
    public abstract void onFail(String msg) throws Exception;
}
