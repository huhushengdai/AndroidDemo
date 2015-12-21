package com.windy.viewpagecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.windy.viewpagecycle.adapter.InfiniteImageAdapter;
import com.windy.viewpagecycle.widget.CycleViewPage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
//        cycleHandler = new Handler();
        CycleViewPage page = (CycleViewPage) findViewById(R.id.main_view_page);
        page.setAdapter(new InfiniteImageAdapter(createImages()));
        page.setCurrentItem(Integer.MAX_VALUE / 2);
        page.setCycleSwitch(true);
    }

    public ArrayList<ImageView> createImages() {
        ArrayList<ImageView> imageViews = new ArrayList<>();
        ImageView imageView;
        for (int i = 0; i < 1; i++) {
            imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageViews.add(imageView);
        }
        return imageViews;
    }
}
