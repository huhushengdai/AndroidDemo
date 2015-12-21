package com.windy.viewpagecycle.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by wang
 * on 2015/12/14
 * description:
 * 无限循环image适配器
 */
public class InfiniteImageAdapter extends PagerAdapter {
    private List<ImageView> mData;


    public InfiniteImageAdapter(List<ImageView> data){
        mData = data;
    }

    @Override
    public int getCount() {
//        return mData == null || mData.size() == 0 ? 0 : mData.size();
        //设置成最大，使用户看不到边界
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        ImageView img = mData.get(position);
//        container.addView(img);
//        return img;
        //对ViewPager页号求模取出View列表中要显示的项
        position %= mData.size();
        if (position<0){
            position = mData.size()+position;
        }
        ImageView view = mData.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(mData.get(position));
    }


}
