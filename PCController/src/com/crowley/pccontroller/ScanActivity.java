package com.crowley.pccontroller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.crowley.model.CameraConfigurationUtils;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

@SuppressWarnings("deprecation")
public class ScanActivity extends Activity  implements SurfaceHolder.Callback, Camera.PreviewCallback{
	private SurfaceView surfaceview;
	private Camera camera;
	private Point screenSize = new Point();
	private Point previewSize = new Point();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_scan);
		this.getScreenSize(screenSize);
		System.out.println(String.format("height:%d, width:%d", screenSize.y, screenSize.x));
		surfaceview = (SurfaceView) findViewById(R.id.preview_view);
		surfaceview.setFocusable(true);
		SurfaceHolder holder = surfaceview.getHolder();
		holder.setKeepScreenOn(true);
		holder.addCallback(this);
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			camera = Camera.open();
			camera.setPreviewDisplay(holder);
			camera.setDisplayOrientation(90);
			camera.setPreviewCallback(this);
			Parameters parameters = camera.getParameters();
			CameraConfigurationUtils.setBestPreviewFPS(parameters);
			//CameraConfigurationUtils.setFocusArea(parameters);//exception thrown
			CameraConfigurationUtils.findBestPreviewSizeValue(parameters, screenSize);
			previewSize.x = parameters.getPreviewSize().width;
			previewSize.y = parameters.getPreviewSize().height;
			System.out.println(String.format("default size height:%d, width:%d",parameters.getPreviewSize().height, parameters.getPreviewSize().width));
			camera.setParameters(parameters);
			camera.startPreview();
			//auto focus per second
			new Thread(new AutoFocusService()).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		System.out.println("in surfaceChanged()...");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		System.out.println("in surfaceDestroyed()...");
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("in destory() ...");
		if(camera != null) {
			//cannot write in surfaceDestroyed(), because exception thrown: 
			//onPreviewFrame() method would be invoked after camera released. 
			synchronized (this) {
				camera.stopPreview();
				camera.release();
				camera = null;
			}
		}
	}


	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		/*PlanarYUVLuminanceSource
		 * byte[] yuvData£º the image data captured by camera
          int dataWidth£ºPreviewSize.width
          int dataHeight£ºPreviewSize.height
          int left,int top,int width,int height£ºdecode the specific area of the image data
          boolean reverseHorizontal£º
		 */
		Size size = camera.getParameters().getPreviewSize();
		//System.out.println("preview size -> height:" + size.height + ", width:" + size.width);
		int width = ScanFrontView.RECT_LEN * size.width / screenSize.y;
		int height = ScanFrontView.RECT_LEN * size.height / screenSize.x;
		int x = (size.width - width) / 2;
		int y = (size.height - height) / 2;
		PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(data, 640, 480, x, y, width, height, true);
		Reader reader = new QRCodeReader();
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		try {
			Result result = reader.decode(bitmap);
			reader.reset();
			System.out.println("text:" + result.getText());
			Intent intent = new Intent();
			intent.putExtra("qr_code", result.getText());
			setResult(RESULT_OK, intent);
			//TODO add beep and vibrate when scan successful
			finish();
		} catch (NotFoundException e1) {
			e1.printStackTrace();
		} catch (ChecksumException e1) {
			e1.printStackTrace();
		} catch (FormatException e1) {
			e1.printStackTrace();
		}
		
		
	}
	

	private void getScreenSize(Point point) {
		WindowManager manager = this.getWindowManager();
		Display display = manager.getDefaultDisplay();
		//System.out.println("rotation:" + display.getRotation());
		display.getSize(point);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Parameters parameters = null;
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent();
			intent.putExtra("qr_code", "");
			setResult(RESULT_CANCELED, intent);
			System.out.println("in finish()...");
			finish();
			break;
		case KeyEvent.KEYCODE_FOCUS:
		case KeyEvent.KEYCODE_CAMERA:
			return true;//consume the event
		case KeyEvent.KEYCODE_VOLUME_UP:
			parameters = camera.getParameters();
			parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(parameters);
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			parameters = camera.getParameters();
			parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(parameters);
			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class AutoFocusService implements Runnable{

		@Override
		public void run() {
			while(camera != null) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (this) {//In order to prevent NullPointException after camera.release();
					if(camera != null) {
						camera.autoFocus(new ScannerAutoFocusCallback());
					}
				}
				
			}
		}
	}
	
	private class ScannerAutoFocusCallback implements AutoFocusCallback {

		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			if (success) {
				if(camera != null) {
					camera.cancelAutoFocus();
				}
			} else {
				System.out.println("onAutoFocus()...failed");
			}
		}
	}
}
