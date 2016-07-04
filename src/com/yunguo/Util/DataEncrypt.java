/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yunguo.Util;

import android.annotation.SuppressLint;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
/**
 *
 * @author Administrator
 */
public class DataEncrypt {
	private final BASE64Encoder enc = new BASE64Encoder(); 
    /**
     * 瀵嗛挜
     */
    private final byte[] key = "&*$ygmjperson*^#".getBytes();
    /**
     * 鐭㈤噺
     */
    private final byte[]  iv = { (byte)0x12, (byte)0x34, (byte)0x56,(byte)0x78, (byte)0x90, (byte)0xAB, (byte)0xCD, (byte)0xEF };
    
    
    @SuppressLint("TrulyRandom")
	public String generateKey() {
        try {
            SecureRandom sr = new SecureRandom();
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            kg.init(sr);

            SecretKey secretKey = kg.generateKey();
            byte[] key = secretKey.getEncoded();
            return enc.encode(key);
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
        }
        return "";
   }
    /**
     * ecb鍔犲瘑
     * @param data
     * @return 
     */
    public String encrypt(String data) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
            byte encryptedData[] = cipher.doFinal(data.getBytes());
            return enc.encode(encryptedData);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return "";
    }
    /**
     * ecb瑙ｅ瘑
     * @param data
     * @return 
     */
    public String decrypt(String data) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
            byte decryptedData[] = cipher.doFinal(data.getBytes());
            return enc.encode(decryptedData);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return "";
    }
    
    /**
     * cbc鍔犲瘑
     * @param data
     * @return 
     */
    public String CBCEncrypt(String data) {

        try {
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 鑻ラ噰鐢∟oPadding妯″紡锛宒ata闀垮害蹇呴』鏄�8鐨勫�嶆暟
            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
            IvParameterSpec param = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);
            byte encryptedData[] = cipher.doFinal(data.getBytes());

            return  enc.encode(encryptedData);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return "";
    }
    /**
     * cbc瑙ｅ瘑
     * @param data
     * @return 
     */
    public String CBCDecrypt(byte[] data) {
        try {
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 鑻ラ噰鐢∟oPadding妯″紡锛宒ata闀垮害蹇呴』鏄�8鐨勫�嶆暟
            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
            IvParameterSpec param = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, param);
            byte decryptedData[] = cipher.doFinal(data);

            return  enc.encode(decryptedData);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return "";
    }

}
