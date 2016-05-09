package com.windy.pop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.windy.pop.utils.Utils;
import com.windy.pop.widget.TosGallery;

import java.util.List;

/**
 * Author: wanglizhi
 * Description:时间选择器
 * Date: 2016/5/6
 */
public class TimeWheelAdapter extends BaseAdapter{
    int mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    int mHeight = 50;

    private Context context;
    private List<String> listData;

    public TimeWheelAdapter(Context context, List<String> listData){
        this.context = context;
        this.listData = listData;
        mHeight = (int) Utils.pixelToDp(context, mHeight);
    }

//    public TimeWheelAdapter(Context context, List<TimeWheelBean> listData, int layoutResID) {
//        super(context, listData, layoutResID);
//        mHeight = (int) com.cloudd.yundidriver.utils.Utils.pixelToDp(context, mHeight);
//    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder vh;
        if (view == null) {
            view = new TextView(context);
            view.setLayoutParams(new TosGallery.LayoutParams(mWidth, mHeight));
            vh = new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }

        vh.textView.setText(listData.get(position));


        return view;
    }




    static class ViewHolder{
        TextView textView;
        public ViewHolder(View convertView){
            textView = (TextView) convertView;
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            textView.setTextColor(Color.BLACK);
        }
    }
}
