package com.crowley.pccontroller;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.crowley.model.Constants;
import com.crowley.model.LoginService;
import com.crowley.model.MessageQueue;



//TODO 目前版本主线程会阻塞，UI体验极差
public class MainActivity extends Activity {
	public static MessageQueue messageQueue = new MessageQueue();
	TextView moveArea;
	PointF preCoordinate = new PointF();
	PointF clickPressCoordinate = new PointF();
	long clickPressTime;
	long clickDuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		moveArea = (TextView) findViewById(R.id.move_area);;
		moveArea.setOnTouchListener(new MyOnTouchListener());
		new LoginService().start();
		
	}
	
	
	@Override
	protected void onPause() {
		messageQueue.clearQueue();
		super.onPause();
	}


	@Override
	protected void onDestroy() {
		//TODO
		super.onDestroy();
	}


	public class MyOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if(preCoordinate.x == 0) {
					preCoordinate.x = event.getX();
					preCoordinate.y = event.getY();
				}
				float curXCoordinate = event.getX();
				float curYCoordinate = event.getY();
				int xOffset = (int) (curXCoordinate - preCoordinate.x);
				int yOffset = (int) (curYCoordinate - preCoordinate.y);
				//System.out.println(event.getX() + ", " + event.getY());
				MainActivity.this.moveArea.append(xOffset + Constants.MESSAGE_SEPARATOR + yOffset + "\n");
				messageQueue.setMessage(Constants.MESSAGE_TYPE_MOUSE_MOVE + Constants.MESSAGE_SEPARATOR + xOffset + Constants.MESSAGE_SEPARATOR + yOffset + "\n");
				preCoordinate.x = event.getX();
				preCoordinate.y = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				clickDuration = System.currentTimeMillis() - clickPressTime;
				MainActivity.this.moveArea.append("click duration:" + clickDuration + "ms" + "\n");
				if(clickDuration < Constants.MAX_LEFT_CLICK_TIME) {
					messageQueue.setMessage("" + Constants.MESSAGE_TYPE_MOUSE_LEFT_CLICK);
				}
				if(clickDuration > Constants.MIN_RIGHT_CLICK_TIME 
						&& clickDuration < Constants.MAX_RIGHT_CLICK_TIME
						&& calcDistance(clickPressCoordinate , new PointF(event.getX(), event.getY())) < Constants.MAX_RIGHT_CLICK_OFFSET) {
					System.out.println("dis:" +  calcDistance(preCoordinate, new PointF(event.getX(), event.getY())));
					messageQueue.setMessage("" + Constants.MESSAGE_TYPE_MOUSE_RIGHT_CLICK);
				}
				
				preCoordinate.x = 0;
				preCoordinate.y = 0;
				break;
			case MotionEvent.ACTION_DOWN:
				clickPressTime = System.currentTimeMillis();
				clickPressCoordinate.x = event.getX();
				clickPressCoordinate.y = event.getY();
				break;
			default:
				break;
			}
			v.performClick();
			
			return true;
		}

	}
	
	private long calcDistance(PointF p1, PointF p2) {
		return (long)Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2)); 
	}
}
