package com.windy.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.windy.tool.activity.BaseActivity;
import com.windy.tool.inject.ContentView;
import com.windy.tool.inject.ViewInject;

import java.util.ArrayList;

/**
 * Author: wanglizhi
 * Description:
 * Date: 2016/4/19
 */
@ContentView(R.layout.activity_payed)
public class PayedActivity extends BaseActivity implements View.OnClickListener {

    public static final int UN_PLAY = 0;//动画没有执行
    public static final int PLAYING = 1;//动画正在执行
    public static final int PLAYED = 2;//动画已经执行结束

    //------view   start ------
    @ViewInject(R.id.payed_title)
    private TextView mTitle;//title
    @ViewInject(R.id.payed_trip_over_info)
    private View mTripInfoContainer;//行程信息界面  容器
    @ViewInject(R.id.payed_scroll_view)
    private ScrollView mPayedScrollView;//scroll view
    @ViewInject(R.id.payed_scroll_container)
    private View mScrollContainer;//ScrollView 的子容器

    //评价界面-----start-----
    @ViewInject(R.id.payed_evaluate_passenger)
    private View mEvaluateContainer;//评价界面容器
    @ViewInject(R.id.evaluate_grade_rating)
    private RatingBar mEvaGradeRating;//评价 等级
    @ViewInject(R.id.evaluate_label)
    private ViewGroup mEvaLabel;//评价标签 容器
    @ViewInject(R.id.evaluate_message_text)
    private TextView mMessageText;//留言 小标题
    @ViewInject(R.id.evaluate_message_edit)
    private EditText mMessageEdit;//留言 输入框
    @ViewInject(R.id.evaluate_content_container)
    private View mEvaContentContainer;//评论内容 容器
    @ViewInject(R.id.evaluate_content)
    private TextView mEvaContent;//评论内容
    @ViewInject(R.id.evaluate_submit)
    private Button mSubmitBtn;//提交评价
    @ViewInject(R.id.payed_after_evaluate_label)
    private ViewGroup mAfterEvaLabel;//评价以后的标签
    //评价界面----end-----

    @ViewInject(R.id.payed_pay_info)
    private View mFeeInfoContainer;//费用信息界面 容器


    //------view   end ------

    //-----动画 宽 高 数据  start-----
    private int mTitleHeight;//title 高度
    private int mTripInfoHeight;//行程 高度
    private int mFeeInfoHeight;//费用信息高度
    private int mEvaHeight;//评价 高度
    private int mScreenHeight;//屏幕高度
    private int mScrollHeight;//滚动布局高度 = mScreenHeight-mTitleHeight-mTripInfoHeight
    //-----动画 宽 高 数据  end-----
    private ViewGroup.LayoutParams mEvaParam;//评价布局参数

    private AnimatorSet mStartAnimSet;//开始动画同时执行
    private AnimatorSet mEndAnimSet;//结束动画同时执行

    private int mStartState = UN_PLAY;//开始动画是否在执行
    private int mEndState = UN_PLAY;//开始动画是否在执行

    private boolean isEvaFlag = false;//是否评价
    //    private boolean isScrollFlag = true;//ScrollView 是否禁止滚动  true 禁止
    private static final int ANIM_TIME = 2000;//动画执行时间

    private ArrayList<CheckBox> mChecks = new ArrayList<>();

    private String[] mLabelStrs = {
            "态度好有礼貌"
            , "中国好乘客"
            , "爱护车辆"
            , "神准时"
            , "主动联系司机"
            , "认路王"
            , "贴心"
    };

    @Override
    protected void init() {
        initView();
        initData();//初始化数据
        initAnim();//初始化动画
    }

    private void initAnim() {
        startAnim();
        endAnim();
    }


    /**
     * 初始化数据
     * 各种动画所需高度
     * 因为onCreate时，还未绘制
     * 所以需要测量view
     */
    private void initData() {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        //set view
        mTitle.measure(width, height);
        mTripInfoContainer.measure(width, height);
        mFeeInfoContainer.measure(width, height);
        mEvaluateContainer.measure(width, height);
        //get height
        mTitleHeight = mTitle.getMeasuredHeight();//标题头高度
        mTripInfoHeight = mTripInfoContainer.getMeasuredHeight();//行程信息高度
        mFeeInfoHeight = mFeeInfoContainer.getMeasuredHeight();//费用信息高度
        mEvaHeight = mEvaluateContainer.getMeasuredHeight();//评价信息高度
        //屏幕高度
        mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        //评价布局参数
        mEvaParam = mEvaluateContainer.getLayoutParams();
        //设置ScrollView 子容器高度
        mScrollHeight = mScreenHeight - mTitleHeight - mTripInfoHeight;
        ViewGroup.LayoutParams params = mScrollContainer.getLayoutParams();
        params.height = mScrollHeight;
        mScrollContainer.setLayoutParams(params);
    }

