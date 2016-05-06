package com.windy.tool.activity;

import android.app.Activity;
import android.os.Bundle;

import com.windy.tool.inject.ViewUtils;

/**
 * Author: wanglizhi
 * Description:
 * Date: 2016/4/18
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        init();
        down();
    }



    protected void init() {
        initView();
        initData();
    }

    private void initData() {

    }

    protected void initView() {

    }

    protected void down() {

    }
}
