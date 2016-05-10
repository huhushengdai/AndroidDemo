package com.windy.pop;

import android.view.View;
import android.widget.Button;

import com.windy.pop.adapter.TimeWheelAdapter;
import com.windy.pop.adapter.TimeWheelAdapter2;
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


    @Override
    protected void initView() {
        View view = View.inflate(this,R.layout.pop_test,null);
//        View view = View.inflate(this,R.layout.pop_wheel,null);
        popupWindow = new WheelPopupWindow(this);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(i+"--->");
        }
//        TimeWheelAdapter adapter = new TimeWheelAdapter(this,data);
        TimeWheelAdapter2 adapter = new TimeWheelAdapter2(this,data);
        popupWindow.setWheelAdapter(adapter);
        popupWindow.showUncheckedItemCount(5);

    }

    public void clickEvent(View view){
        popupWindow.showAsDropDown(view);
    }


}
