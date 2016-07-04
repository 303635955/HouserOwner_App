package com.IDCardReader.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.R.integer;
import android.R.string;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.widget.Toast;

public class ImageProcess {

	public static int saveBitmap2JpegWithResize(Bitmap bmp, float scale, String path, int quality)
	{
		Bitmap rzBmp = resizeImage(bmp, scale);
		return saveBitmapToImage(rzBmp, path, quality, Bitmap.CompressFormat.JPEG);
	}
	
	public static int saveBitmapToImage(Bitmap bmp, String path, int quality, Bitmap.CompressFormat format)
	{
		long time1 = System.currentTimeMillis();
		
		//����������ѡ��ͼƬ
		File f = new File(path);
		if (f.exists())
			f.delete();
		
		try {
			FileOutputStream out = new FileOutputStream(f);
			bmp.compress(format, quality, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		long time2 = System.currentTimeMillis();
		return (int)(time2-time1);
	}
	
	//����ͼ�񣬰�����ת�����ŵ����ʳߴ�
	public static Bitmap resizeImage(Bitmap picBitmap, float scale)
    {
		if (scale == 1.0) {
			return picBitmap;
		}
		
    	int imgWidth = picBitmap.getWidth(), imgHeight = picBitmap.getHeight();
    	Matrix matrix = new Matrix();
		matrix.postScale(scale, scale); //���Ϳ�Ŵ���С�ı���
		return Bitmap.createBitmap(picBitmap, 0, 0, imgWidth, imgHeight, matrix, true);
    }
	
	public static Bitmap extractThumbNail(final String path, final int maxSize)
	{	
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap tmp = BitmapFactory.decodeFile(path, options);
		if (tmp != null) {
			tmp.recycle();
			tmp = null;
		}
		
		// NOTE: out of memory error
		if (options.inSampleSize <= 1) {
			options.inSampleSize = 1;
		}
		while (options.outHeight * options.outWidth / options.inSampleSize / options.inSampleSize > maxSize) {
			options.inSampleSize++;
		}
		
		//options.inSampleSizeֻ����1,2,4...���3��ôҲΪ2
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}
	
	public static ByteArrayOutputStream getImageOutJpeg (String path, int maxSize, int maxEdge) {
		Bitmap bmp = extractThumbNail(path, maxSize);
		if (bmp == null) {
			return null;
		}
		
		int width = bmp.getWidth(), height = bmp.getHeight();
		float scale = 1.0f;
		
		if (Math.max(width, height) > maxEdge) {
			scale = (float)maxEdge/Math.max(width, height);
			Bitmap resizeBmp = resizeImage(bmp, scale);
			bmp.recycle();
			bmp = resizeBmp;
		}
		
		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 80, boas);
		
		return boas;
	}
	
	public static ByteArrayOutputStream getOutImage(String filePath) {
		try {
			FileInputStream  fis = new FileInputStream(new File(filePath));
	        ByteArrayOutputStream img_out = new ByteArrayOutputStream();
	        byte[] buffer = new byte[128];
	        int iLength = 0;
	        while((iLength = fis.read(buffer)) != -1) {
	        	img_out.write(buffer, 0, iLength);
	        }
	        fis.close();
	        img_out.close();
	        
	        return img_out;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	} 
}
