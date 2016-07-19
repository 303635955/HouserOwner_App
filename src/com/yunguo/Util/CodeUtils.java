package com.yunguo.Util;

import java.util.Random;

public class CodeUtils {
	private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private static final int DEFAULT_CODE_LENGTH = 4;  
    private static Random random = new Random();  
    private static int codeLength = DEFAULT_CODE_LENGTH;
    private static String code = "";
    
    /**
     * 返回随机验证码
     * @return
     */
    public static  String createCode() {  
        StringBuilder buffer = new StringBuilder();  
        for (int i = 0; i < codeLength; i++) {  
            buffer.append(CHARS[random.nextInt(CHARS.length)]);  
        }  
        
        code = buffer.toString();
        
        return buffer.toString();  
    }
    
    
    public static String getcode(){
    	return code;
    }
}
