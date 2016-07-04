package com.IDCardReader.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtility {

	//拷贝文件
    public static boolean copyFile(String fromUri, String toUri) 
    {
    	if (fromUri.equals(toUri))
    		return false;
    	
        try 
        {
            File fileFrom = new File(fromUri); 
            File fileTo = new File(toUri);
            
            if (fileTo.exists()) {
            	fileTo.delete();
			}
            
            //要求源文件是文件，要求源文件存在，目标文件不存在
            if (fileFrom.isFile() && fileFrom.exists() && !fileTo.exists()) 
            { 	
            	fileTo.createNewFile();
                InputStream in = new FileInputStream(fileFrom);
                OutputStream out = new FileOutputStream(fileTo);
                
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return true;
            }
        }   
        catch (Exception e) {   
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();   
        }
        
        return false;
    }
    
}
