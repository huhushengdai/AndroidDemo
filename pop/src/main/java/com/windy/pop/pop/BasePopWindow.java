package com.windy.pop.pop;

import android.animation.ValueAnimator;
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
    //-------------------anim   start-------------------------------------------------
    /**
     * PopupWindow 本身的动画执行的动画，放在xml文件中，可自行创建
     * 使用 setAnimationStyle() 方法可以自信设置
     *
     * PopupWindow 外部背景不为PopupWindow控制
     * 此处背景透明动画，是针对背景Activity处理，
     * 当PopupWindow显示（或隐藏）后，改变显示PopupWindow的activity的透明度
     */
    public static final int ANIM_POP = R.style.anim_pop_base;//默认显示和隐藏动画
    private ValueAnimator mBgShowAnim;//开始背景动画
    private float mStartAlpha = 1;//默认开始动画透明度
    private ValueAnimator mBgDismissAnim;//结束背景动画
    private float mEndAlpha = 0.5f;//默认结束动画透明度
    private int mBgTime = 3000;//默认动画执行时间
    //-------------------anim   end---------------------------------------------------


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
        setBackgroundDrawable(new BitmapDrawable());
        this.setOutsideTouchable(true);// 设置外部可点击
        this.setFocusable(true);// 设置弹出窗体可点击
        setAnimationStyle(ANIM_POP);//设置show、dismiss动画
//        setClippingEnabled(false);

    }

    /**
     * 设置背景透明动画
     *
     * @param activity   背景activity
     * @param time       动画执行时间
     * @param startAlpha 开始透明度
     * @param endAlpha   结束透明度
     */
    public void setBgAlphaAnim(Activity activity, int time, float startAlpha, float endAlpha) {
        //开始动画
        mBgShowAnim = createAlphaAnim(activity, startAlpha, endAlpha);
        mBgShowAnim.setDuration(time);
        //结束动画
        mBgDismissAnim = createAlphaAnim(activity, endAlpha, startAlpha);
        mBgDismissAnim.setDuration(time);
    }

    public void setBgAlphaAnim(Activity activity, float startAlpha, float endAlpha) {
        this.setBgAlphaAnim(activity, mBgTime, startAlpha, endAlpha);
    }

    public void setBgAlphaAnim(Activity activity) {
        this.setBgAlphaAnim(activity, mBgTime, mStartAlpha, mEndAlpha);
    }

    /**
     * 创建activity 透明动画
     *
     * @param activity 背景activity
     * @param start    开始透明值
     * @param end      结束透明值
     * @return anim
     */
    protected ValueAnimator createAlphaAnim(final Activity activity, float start, float end) {
        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
        anim.setTarget(activity);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = (Float) animation.getAnimatedValue();
                activity.getWindow().setAttributes(lp);
            }
        });
        return anim;
    }

    /**
     * 设置开始背景动画执行时间
     *
     * @param time 执行时间
     */
    public void setBgShowAnimTime(int time) {
        if (mBgShowAnim == null) {
            throw new NullPointerException(
                    "anim is null,please call setBgAlphaAnim(...) before this");
        }
        mBgShowAnim.setDuration(time);
    }

    /**
     * 设置结束背景动画执行时间
     *
     * @param time 执行时间
     */
    public void setBgDismissAnim(int time) {
        if (mBgDismissAnim == null) {
            throw new NullPointerException(
                    "anim is null,please call setBgAlphaAnim(...) before this");
        }
        mBgDismissAnim.setDuration(time);
    }

    /**
     * 设置开始背景动画 的起始透明度与结束透明度
     *
     * @param start 开始透明度
     * @param end   结束透明度
     */
    public void setBgShowAnimValue(float start, float end) {
        if (mBgShowAnim == null) {
            throw new NullPointerException(
                    "anim is null,please call setBgAlphaAnim(...) before this");
        }
        mBgShowAnim.setFloatValues(start, end);
    }

    /**
     * 设置结束背景动画 的起始透明度与结束透明度
     *
     * @param start 开始透明度
     * @param end   结束透明度
     */
    public void setBgDismissAnimValue(float start, float end) {
        if (mBgDismissAnim == null) {
            throw new NullPointerException(
                    "anim is null,please call setBgAlphaAnim(...) before this");
        }
        mBgDismissAnim.setFloatValues(start, end);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        bgShowAnim();
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        bgShowAnim();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        bgShowAnim();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        bgShowAnim();
        super.showAsDropDown(anchor, xoff, yoff, gravity);

    }

    /**
     * 执行显示背景动画
     */
    private void bgShowAnim() {
        if (isShowing()){
            //如果PopupWindow已经显示，则不执行动画
            return;
        }

        if (mBgShowAnim != null) {
            mBgShowAnim.start();
        }
    }



    /**
     * 执行隐藏背景动画
     */
    private void bgDismissAnim(){
        if (!isShowing()){
            //如果没有显示，则不执行动画
            return;
        }
        if (mBgDismissAnim != null){
            mBgDismissAnim.start();
        }
    }

    @Override
    public void dismiss() {
        bgDismissAnim();
        super.dismiss();
    }

}
