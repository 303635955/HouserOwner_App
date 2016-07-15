package com.yunguo.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FromUtil {
	/**
	 * 
	 * @param begin 开始时间
	 * @param end    结束时间
	 * @return
	 */
	public static boolean date(String begin,String end){
		boolean t = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date datebegin = sdf.parse(begin);
			Date dateend = sdf.parse(end);
			t = datebegin.before(dateend);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
}
