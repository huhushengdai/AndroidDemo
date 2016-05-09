package com.windy.pop.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.windy.pop.R;

/**
 * Author: wanglizhi
 * Description:Base  PopupWindow
 * Date: 2016/5/6
 */
public class BasePopWindow extends PopupWindow {

    public static final int ANIM_POP = R.style.anim_pop_base;//默认显示和隐藏动画
    public static final int ANIM_BACKGROUND_SHOW = 0;//默认背景显示动画
    public static final int ANIM_BACKGROUND_DISMIS = 0;//默认背景隐藏动画

    public BasePopWindow(Context context) {
        super(context);
        initPop();
    }

    public BasePopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPop();
    }

    public BasePopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPop();
    }

    public BasePopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPop();
    }

    public BasePopWindow() {
        initPop();
    }

    public BasePopWindow(View contentView) {
        super(contentView);
        initPop();
    }

    public BasePopWindow(int width, int height) {
        super(width, height);
        initPop();
    }

    public BasePopWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        initPop();
    }

    public BasePopWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        initPop();
    }

    /**
     * 初始化PopupWindow
     * 默认初始化设置
     */
    private void initPop() {
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//        BitmapDrawable bitmapDrawable = new BitmapDrawable();
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(new BitmapDrawable());
        this.setOutsideTouchable(true);// 设置外部可点击
        this.setFocusable(true);// 设置弹出窗体可点击
        setAnimationStyle(ANIM_POP);//设置弹出动画
        setClippingEnabled(false);

    }

    public void setBackgroundShow(Activity activity){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha=0.5f;
        activity.getWindow().setAttributes(lp);
    }

//    public static class Builder {
//        final BasePopWindow pop;
//
//        public Builder(Context context) {
//            pop = new BasePopWindow(context);
//            initPop();
//        }
//
//        public Builder(Context context, AttributeSet attrs) {
//            pop = new BasePopWindow(context, attrs);
//            initPop();
//        }
//
//        public Builder(Context context, AttributeSet attrs, int defStyleAttr) {
//            pop = new BasePopWindow(context, attrs, defStyleAttr);
//            initPop();
//        }
//
//        public Builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//            pop = new BasePopWindow(context, attrs, defStyleAttr, defStyleRes);
//            initPop();
//        }
//
//        public Builder(View contentView) {
//            pop = new BasePopWindow(contentView);
//            initPop();
//        }
//
//        public Builder(int width, int height) {
//            pop = new BasePopWindow(width, height);
//            initPop();
//        }
//
//        public Builder(View contentView, int width, int height) {
//            pop = new BasePopWindow(contentView, width, height);
//            initPop();
//        }
//
//        public Builder(View contentView, int width, int height, boolean focusable) {
//            pop = new BasePopWindow(contentView, width, height, focusable);
//            initPop();
//        }
//
//        /**
//         * 初始化PopupWindow
//         * 默认初始化设置
//         */
//        private void initPop() {
//            // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//            pop.setBackgroundDrawable(new BitmapDrawable());
//
//            pop.setAnimationStyle(ANIM_POP);
//        }
//    }
}