    protected void initView() {
        mEvaGradeRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mStartAnimSet != null && mStartState == UN_PLAY && !isEvaFlag) {
                    mStartAnimSet.start();
                }
                return false;
            }
        });
        mSubmitBtn.setOnClickListener(this);
    }

    /**
     * 添加评价乘客 标签
     */
    private void addLabel() {
        mChecks.clear();
        mEvaLabel.removeAllViews();
        int count = mLabelStrs.length;
        CheckBox checkBox = null;
        for (int i = 0; i < count; i++) {
            checkBox = (CheckBox) View.inflate(this, R.layout.check_label, null);
            checkBox.setText(mLabelStrs[i]);
            mEvaLabel.addView(checkBox);
            mChecks.add(checkBox);
        }
    }

    /**
     * 添加评论以后的标签
     */
    private void addAfterLabel() {
//        mEvaLabel.removeAllViews();
        mAfterEvaLabel.removeAllViews();//防止一个子view有两个父布局
        int count = mChecks.size();
        CheckBox checkBox = null;
        CheckBox checkBox1 = null;
        for (int i = 0; i < count; i++) {
            checkBox1 = mChecks.get(i);
            if (checkBox1.isChecked()) {
                //被选中就加入“评价后的标签”
                //重新创建一个CheckBox 实例
                checkBox = (CheckBox) View.inflate(this, R.layout.check_label, null);
                checkBox.setText(checkBox1.getText());
                checkBox.setChecked(true);
                mAfterEvaLabel.addView(checkBox);
            }
        }

    }

    /**
     * 启动开始动画
     */
    private void startAnim() {
        //set start
        mStartAnimSet = new AnimatorSet();
        mStartAnimSet.setDuration(ANIM_TIME);
        mStartAnimSet.setInterpolator(new LinearInterpolator());
        //两个动画同时执行
        mStartAnimSet.playTogether(
                createMoveAnim(0, -mFeeInfoHeight),//移动动画
                createExtendAnim(mEvaHeight, mScrollHeight));//拉伸动画
        mStartAnimSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                beginStartAnim();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                afterStartAnim();
            }
        });
    }

    /**
     * 启动结束动画
     */
    private void endAnim() {
        //end set
        mEndAnimSet = new AnimatorSet();
        mEndAnimSet.setDuration(ANIM_TIME);
        mEndAnimSet.setInterpolator(new LinearInterpolator());
        //两个动画同时执行
        mEndAnimSet.playTogether(
                createMoveAnim(-mFeeInfoHeight, 0),//移动动画
                createExtendAnim(mScrollHeight, mEvaHeight));//收缩动画
        mEndAnimSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                beginEndAnim();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                afterEndAnim();

            }
        });
    }

    /**
     * 创建移动动画
     *
     * @param value
     * @return
     */
    private ValueAnimator createMoveAnim(float... value) {
        ValueAnimator moveAnim = ValueAnimator.ofFloat(value);
        moveAnim.setTarget(mEvaluateContainer);
        moveAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEvaluateContainer.setTranslationY((Float) animation.getAnimatedValue());
                mEvaluateContainer.postInvalidate();
                mEvaluateContainer.invalidate();
            }
        });
        return moveAnim;
    }

    /**
     * 创建扩展/收缩动画
     *
     * @param value
     * @return
     */
    private ValueAnimator createExtendAnim(float... value) {
        ValueAnimator shrinkAnim = ValueAnimator.ofFloat(value);
        shrinkAnim.setTarget(mEvaluateContainer);//设置动画对象
        shrinkAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mEvaParam.height = (int) ((float) animation.getAnimatedValue());
                mEvaluateContainer.setLayoutParams(mEvaParam);
                mEvaluateContainer.postInvalidate();
                mEvaluateContainer.invalidate();
            }
        });
        return shrinkAnim;
    }

    /**
     * 在开始动画执行之前
     */
    private void beginStartAnim() {
        mStartState = PLAYING;
        mEndState = UN_PLAY;
        addLabel();
        mEvaLabel.setVisibility(View.VISIBLE);
        mMessageText.setVisibility(View.VISIBLE);
        mMessageEdit.setVisibility(View.VISIBLE);
        mMessageEdit.setText("");
        mSubmitBtn.setVisibility(View.VISIBLE);
//        isScrollFlag = true;//禁止滚动
    }

    /**
     * 开始动画执行之后
     */
    private void afterStartAnim() {
        mStartState = PLAYED;
        mEndState = UN_PLAY;
        mEvaGradeRating.setIsIndicator(false);
        ViewGroup.LayoutParams params = mScrollContainer.getLayoutParams();
        params.height = mEvaluateContainer.getHeight();
        mScrollContainer.setLayoutParams(params);
    }

    /**
     * 结束动画开始之时
     */
    private void beginEndAnim() {
        mEndState = PLAYING;
        mStartState = UN_PLAY;
        mEvaGradeRating.setIsIndicator(true);//设置评价不能触摸
    }

    /**
     * 结束动画结束之后
     */
    private void afterEndAnim() {
        mEndState = PLAYED;
        mStartState = UN_PLAY;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mEndAnimSet != null && mEndState == UN_PLAY) {
                mEndAnimSet.start();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

        mEndAnimSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mAfterEvaLabel.getChildCount() > 0) {
                    mAfterEvaLabel.setVisibility(View.VISIBLE);
                }
                if (!TextUtils.isEmpty(mEvaContent.getText().toString())) {
                    mEvaContentContainer.setVisibility(View.VISIBLE);
                }
//                isScrollFlag = false;//可以滚动
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                addAfterLabel();
                mEvaContent.setText(mMessageEdit.getText());
            }
        });
        mEndAnimSet.start();
        isEvaFlag = true;//已经评价
    }


}
