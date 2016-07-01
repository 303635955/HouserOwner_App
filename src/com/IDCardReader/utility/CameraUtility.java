package com.IDCardReader.utility;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.view.Surface;

public class CameraUtility {

	public final static int FRONTCAMERA = 1;
	public final static int BACKCAMERA = FRONTCAMERA+1;
	
	/**
	 * 设置当前需要开启的摄像头
	 * @param whichCamera FRONTCAMERA或者BACKCAMERA
	 * @return 对应摄像头的id
	 */
	@SuppressLint("NewApi")
	public static int setCamera(int whichCamera)
	{
		int cameraIndex=0;
		CameraInfo cameraInfo = new Camera.CameraInfo();
		if(whichCamera==FRONTCAMERA)
		{
			int cameraCount = Camera.getNumberOfCameras();
			for (int i = 0; i < cameraCount; ++i) {
				Camera.getCameraInfo(i, cameraInfo);
				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
					cameraIndex = i;
					break;
				}
			}
		}
		else if(whichCamera==BACKCAMERA)
		{
			int cameraCount = Camera.getNumberOfCameras();
			for (int i = 0; i < cameraCount; ++i) {
				Camera.getCameraInfo(i, cameraInfo);
				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
					cameraIndex = i;
					break;
				}
			}
		}
		return cameraIndex;
	}
	
	/**
	 * 设置摄像头需要调整的角度，以便使预览界面正常显示
	 * @param activity 当前线程
	 * @param cameraId 摄像头id
	 * @param camera 当前使用的camera实例
	 * @return 返回camera需要调整的角度
	 */
	@SuppressLint("NewApi")
	public static int setCameraDisplayOrientation(Activity activity,
	         int cameraId, Camera camera) {
	     Camera.CameraInfo info =new Camera.CameraInfo();
	     Camera.getCameraInfo(cameraId, info);
	     int rotation = activity.getWindowManager().getDefaultDisplay()
	             .getRotation();
	     int degree=0;
	     switch (rotation) {
	         case Surface.ROTATION_0: degree = 0; break;
	         case Surface.ROTATION_90: degree = 90; break;
	         case Surface.ROTATION_180: degree = 180; break;
	         case Surface.ROTATION_270: degree = 270; break;
	     }

	     if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	    	 degree = (info.orientation + degree) % 360;
	    	 degree = (360 - degree) % 360;  // compensate the mirror
	     } else {  // back-facing
	    	 degree = (info.orientation - degree + 360) % 360;
	     }
	     return degree;
	 }
	
	/**
	 * 测试当前摄像头是否支持自动对焦
	 * @param parameters
	 * @return
	 */
	public static boolean isSupportAutoFocus(Parameters parameters)
	{
		List<String> focusModes = parameters.getSupportedFocusModes();
		for (int i = 0; i < focusModes.size(); i++)
		{
			if(focusModes.get(i).equals(Camera.Parameters.FOCUS_MODE_AUTO))
				return true;
		}
		return false;
	}
	
	/**
	 * 测试当前手机是否有前置摄像头
	 * @return
	 */
	@SuppressLint("NewApi")
	public static boolean hasFrontCamera() {
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		int cameraCount = Camera.getNumberOfCameras();
		for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				return true;
			}
		}
		return false;
	}
}
