package com.gclue.CameraSample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

public class CameraSample extends Activity {

	private MyView mView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Notification Bar������
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Title Bar������
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		CameraView mCamera = new CameraView(this);
		setContentView(mCamera);

		mView = new MyView(this);
		addContentView(mView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}
}

/**
 * CameraView
 */
class CameraView extends SurfaceView implements SurfaceHolder.Callback {
	/**
	 * Camera�̃C���X�^���X���i�[����ϐ�
	 */
	private Camera mCamera;

	public CameraView(Context context) {
		super(context);
		getHolder().addCallback(this);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/**
	 * Surface�ɕω����������ꍇ�ɌĂ΂��
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.i("CAMERA", "surfaceChaged");

		// ��ʐݒ�
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(width, height);
		mCamera.setParameters(parameters);

		// �v���r���[�\�����J�n
		mCamera.startPreview();
	}

	/**
	 * Surface���������ꂽ�ۂɌĂ΂��
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("CAMERA", "surfaceCreated");

		// �J������Open
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (Exception e) {
		}
	}

	/**
	 * Surface���j�����ꂽ�ꍇ�ɌĂ΂��
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("CAMERA", "surfaceDestroyed");

		// �J������Close
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}
}

/**
 * �I�[�o�[���C�`��p�̃N���X
 */
class MyView extends View {
	
	/**
	 * x���W
	 */
	private int x;
	
	/**
	 * y���W
	 */
	private int y;
		
	/**
	 * �R���X�g���N�^
	 * 
	 * @param context
	 */
	public MyView(Context context) {
		super(context);
		setFocusable(true);
	}

	/**
	 * �`�揈��
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// �w�i�F��ݒ� 
		canvas.drawColor(Color.TRANSPARENT);

		// �`�悷�邽�߂̐��̐F��ݒ� 
		Paint mainPaint = new Paint();
		mainPaint.setStyle(Paint.Style.FILL);
		mainPaint.setARGB(255, 255, 255, 100);

	
		// ���ŕ`��
		canvas.drawLine(x, y, 50, 50, mainPaint);
	}
	
	/**
	 * �^�b�`�C�x���g
	 */
	public boolean onTouchEvent(MotionEvent event) {
		
		// X,Y���W�̎擾 
		x = (int)event.getX();		 
		y = (int)event.getY();
		
		// �ĕ`��̎w�� 
		invalidate();
 
		return true;
	}
}