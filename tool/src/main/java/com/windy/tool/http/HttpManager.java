package com.windy.tool.http;

import android.text.TextUtils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * author: wang
 * time: 2015/12/10
 * description:
 * 请求服务端工具
 */
public class HttpManager {
    private static HttpManager manager;

    private OkHttpClient okClient;
    private HttpManager(){
        okClient = new OkHttpClient();
    }

    public static HttpManager getInstance(){
        if (manager==null){
            synchronized (HttpManager.class){
                if (manager==null){
                    manager = new HttpManager();
                }
            }
        }
        return manager;
    }

    /**
     * http GET 请求
     * @param url
     * @param callback
     * @return
     */
    public Call httpGet(String url,Map<String,String> head,HttpCallBack callback){
        if (TextUtils.isEmpty(url)){
            return null;
        }
        Call call = okClient.newCall(createRequest(url,head));
        call.enqueue(callback);
        return call;
    }
    /**
     * 创建Request
     * @param url
     * @param head
     * @return Request
     */
    private Request createRequest(String url,Map<String,String> head){
        Request.Builder builder = new Request.Builder().url(url);
        if (head!=null&&head.size()>0){
            //添加请求头信息
            for (Map.Entry<String,String> entry:head.entrySet()){
                builder.addHeader(entry.getKey(),entry.getValue());
            }
        }
        return builder.build();
    }

    //-----------------image--------------

}
