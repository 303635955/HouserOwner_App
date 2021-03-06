package com.yunguo.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

public class UpAPPVersion {
	private Handler m_mainHandler = new Handler();
	private ProgressDialog m_progressDlg;
	private String m_appNameStr;
	private Context context;
	private String url;
	
	public UpAPPVersion(ProgressDialog m_progressDlg,String url,String versionstr,Context context){
		this.m_progressDlg = m_progressDlg;
		this.url = url;
		this.context = context;
		this.m_appNameStr ="yunguo_"+versionstr;//APP鏂囦欢鍚� 
	}
	  @SuppressWarnings("unused")
			public  void downFile()
			{
				m_progressDlg.show();  
			    new Thread() {  
			        public void run() {  
			            HttpClient client = new DefaultHttpClient();  
			            HttpGet get = new HttpGet(url);  
			            HttpResponse response;  
			            try {  
			                response = client.execute(get);  
			                HttpEntity entity = response.getEntity();  
			                long length = entity.getContentLength();  
			                
			                m_progressDlg.setMax((int)length);//璁剧疆杩涘害鏉＄殑鏈�澶у��
			                
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
			                        	 m_progressDlg.setProgress(count);
			                        }  
			                    }  
			                }  
			                fileOutputStream.flush();  
			                if (fileOutputStream != null) {  
			                    fileOutputStream.close();  
			                }  
			                down();  //鍛婅瘔HANDER宸茬粡涓嬭浇瀹屾垚浜嗭紝鍙互瀹夎浜�
			            } catch (ClientProtocolException e) {  
			                e.printStackTrace();  
			            } catch (IOException e) {  
			                e.printStackTrace();  
			            }  
			        }  
			    }.start();  
			}
		    
		    
		    /**
			 * 鍛婅瘔HANDER宸茬粡涓嬭浇瀹屾垚浜嗭紝鍙互瀹夎浜�
			 */
			private void down() {
		        m_mainHandler.post(new Runnable() {
		            public void run() {
		                m_progressDlg.cancel();
		                update();
		            }
		        });
		}
			/**
			 * 瀹夎绋嬪簭
			 */
		    void update() {
		        Intent intent = new Intent(Intent.ACTION_VIEW);
		        intent.setDataAndType(Uri.fromFile(new File(Environment
		                .getExternalStorageDirectory(), m_appNameStr)),
		                "application/vnd.android.package-archive");
		        context.startActivity(intent);
		        System.exit(0);
		    }
}
