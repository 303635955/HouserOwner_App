package com.IDCardReader.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yunguo.houserowner_app.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UpDateApk extends Activity{
	/** Called when the activity is first created. */
	public int newVerCode = -1;// �°汾��
	public ProgressDialog pd;
	//public String UPDATE_SERVERAPK = "ApkUpdateAndroid.apk";
	private Activity context;
	private String m_appNameStr;
	
	String verName;
	int verCode;
	/**
	 * ��ð汾��
	 */
	public static int getVerCode(MainActivity main) {
		int verCode = -1;
		try {
			verCode = main.getPackageManager().getPackageInfo(
					main.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e("�汾�Ż�ȡ�쳣", e.getMessage());
		}
		return verCode;
	}

	/**
	 * ��ð汾����
	 */
	public String getVerName(MainActivity main) {
		this.context=main;
		String verName = "";
		try {
			verName = main.getPackageManager().getPackageInfo(
					main.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e("�汾���ƻ�ȡ�쳣", e.getMessage());
		}
		return verName;
	}

	/**
	 * �ӷ������˻�ð汾����汾����
	 * 
	 * @return
	 */
	public String getServerVer(String paramStr) {
		String res = "";
		String params = "param=" + paramStr;
		URL url;
		try {
			byte[] data = params.getBytes();
			URLEncoder.encode(params,"GBK") ;
			url = new URL("http://120.25.65.125:8118/Client1/AppUpdate");
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
			httpConnection.setConnectTimeout(5000);
			
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			//httpConnection.connect();
			httpConnection.setRequestProperty("Content-Length", data.length + "");
			
			httpConnection.setDoOutput(true);
			OutputStream out = new DataOutputStream(httpConnection.getOutputStream());
			out.write(data);
			out.flush();
			out.close();
			
			StringBuffer strBuf = new StringBuffer();
			strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpConnection.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			String ress = strBuf.toString();
			res = URLDecoder.decode(ress, "UTF-8");
			reader.close();
			reader = null;
			/*InputStreamReader reader = new InputStreamReader(
					httpConnection.getInputStream());
			BufferedReader bReader = new BufferedReader(reader);
			String json = bReader.readLine();
			JSONArray array = new JSONArray(json);
			JSONObject jsonObj = array.getJSONObject(0);
			newVerCode = Integer.parseInt(jsonObj.getString("verCode"));
			newVerName = jsonObj.getString("verName");*/
		} /*catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}*/
		catch (Exception ex) {

        }
		return res;
	}

	
	/**
	 * �����°汾
	 */
	public void notNewVersionUpdate(final Context context) {
		int verCode = ((UpDateApk) context).getVerCode((MainActivity) context);
		String verName = ((UpDateApk) context).getVerName((MainActivity) context);
		StringBuffer sb = new StringBuffer();
		sb.append("��ǰ�汾��");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append("\n�������°汾���������");
		Dialog dialog = new AlertDialog.Builder(this).setTitle("�������")
				.setMessage(sb.toString())
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				}).create();
		dialog.show();
	}

	/**
	 * ���°汾
	 */
	public void doNewVersionUpdate(ProgressDialog m_progressDlg,final String url,String versionstr,final MainActivity mainActivity) {
		this.m_appNameStr ="yunguo_"+versionstr;//APP�ļ���
		
		m_progressDlg =  new ProgressDialog(mainActivity);
		m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// ����ProgressDialog �Ľ������Ƿ���ȷ false ���ǲ�����Ϊ����ȷ     
		m_progressDlg.setIndeterminate(false);
		
		this.pd=m_progressDlg;
		
		
		verCode = this.getVerCode(mainActivity);
		verName = this.getVerName(mainActivity);
		StringBuffer sb = new StringBuffer();
		sb.append("��ǰ�汾��");
		sb.append(verName);
		sb.append(",���ְ汾��");
		sb.append(versionstr);
		sb.append(",�Ƿ����");
		Dialog dialog = new AlertDialog.Builder(mainActivity)
				.setTitle("�������")
				.setMessage(sb.toString())
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						downFile(url,mainActivity);
					}
				})
				.setNegativeButton("�ݲ�����",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}
						}).create();
		// ��ʾ���¿�
		dialog.show();
	}

	
	
	/**
	 * ����apk
	 */
	public void downFile(final String url,final MainActivity mainActivity) {
		pd = new ProgressDialog(mainActivity);
		//pd.setTitle("��������");
		pd.show();  
	    new Thread() {  
	    	public void run() {  
	            HttpClient client = new DefaultHttpClient();  
	            HttpGet get = new HttpGet(url);  
	            HttpResponse response;  
	            try {  
	                response = client.execute(get);  
	                HttpEntity entity = response.getEntity();  
	                long length = entity.getContentLength();  
	                
	                pd.setMax((int)length);//���ý����������ֵ
	                //pd.setProgressNumberFormat("%1d kb/%2d kb");
	                InputStream is = entity.getContent();  
	                FileOutputStream fileOutputStream = null;  
	                if (is != null) {  
	                    File file = new File(  
	                            Environment.getExternalStorageDirectory(),  
	                            m_appNameStr);  
	                    fileOutputStream = new FileOutputStream(file);  
	                    byte[] buf = new byte[1024];  
	                    int ch = -1;  
	                    int count = 0;  
	                    while ((ch = is.read(buf)) != -1) {  
	                        fileOutputStream.write(buf, 0, ch);  
	                        count += ch;  
	                        if (length > 0) {  
	                        	pd.setProgress(count);
	                        }  
	                    }  
	                }
	                fileOutputStream.flush();  
	                if (fileOutputStream != null) {  
	                    fileOutputStream.close();  
	                }  
	                down();  //����HANDER�Ѿ���������ˣ����԰�װ��
	            } catch (ClientProtocolException e) {  
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }.start();  
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			pd.cancel();
			update();
		}
	};

	/**
	 * ������ɣ�ͨ��handler�����ضԻ���ȡ��
	 */
	public void down() {
		new Thread() {
			public void run() {
				Message message = handler.obtainMessage();
				handler.sendMessage(message);
			}
		}.start();
	}

	/**
	 * ��װӦ��
	 */
	public void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), m_appNameStr)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
		System.exit(0);
	}
}
