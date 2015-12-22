package com.example.testdrawsd;

import java.math.BigDecimal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

public class NewCarcicleView extends SurfaceView implements Callback {

	private SurfaceHolder holder;
	private Paint paint;
	private Canvas canvas;

	private int screenWidth;

	private int screenHeight;

	private float density;

	private int densityDpi;

	private Context mContext;

	private boolean flag;

	private Rect bgRect;

	// pan
	private Bitmap speedPan, oilPan, templurePan;

	// 指针
	private Bitmap speedPointer, oilPointer, templurePointer;

	// 坐标 (xy)

	// pan
	private float speedPanX, speedPanY;
	private float templurePanX, templurePanY;
	private float oilPanX, oilPanY;

	// zhi zheng

	private float speedPointerX, speedPointerY;
	private float templurePointerX, templurePointerY;
	private float oilPointerX, oilPointerY;

	// du
	private float templureDegrees = 0;

	private float oilDegrees = 0;

	private float speedDegrees = 0;

	// 系统传过来的参数 表示度数
	private float currentSpeedDegrees;

	private float currentTemplureDegrees;

	private float currentOilDegrees;

	public NewCarcicleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		init();
	}

	public NewCarcicleView(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public NewCarcicleView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		mContext = context;
		init();
	}

	private void init() {
		holder = getHolder();
		holder.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setColor(Color.argb(255, 207, 60, 11));
		paint.setTextSize(22);
		setFocusable(true);
		setFocusableInTouchMode(true);
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager manager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);

		manager.getDefaultDisplay().getMetrics(metric);

		density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
		screenWidth = metric.widthPixels;

		screenHeight = (int) (metric.heightPixels * 0.4f);
	}

	private class DrawThread extends Thread {

		@Override
		public void run() {
			while (flag) {
				long start = System.currentTimeMillis();

				myDraw();
				changeSpeed();

//				if (isOverSpeed && isOverTemplure && isOverOil) {
					//如果 3个表盘都结束，则推出绘制线程
//					flag = false;
//					break;
//
//				}

				long end = System.currentTimeMillis();
				try {

					if (end - start < 50)
						Thread.sleep(50 - (end - start));

				} catch (Exception e) {
					e.printStackTrace();
				}
				// int temp = 180 / 240 * speedDegrees+60;

				// break;
				Thread.interrupted();
			}
		}

	}

	boolean isOverSpeed = false;
	boolean isOverTemplure = false;
	boolean isOverOil = false;
	public void stopDraw(){
		flag = false;
	}
	public void changeSpeed() {

		if (speedDegrees > currentSpeedDegrees) {

//			speedDegrees = currentSpeedDegrees;
//
//			isOverSpeed = true;
			speedDegrees--;
		} else {
			speedDegrees++;
		}

		if (templureDegrees > currentTemplureDegrees) {
//			templureDegrees = currentTemplureDegrees;
//			isOverTemplure = true;
			templureDegrees--;
		} else {
			templureDegrees++;
		}

		if (oilDegrees > currentOilDegrees) {
//			oilDegrees = currentOilDegrees;
//			isOverOil = true;
			oilDegrees--;
		} else {
			oilDegrees++;
		}

	}

	public void myDraw() {
		try {
			canvas = holder.lockCanvas(bgRect);
			canvas.drawColor(Color.WHITE);
			drawSpeed();
			drawOil();
			drawTemplure();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				holder.unlockCanvasAndPost(canvas);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public void startDraw(float speedD, float templureD, float oilD) {
		if (speedD > 240) {
			speedD = 0;
		}

		if (templureD > 100 || templureD < -60) {
			templureD = 0;
		}

		if (oilD > 600) {
			oilD = 0;
		}

		BigDecimal b = new BigDecimal(180.0f / 190.0f * speedD);

		currentSpeedDegrees = b.setScale(1, BigDecimal.ROUND_HALF_UP)
				.floatValue();
		BigDecimal c = new BigDecimal(90.0f / 100.0f * templureD);
		currentTemplureDegrees = c.setScale(1, BigDecimal.ROUND_HALF_UP)
				.floatValue();
		BigDecimal d = new BigDecimal(180.0f / 475.0f * oilD);
		currentOilDegrees = d.setScale(1, BigDecimal.ROUND_HALF_UP)
				.floatValue();

	}

	public void start(float speedD, float templureD, float oilD) {
		if (speedD > 240) {
			speedD = 0;
		}

		if (templureD > 100 || templureD < -60) {
			templureD = 0;
		}

		if (oilD > 600) {
			oilD = 0;
		}

		BigDecimal b = new BigDecimal(180.0f / 190.0f * speedD);

		currentSpeedDegrees = b.setScale(1, BigDecimal.ROUND_HALF_UP)
				.floatValue();
		BigDecimal c = new BigDecimal(90.0f / 100.0f * templureD);
		currentTemplureDegrees = c.setScale(1, BigDecimal.ROUND_HALF_UP)
				.floatValue();
		BigDecimal d = new BigDecimal(180.0f / 475.0f * oilD);
		currentOilDegrees = d.setScale(1, BigDecimal.ROUND_HALF_UP)
				.floatValue();

//		templureDegrees = 0;

//		oilDegrees = 0;

//		speedDegrees = 0;

	}
	public void reStart(float speedD, float templureD, float oilD){
		start(speedD,templureD,oilD);
		myDraw();
	}
	// @SuppressLint("NewApi")
	// public void stop() {
	//
	//
	// this.destroyDrawingCache();
	// flag = false;
	// holder.unlockCanvasAndPost(canvas);
	// holder.removeCallback(this);
	// holder.getSurface().release();
	//
	// }

	private void initView() {
		speedPan = BitmapFactory.decodeResource(getResources(),
				R.drawable.car_deatil_speed_pan);
		speedPointer = BitmapFactory.decodeResource(getResources(),
				R.drawable.car_deatil_speed);

		templurePan = BitmapFactory.decodeResource(getResources(),
				R.drawable.car_deatil_tem_pan);
		templurePointer = BitmapFactory.decodeResource(getResources(),
				R.drawable.car_deatil_tem);

		oilPan = BitmapFactory.decodeResource(getResources(),
				R.drawable.car_deatil_oil_pan);
		oilPointer = BitmapFactory.decodeResource(getResources(),
				R.drawable.car_deatil_oil);

		bgRect = new Rect(0, 0, screenWidth, screenHeight);
		flag = true;
		new DrawThread().start();
	}

	private void initXY() {

		float sCenterHalf_w_01 = screenWidth * 0.5f;

		float sCenterHalf_h_01 = screenWidth * 0.5f;

		float sCenterHalf_w_02 = sCenterHalf_w_01 * 0.5f;

		float sCenterHalf_h_02 = sCenterHalf_h_01 * 0.5f;

		float sCenterHalf_w_03 = sCenterHalf_w_01 + sCenterHalf_w_02;

		float sCenterHalf_h_03 = sCenterHalf_h_01 + sCenterHalf_h_02;

		// pan
		templurePanX = sCenterHalf_w_02 + sCenterHalf_w_02 * 0.25f;

		templurePanY = 50f;
		// canvas.drawBitmap(templurePan, templurePanX, templurePanY, paint);

		speedPanX = ((sCenterHalf_w_02 * 0.5f) * 0.5f);

		speedPanY = (templurePanY + templurePan.getHeight()) * 0.55f;
		// canvas.drawBitmap(speedPan, speedPanX, speedPanY, paint);

		oilPanX = sCenterHalf_h_01 + ((sCenterHalf_w_02 * 0.5f) * 0.5f) * 0.5f;

		oilPanY = speedPanY;
		// canvas.drawBitmap(oilPan, oilPanX, oilPanY, paint);

		// zhengzhi

		// templurePointerX = sCenterHalf_w_02 + sCenterHalf_w_02 * 0.25f;

		templurePointerX = templurePanX;

		templurePointerY = templurePanY;

		// canvas.drawBitmap(templurePointer, templurePointerX,
		// templurePointerY,
		// paint);

		speedPointerX = speedPanX;

		speedPointerY = speedPanY;

		// canvas.drawBitmap(speedPointer, speedPointerX, speedPointerY, paint);

		oilPointerX = oilPanX;

		oilPointerY = oilPanY;
		// canvas.drawBitmap(oilPointer, oilPointerX, oilPointerY, paint);

	}

	private void drawSpeed() {
		// canvas.drawRect(speedPanX, speedPanY, speedPan.getWidth(),
		// speedPan.getHeight(), paint);
		canvas.drawBitmap(speedPan, speedPanX, speedPanY, paint);
		canvas.save();
		canvas.rotate(speedDegrees,
				speedPointerX + speedPointer.getWidth() / 2,
				speedPointerY + speedPointer.getHeight() / 2);
//		Log.e("width", ":" + speedPointerX + speedPointer.getWidth() / 2);
//		Log.e("height", ":" + speedPointerY	+ speedPointer.getHeight() / 2);
		Log.e("speedDegrees", ":" + speedDegrees);
		canvas.drawBitmap(speedPointer, speedPointerX, speedPointerY, paint);
		canvas.restore();
	}

	private void drawTemplure() {
		// canvas.drawRect(templurePanX, templurePanY, templurePan.getWidth(),
		// templurePan.getHeight(), paint);
		canvas.drawBitmap(templurePan, templurePanX, templurePanY, paint);
		canvas.save();
		canvas.rotate(templureDegrees,
				templurePointerX + templurePointer.getWidth() / 2,
				templurePointerY + templurePointer.getHeight() / 2);
		canvas.drawBitmap(templurePointer, templurePointerX, templurePointerY,
				paint);
		canvas.restore();
	}

	private void drawOil() {
		// canvas.drawRect(oilPanX, oilPanY, oilPan.getWidth(),
		// oilPan.getHeight(), paint);
		canvas.drawBitmap(oilPan, oilPanX, oilPanY, paint);

		canvas.save();
		canvas.rotate(oilDegrees, oilPointerX + oilPointer.getWidth() / 2,
				oilPointerY + oilPointer.getHeight() / 2);
		canvas.drawBitmap(oilPointer, oilPointerX, oilPointerY, paint);
		canvas.restore();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		initView();
		initXY();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		flag = false;
	}

}