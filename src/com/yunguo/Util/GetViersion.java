package com.yunguo.Util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class GetViersion {
		/** 
	    * ��ȡ�汾���� 
	    * @param context 
	    * @return 
	    */  
	    public static String getVerName(Context context) {  
	        String verName = "";  
	        try {  
	            PackageManager pm = context.getPackageManager();  
	            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
	            verName = pi.versionName;    
	        } catch (NameNotFoundException e) {  
	            Log.e("msg",e.getMessage());  
	        }  
	        return verName;     
	}
}
