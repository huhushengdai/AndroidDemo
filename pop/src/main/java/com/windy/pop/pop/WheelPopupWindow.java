package com.windy.pop.pop;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
public class WheelPopupWindow extends BasePopWindow{
    private Activity mActivity;
    private BaseAdapter mWheelAdapter;
    //listener
    private SubmitListener mSubmitListener;//确定监听
    private CancelListener mCancelListener;//取消监听
    //--------view----------------
    private WheelView mWheelView;//选择器View

    public WheelPopupWindow(Activity activity){
        super();
        mActivity = activity;
        initView();
    }

    protected void initView() {
        View parent = View.inflate(mActivity,R.layout.pop_wheel,null);
        ViewUtils.inject(this,parent);

        mWheelView = (WheelView) parent.findViewById(R.id.pop_wheel_view);
        //确定点击事件
        parent.findViewById(R.id.pop_wheel_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSubmitListener!=null){
                    mSubmitListener.submit(mWheelView.getSelectedItemPosition());
                }
                dismiss();
            }
        });
        //取消点击事件
        parent.findViewById(R.id.pop_wheel_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelListener!=null){
                    mCancelListener.cancel();
                }
                dismiss();
            }
        });
        setContentView(parent);

        setWidth(-1);
        setHeight(-2);
        setBgAlphaAnim(mActivity);
    }


    public void setWheelAdapter(BaseAdapter adapter){
        mWheelAdapter = adapter;
        mWheelView.setAdapter(adapter);
    }

    public BaseAdapter getWheelAdapter(){
        return mWheelAdapter;
    }

    /**
     * 设置WheelView滚动结束接听
     * @param listener
     */
    public void setOnEndFlingListener(TosGallery.OnEndFlingListener listener){
        mWheelView.setOnEndFlingListener(listener);
    }

    /**
     * 设置确定监听
     */
    public void setSubmitListener(SubmitListener listener){
        mSubmitListener = listener;
    }
    /**
     * 设置取消监听
     */
    public void setCancelListener(CancelListener listener){
        mCancelListener = listener;
    }



    /**
     * 设置未选中的Item数量
     * 2的整数倍
     * @param count 未选中Item数量
     */
    public void showUncheckedItemCount(int count){
        if (mWheelAdapter == null){
            return;
        }
        count = count/2*2;//取2的整数倍
        View view = mWheelAdapter.getView(0,null,null);
        view.measure(0,0);
        int height = view.getMeasuredHeight()*(count+1);
        ViewGroup.LayoutParams params = mWheelView.getLayoutParams();
        params.height = height;
        mWheelView.setLayoutParams(params);
    }



    //-----------------------------------   interface    -----------------------------
    interface SubmitListener{
        public void submit(int position);
    }

    interface CancelListener{
        public void cancel();
    }
}
