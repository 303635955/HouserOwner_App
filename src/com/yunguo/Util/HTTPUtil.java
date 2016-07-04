package com.yunguo.Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.util.Log;

public class HTTPUtil {
	/**
	 * Http Post ����
	 * 
	 * @param urlStr
	 *            url��ַ
	 * @param paramStr
	 *            ����
	 * @return
	 */
	static public String PostStringToUrl(String urlStr, String paramStr) {
		return PostStringToUrl(urlStr, paramStr, "param");
	}

	/**
	 * ��ָ�� URL ����POST����������
	 * 
	 * @param url
	 *            ��������� URL
	 * @param param
	 *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return ������Զ����Դ����Ӧ���
	 */
	static public String PostStringToUrl(String urlStr, String 	paramStr,
			String key) {
		System.out.println(urlStr + "========url");
		String res = "";
		String params = key + "=" + paramStr;
		HttpURLConnection conn = null;
		OutputStream out = null;
		InputStreamReader InputStreamReader = null;
		BufferedReader reader = null;
		try {
			System.out.println(params + "========params");
			byte[] data = params.getBytes();
			URLEncoder.encode(params, "GBK");
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", data.length + "");

			conn.setDoOutput(true);
			out = new DataOutputStream(conn.getOutputStream());
			out.write(data);
			out.flush();
			out.close();

			StringBuffer strBuf = new StringBuffer();
			strBuf = new StringBuffer();
			InputStreamReader = new InputStreamReader(conn.getInputStream());
			reader = new BufferedReader(InputStreamReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			String ress = strBuf.toString();
			res = URLDecoder.decode(ress, "UTF-8");
			InputStreamReader.close();
			reader.close();
			reader = null;
		} catch (Exception ex) {
			System.out.println(params + "========params");
		} finally {
			try {
				if(out!=null){
					out.flush();
					out.close();
				}
				if(InputStreamReader!=null){
					
					InputStreamReader.close();
					InputStreamReader = null;
				}
				if(reader!=null){
					reader.close();
					reader = null;
				}
				if(conn!=null){
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(urlStr + "��������==========" + res);
		return res;
	}

	static public String PostOwnerIdToUrl(String urlStr, int paramStr) {
		return PostOwnerIdToUrl(urlStr, paramStr, "param");
	}
	
	/**
	 * ��ָ�� URL ����POST����������
	 * 
	 * @param url
	 *            ��������� URL
	 * @param param
	 *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return ������Զ����Դ����Ӧ���
	 */
	static public String PostOwnerIdToUrl(String urlStr, int paramStr,
			String key) {
		System.out.println(urlStr + "========url");
		String res = "";
		String params = key + "=" + paramStr;
		HttpURLConnection conn = null;
		try {
			System.out.println(params + "========params");
			byte[] data = params.getBytes();
			URLEncoder.encode(params, "GBK");
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(60000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", data.length + "");

			conn.setDoOutput(true);
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			out.write(data);
			out.flush();
			out.close();

			StringBuffer strBuf = new StringBuffer();
			strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			String ress = strBuf.toString();
			res = URLDecoder.decode(ress, "UTF-8");
			reader.close();
			reader = null;
		} catch (Exception ex) {
			System.out.println(params + "========params");
		} finally {
		}
		System.out.println(urlStr + "��������==========" + res);
		return res;
	}
}
