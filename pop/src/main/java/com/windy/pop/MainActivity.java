package com.windy.pop;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.windy.pop.pop.BasePopWindow;
import com.windy.tool.activity.BaseActivity;
import com.windy.tool.inject.ContentView;
import com.windy.tool.inject.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.main_parent)
    View container;
    @ViewInject(R.id.button)
    Button button;
    BasePopWindow popupWindow;
    @Override
    protected void initView() {
        View view = View.inflate(this,R.layout.pop_test,null);
        popupWindow = new BasePopWindow(view,-1,-2);

    }

    public void clickEvent(View view){
//        popupWindow.showAtLocation(view, Gravity.BOTTOM ,0,0);
        popupWindow.showAsDropDown(view);
        popupWindow.setBackgroundShow(this);
        Log.e("container","Height:"+container.getHeight());
    }
}
