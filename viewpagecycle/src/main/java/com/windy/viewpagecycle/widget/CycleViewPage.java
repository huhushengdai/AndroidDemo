package com.windy.viewpagecycle.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * author: wang
 * time: 2015/12/14
 * description:
 * 可以设置自动循环
 */
public class CycleViewPage extends ViewPager implements Runnable{

    private static final int TURN_NEXT = 1;//跳转到下一页page
    private boolean switchState = false;//是否开启自动循环
    private int interval = 3000;//跳转到下一页的间隔时间
    private CycleHandler cycleHandler;

    public CycleViewPage(Context context) {
        super(context);
    }

    public CycleViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public synchronized void setCycleSwitch(boolean isOpen){
        if (cycleHandler==null){
                cycleHandler = new CycleHandler();
        }
        if (isOpen){
            if (!switchState){
                new Thread(this).start();
            }
        }
        switchState = isOpen;
    }

    @Override
    public void run() {
        try {
            while(switchState){
                Thread.sleep(interval);
                cycleHandler.sendEmptyMessage(TURN_NEXT);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    class CycleHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                setCurrentItem(getCurrentItem()+ TURN_NEXT);
            }
        }
    }
}
