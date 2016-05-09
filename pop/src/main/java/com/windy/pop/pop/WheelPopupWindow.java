package com.windy.pop.pop;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;

import com.windy.pop.R;
import com.windy.pop.widget.TosGallery;
import com.windy.pop.widget.WheelView;
import com.windy.tool.inject.ViewInject;
import com.windy.tool.inject.ViewUtils;

/**
 * Author: wanglizhi
 * Description:滚动选择器  PopupWindow
 * Date: 2016/5/9
 */
public class WheelPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {
    private Activity mActivity;
    private BaseAdapter mWheelAdapter;

    public static final int ANIM_POP = R.style.anim_pop_base;//默认显示和隐藏动画
    //--------view----------------
    @ViewInject(R.id.pop_wheel_container)
    ViewGroup mContainer;//内容父类容器
    WheelView mWheelView;//选择器View
    public WheelPopupWindow(Activity activity){
        mActivity = activity;
        init();
    }

    /**
     * 初始化PopupWindow
     */
    private void init() {
        initView();
    }

    private void initView() {
        View parent = View.inflate(mActivity,R.layout.pop_wheel,null);
        ViewUtils.inject(this,parent);

        mWheelView = new WheelView(mActivity);
//        mWheelView.setOnEndFlingListener(mListener);//滑动结束监听
//        mWheelView.setSoundEffectsEnabled(true);
        mWheelView.setLayoutParams(new ViewGroup.LayoutParams(-1,-2));
        mWheelView.setGravity(Gravity.CENTER);
        setContainer(mWheelView);
        setContentView(parent);

        setWidth(-1);
        setHeight(-2);


        setBackgroundDrawable(new BitmapDrawable());
        this.setOutsideTouchable(true);// 设置外部可点击
        this.setFocusable(true);// 设置弹出窗体可点击
        setAnimationStyle(ANIM_POP);//设置弹出动画
        setClippingEnabled(false);

        setOnDismissListener(this);
    }

    /**
     * 添加内容View
     * @param childView 内容view
     */
    public void setContainer(View childView){
        mContainer.removeAllViews();
        mContainer.addView(childView);
    }

    public void setWheelAdapter(BaseAdapter adapter){
        mWheelAdapter = adapter;
        mWheelView.setAdapter(adapter);
    }

    public BaseAdapter getWheelAdapter(){
        return mWheelAdapter;
    }

    public void setOnEndFlingListener(TosGallery.OnEndFlingListener listener){
        mWheelView.setOnEndFlingListener(listener);
    }

    public void setWheelViewParams(int width,int height){
        ViewGroup.LayoutParams params = mWheelView.getLayoutParams();
        if (width!=0) {
            params.width = width;
        }
        if (height!=0){
            params.height = height;
        }
        mWheelView.setLayoutParams(params);
    }

    public void setWheelViewParams(ViewGroup.LayoutParams params){
        mWheelView.setLayoutParams(params);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        ValueAnimator moveAnim = ValueAnimator.ofFloat(1,0.5f);
        moveAnim.setTarget(mActivity);
        moveAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha= (Float) animation.getAnimatedValue();
                mActivity.getWindow().setAttributes(lp);
            }
        });
        moveAnim.setDuration(2000);
        moveAnim.start();
    }


    @Override
    public void onDismiss() {
        ValueAnimator moveAnim = ValueAnimator.ofFloat(0.5f,1);
        moveAnim.setTarget(mActivity);
        moveAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha= (Float) animation.getAnimatedValue();
                mActivity.getWindow().setAttributes(lp);
            }
        });
        moveAnim.setDuration(2000);
        moveAnim.start();
    }
}
