package com.IDCardReader.idcardreader.lib;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.IDCardReader.idcardreader.lib.IDCardPostRequest.IDCardCommands;
import com.IDCardReader.utility.ImageProcess;

public class IDCardRecog {
	
	private final int MaxImageSize = 1920*1080;
	private final int MaxIDCardSide = 1280;
	
	private String apixKey;
	private Handler handler;
	
	
	
	public IDCardRecog(String apixKey, Handler handler) {
		this.apixKey = apixKey;
		this.handler = handler;
	}
	
	public void recogFront(String imgPath) {
		final String path= imgPath;
		final String picType = "jpg";
		final IDCardCommands cmd = IDCardCommands.IDCardPhotoFront;
		
		Thread mThread = new Thread(new Runnable(){
			@Override
	        public void run() {
				ByteArrayOutputStream img_out = ImageProcess.getImageOutJpeg(path, MaxImageSize, MaxIDCardSide);
				IDCardPostRequest pRequest = new IDCardPostRequest(cmd, apixKey);
				
				pRequest.initConetent(img_out, picType);
				pRequest.sendRequest();
				
				Bundle bundle = new Bundle();
				int res = pRequest.analyzeIDCardFrontResult(bundle);
				if (res == 1){
					sendMessage(1, bundle);
				}
				else{
					sendMessage(0, bundle);
				}
	        }
		});
		
    	mThread.setPriority(Thread.MAX_PRIORITY);
		mThread.start();
	}
	
	public void recogBack(String imgPath) {
		final String path= imgPath;
		final String picType = "jpg";
		final IDCardCommands cmd = IDCardCommands.IDCardPhotoBack;
		Thread mThread = new Thread(new Runnable(){
			@Override
	        public void run() {
				ByteArrayOutputStream img_out = ImageProcess.getImageOutJpeg(path, MaxImageSize, MaxIDCardSide);
				IDCardPostRequest pRequest = new IDCardPostRequest(cmd, apixKey);
				
				pRequest.initConetent(img_out, picType);
				pRequest.sendRequest();
				
				Bundle bundle = new Bundle();
				int res = pRequest.analyzeIDCardBackResult(bundle);
				if (res == 1){
					sendMessage(2, bundle);
				}
				else{
					sendMessage(0, bundle);
				}
	        }
		});
    	
    	mThread.setPriority(Thread.MAX_PRIORITY);
		mThread.start(); 
	}

	private void sendMessage(int result, Bundle data) {
		Message msg = new Message();
		msg.what = result;
		msg.setData(data);
		handler.sendMessage(msg);
	}
}
