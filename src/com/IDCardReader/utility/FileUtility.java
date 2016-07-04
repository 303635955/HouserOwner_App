package com.IDCardReader.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtility {

	//�����ļ�
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
            
            //Ҫ��Դ�ļ����ļ���Ҫ��Դ�ļ����ڣ�Ŀ���ļ�������
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
            System.out.println("���Ƶ����ļ���������");
            e.printStackTrace();   
        }
        
        return false;
    }
    
}
