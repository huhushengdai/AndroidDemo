package com.windy.tool.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Author: wanglizhi
 * Description:
 * Date: 2016/5/4
 */
public abstract class AppBaseAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mData;

    public AppBaseAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        BaseViewHolder vh;
//        if (view == null){
//            view = View.inflate(mContext,getViewLayout(),parent);
//            vh = getViewHolder(view);
//            view.setTag(vh);
//        }else {
//            vh = (BaseViewHolder) view.getTag();
//        }
//        vh.initView(position);
//        return view;
//    }

    public abstract BaseViewHolder getViewHolder(View convertView);
    public abstract int getViewLayout();
}
