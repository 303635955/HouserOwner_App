package com.yunguo.Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

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
	
	public static  String uploadFile(String filepath,String RequestURL,Map<String, String> param){  
        String result = "";  
        String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成  
        String PREFIX = "--" , LINE_END = "\r\n";  
        String CONTENT_TYPE = "multipart/form-data";   //内容类型  
        try {  
            URL url = new URL(RequestURL);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setReadTimeout(5000);  
            conn.setConnectTimeout(5000);  
            conn.setDoInput(true);  //允许输入流  
            conn.setDoOutput(true); //允许输出流  
            conn.setUseCaches(false);  //不允许使用缓存  
            conn.setRequestMethod("POST");  //请求方式  
            conn.setRequestProperty("Charset", "utf-8");  //设置编码  
            conn.setRequestProperty("connection", "keep-alive");  
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);  
            if(filepath!=null && !filepath.equals("")){  
                /** 
                 * 当文件不为空，把文件包装并且上传 
                 */  
                DataOutputStream dos = new DataOutputStream( conn.getOutputStream());  
                StringBuffer sb = new StringBuffer();  
                  
                String params = "";    
                if (param != null && param.size() > 0) {    
                    Iterator<String> it = param.keySet().iterator();    
                    while (it.hasNext()) {    
                        sb = null;    
                        sb = new StringBuffer();    
                        String key = it.next();    
                        String value = param.get(key);    
                        sb.append(PREFIX).append(BOUNDARY).append(LINE_END);    
                        sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);    
                        sb.append(value).append(LINE_END);    
                        params = sb.toString();    
                        Log.i("上传数据 ====", key+"="+params+"##");    
                        dos.write(params.getBytes());    
//                      dos.flush();    
                    }    
                }    
                sb = new StringBuffer();  
                sb.append(PREFIX);  
                sb.append(BOUNDARY);  
                sb.append(LINE_END);  
                /** 
                 * 这里重点注意： 
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件 
                 * filename是文件的名字，包含后缀名的   比如:abc.png 
                 */  
                sb.append("Content-Disposition: form-data; name=\"imgfile\";filename=\""+new File(filepath).getName()+"\""+LINE_END);  
                sb.append("Content-Type: image/pjpeg; charset="+"utf-8"+LINE_END);  
                sb.append(LINE_END);  
                dos.write(sb.toString().getBytes());  
                
                InputStream is = ZIPBitmap.getimage(filepath);  
                byte[] bytes = new byte[1024];  
                int len = 0;  
                while((len=is.read(bytes))!=-1){  
                    dos.write(bytes, 0, len);  
                }
                is.close();  
                dos.write(LINE_END.getBytes());  
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();  
                dos.write(end_data);  
                  
                dos.flush();  
                /** 
                 * 获取响应码  200=成功 
                 * 当响应成功，获取响应的流 
                 */  
  
                int res = conn.getResponseCode();  
                System.out.println("res========="+res);  
                if(res==200){  
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
        					conn.getInputStream()));
                    StringBuffer strBuf = new StringBuffer();
                    String line = null;
        			while ((line = reader.readLine()) != null) {
        				strBuf.append(line).append("\n");
        			}
        			String ress = strBuf.toString();
        			result = URLDecoder.decode(ress, "UTF-8");
        			
        			reader.close();
        			reader = null;
//                 // 移除进度框  
//                  removeProgressDialog();  
                }else{
                	result = "";
                }  
            }  
        }catch (Exception e) {  
            e.printStackTrace();  
            return result;  
        }  
        System.out.println("返回的数据为："+result);
        return result;  
    }
	
	
	
}
