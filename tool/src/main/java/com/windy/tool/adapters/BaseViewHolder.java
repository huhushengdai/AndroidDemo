package com.windy.tool.adapters;

import android.view.View;

import com.windy.tool.inject.ViewUtils;

/**
 * Author: wanglizhi
 * Description:
 * Date: 2016/5/4
 */
public abstract class BaseViewHolder {

    public BaseViewHolder(View convertView){
        ViewUtils.inject(this,convertView);
    }

    public abstract void initView(int position);
}
