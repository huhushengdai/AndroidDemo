package com.windy.pop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.windy.pop.R;
import com.windy.tool.adapters.AppBaseAdapter;
import com.windy.tool.adapters.BaseViewHolder;
import com.windy.tool.inject.ViewInject;

import java.util.List;

/**
 * Author: wanglizhi
 * Description:
 * Date: 2016/5/10
 */
public class TimeWheelAdapter2 extends AppBaseAdapter<String>{
    public TimeWheelAdapter2(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public BaseViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public int getViewLayout() {
        return R.layout.item_text;
    }

    @Override
    public void initView(BaseViewHolder vh, int position) {
        ((ViewHolder)vh).textView.setText(mData.get(position));
    }

    static class ViewHolder extends BaseViewHolder{
        @ViewInject(R.id.item_text1)
        TextView textView;

        public ViewHolder(View convertView) {
            super(convertView);
        }
    }
}
