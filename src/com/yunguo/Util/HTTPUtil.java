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
	 * Http Post 请求
	 * 
	 * @param urlStr
	 *            url地址
	 * @param paramStr
	 *            参数
	 * @return
	 */
	static public String PostStringToUrl(String urlStr, String paramStr) {
		return PostStringToUrl(urlStr, paramStr, "param");
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	static public String PostStringToUrl(String urlStr, String paramStr,
			String key) {
		System.out.println(urlStr + "========url");
		String res = "";
		String params = key + "=" + paramStr;
		try {
			System.out.println(params + "========params");
			byte[] data = params.getBytes();
			URLEncoder.encode(params, "GBK");
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
		System.out.println(urlStr + "返回数据==========" + res);
		return res;
	}

	static public String PostOwnerIdToUrl(String urlStr, int paramStr) {
		return PostOwnerIdToUrl(urlStr, paramStr, "param");
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	static public String PostOwnerIdToUrl(String urlStr, int paramStr,
			String key) {
		System.out.println(urlStr + "========url");
		String res = "";
		String params = key + "=" + paramStr;
		try {
			System.out.println(params + "========params");
			byte[] data = params.getBytes();
			URLEncoder.encode(params, "GBK");
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
		System.out.println(urlStr + "返回数据==========" + res);
		return res;
	}
}
