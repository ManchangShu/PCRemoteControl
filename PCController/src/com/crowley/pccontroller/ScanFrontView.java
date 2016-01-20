package com.crowley.pccontroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

@SuppressLint("DrawAllocation")
public class ScanFrontView extends View {
	Display display;
	int left;
	int top;
	int right;
	int bottom;
	final int RECT_LEN = 240;
	int yPos;
	final int MIN_Y_POS;
	final int MAX_Y_POS;
	boolean goingDown = true;

	public ScanFrontView(Context context, AttributeSet attrs) {
		super(context, attrs);
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = manager.getDefaultDisplay();
		left = (display.getWidth() - RECT_LEN) / 2;
		right = left + RECT_LEN;
		top = (display.getHeight() - RECT_LEN) / 2;
		bottom = top + RECT_LEN;
		yPos = top;
		MIN_Y_POS = top;
		MAX_Y_POS = bottom;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAlpha(150);
		paint.setAntiAlias(true);
		paint.setStrokeMiter(2);
		paint.setStrokeCap(Cap.SQUARE);
		paint.setStrokeJoin(Join.BEVEL);
		
		//draw shadow
		canvas.drawRect(0, 0, left, display.getHeight(), paint);//left rect
		canvas.drawRect(right, 0, display.getWidth(), display.getHeight(), paint);//right rect
		canvas.drawRect(left, 0, right, top, paint);//top rect
		canvas.drawRect(left, bottom, right, display.getHeight(), paint);//bottom rect
		
		//draw white rectangle 
		paint.setColor(Color.WHITE);
		canvas.drawLine(left, top, right, top, paint);
		canvas.drawLine(left, bottom, right, bottom, paint);
		canvas.drawLine(left, top, left, bottom, paint);
		canvas.drawLine(right, top, right, bottom, paint);
		
		//draw moving line
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(3);
		updateYPos();
		canvas.drawLine(left, yPos, right, yPos, paint);
		
		//draw hint text
		paint.setTextSize(22);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText(this.getContext().getResources().getString(R.string.scan_me), display.getWidth() / 2, bottom + 50, paint);
		
		
		invalidate();
	}
	
	private void updateYPos() {
		if(goingDown) {
			yPos += 3;
			if(yPos >= MAX_Y_POS) {
				goingDown = false;
			}
		} else {
			yPos -= 3;
			if(yPos <= MIN_Y_POS) {
				goingDown = true;
			}
		}
	}

}
