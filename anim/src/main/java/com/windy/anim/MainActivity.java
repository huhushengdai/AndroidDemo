package com.windy.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.windy.tool.activity.BaseActivity;
import com.windy.tool.inject.ContentView;
import com.windy.tool.inject.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.yellow_container)
    private LinearLayout mYellowText;
    @ViewInject(R.id.main_text)
    private TextView mText;
    @ViewInject(R.id.yellow2)
    private TextView mYellow2;

    private PopupWindow mPopWindow;

    public void clickEvent(final View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, -mYellowText.getHeight());
        animator.setTarget(mYellowText);
        animator.setDuration(1000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mYellowText.setTranslationY((Float) animation.getAnimatedValue());
                mYellowText.postInvalidate();
                mYellowText.invalidate();

            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mPopWindow == null) {
                    mPopWindow = createPop();
                }
                mPopWindow.showAsDropDown(mYellowText);
                mYellow2.setVisibility(View.VISIBLE);
            }
        });
    }

    private PopupWindow createPop() {
        View contentView = View.inflate(this, R.layout.pop_test, null);
        PopupWindow popupWindow = new PopupWindow(contentView, -1, -1);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ValueAnimator animator = ValueAnimator.ofFloat(-mYellowText.getHeight(), 0);
                animator.setTarget(mYellowText);
                animator.setDuration(1000).start();
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mYellowText.setTranslationY((Float) animation.getAnimatedValue());
                        mYellowText.postInvalidate();
                        mYellowText.invalidate();
//                        mYellow2.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        return popupWindow;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPopWindow != null && mPopWindow.isShowing()) {
                mPopWindow.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
