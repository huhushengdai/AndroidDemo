package com.windy.anim.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Author: wanglizhi
 * Description:自动换行 ViewGroup
 * Date: 2016/4/19
 */
public class LineWrapViewGroup extends LinearLayout{
    private final static int VIEW_MARGIN = 5;//间隔

    public LineWrapViewGroup(Context context) {
        super(context);
    }

    public LineWrapViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineWrapViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 0;

        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                // 此处增加onlayout中的换行判断，用于计算所需的高度
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width + VIEW_MARGIN;
                y = row * height + height;
                if (x >= maxWidth) {
                    x = width+ VIEW_MARGIN;
                    row++;
                    y = row * height + height;
                }
            }
        }
        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, y);

    }

    @Override//左 上 右 下
    protected void onLayout(boolean arg0, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 0;
        int y = 0;
        int row = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width +VIEW_MARGIN;
                y = row * height + height;
                if (x > maxWidth) {
                    x = width+VIEW_MARGIN;
                    row++;
                    y = row * height + height;
                }
                child.layout(x - width, y - height+VIEW_MARGIN, x, y);
            }
        }

    }
}
