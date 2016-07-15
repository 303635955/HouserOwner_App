package com.yunguo.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ZIPBitmap {
	
	public static InputStream compressImage(Bitmap image) {  
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
	        int options = 100;  
	        while ( baos.toByteArray().length / 1024>100) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
	            baos.reset();//����baos�����baos  
	            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
	            options -= 10;//ÿ�ζ�����10  
	        }  
        InputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��  
        try {
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return isBm;  
    } 
	
	
	
	public static InputStream getimage(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//��ʱ����bmΪ��  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
        float hh = 800f;//�������ø߶�Ϊ800f  
        float ww = 480f;//�������ÿ��Ϊ480f  
        //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
        int be = 1;//be=1��ʾ������  
        if (w > h && w > ww) {//�����ȴ�Ļ����ݿ�ȹ̶���С����  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//����߶ȸߵĻ����ݿ�ȹ̶���С����  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//�������ű���  
        //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);//ѹ���ñ�����С���ٽ�������ѹ��  
    }
	
	//����һ������ͼƬurl��ȡInputStream�ķ���  
    public static byte[] getBytes(InputStream is){  
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024]; // ������װ  
        int len = -1;  
        try {
			while ((len = is.read(buffer)) != -1) {  
			    outstream.write(buffer, 0, len);  
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
        try {
			outstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        // �ر���һ��Ҫ�ǵá�  
        return outstream.toByteArray();  
    }  
}
