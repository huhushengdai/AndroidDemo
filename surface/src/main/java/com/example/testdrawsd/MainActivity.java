package com.example.testdrawsd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	NewCarcicleView s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		s = (NewCarcicleView) findViewById(R.id.newCicleView);
		s.start(180, 50, 500);
	}

	public void eventClick(View view){
		s.reStart(120,30,100);
	}

	@Override
	protected void onDestroy() {
		s.stopDraw();
		super.onDestroy();
	}
}
