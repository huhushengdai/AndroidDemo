package com.windy.pop;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;

import com.windy.pop.adapter.TimeWheelAdapter;
import com.windy.pop.pop.BasePopWindow;
import com.windy.pop.pop.WheelPopupWindow;
import com.windy.tool.activity.BaseActivity;
import com.windy.tool.inject.ContentView;
import com.windy.tool.inject.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.main_parent)
    View container;
    @ViewInject(R.id.button)
    Button button;
    WheelPopupWindow popupWindow;

    BasePopWindow basePopWindow;
    @Override
    protected void initView() {
        View view = View.inflate(this,R.layout.pop_test,null);
//        View view = View.inflate(this,R.layout.pop_wheel,null);
        popupWindow = new WheelPopupWindow(this);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(i+"--->");
        }
        TimeWheelAdapter adapter = new TimeWheelAdapter(this,data);
        popupWindow.setWheelAdapter(adapter);
        popupWindow.setWheelViewParams(0,300);
        basePopWindow = new BasePopWindow(view,-1,-2);
    }

    public void clickEvent(View view){
        popupWindow.showAtLocation(view, Gravity.BOTTOM ,0,0);
//        basePopWindow.showAtLocation(view, Gravity.BOTTOM ,0,0);
//        popupWindow.showAsDropDown(view);
//        Log.e("container","Height:"+container.getHeight());
    }


}
