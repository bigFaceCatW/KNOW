package com.know.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
   
/**
 * AES 对称加密解密
 * @author lx.ci
 *
 *  配合使用的JS前台加密方法，保证前后台使用的key和iv一致
 *   <script src="./js/CryptoJS-v3.1.2/rollups/aes.js" type="text/javascript"></script>
 *   
 *	function Encrypt(data,key,iv){  
			var key  = CryptoJS.enc.Utf8.parse(key);  
            var iv   = CryptoJS.enc.Utf8.parse(iv);  
            var encrypted =CryptoJS.AES.encrypt(data,key,  
                    {  
                       iv:iv,  
                       mode:CryptoJS.mode.CBC,  
                       padding:CryptoJS.pad.Pkcs7  
                    });  
            return encrypted.toString();    //返回的是base64格式的密文 
	} 
 
	
	function Decrypt(encrypted,key,iv){//解密  
            var key  = CryptoJS.enc.Utf8.parse(key);  
            var iv   = CryptoJS.enc.Utf8.parse(iv);  
            var decrypted =CryptoJS.AES.decrypt(encrypted,key,  
                    {  
                       iv:iv,  
                       mode:CryptoJS.mode.CBC,  
                       padding:CryptoJS.pad.Pkcs7  
                    });  
            return decrypted.toString(CryptoJS.enc.Utf8);      //  
        }  

 */

public class CryptoHelper {
	public static final  Logger logger = LoggerFactory.getLogger(CryptoHelper.class);
    /**
     * 加密，将字符串加密成BASE64格式的字符串密文
     * @param content 待加密内容
     * @param sKey 加密秘钥
     * @param siv  加密偏移量
     * @return  返回BASE64格式的密文
     * @throws Exception
     */
    public static String encrypt(String content, String sKey,String siv) throws Exception {    
        try {    
            // 判断Key是否正确    
            if (sKey == null) {    
                System.out.print("Key为空null");      
                return null;    
            }    
            // 判断Key是否为16位    
            if (sKey.length() != 16) {    
                System.out.print("Key长度不是16位");    
                return null;    
            }    
            byte[] raw = sKey.getBytes("ASCII");    
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");    
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");    
            IvParameterSpec iv = new IvParameterSpec(siv.getBytes());    
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);    
            byte[] encrypted1 = content.getBytes();
            
            byte[] original = cipher.doFinal(encrypted1);
            String originalString =  Base64.encodeBase64String(original);
            return originalString;     
        } catch (Exception e) {    
        	logger.info(e.getMessage());
            System.out.println(e.toString());    
            return null;    
        }    
    }    
    
    
    /**
     * AES 解密
     * @param content 待解密的BASE64位密文
     * @param sKey 秘钥 长度为16位
     * @param siv  偏移量 长度16位，建议和秘钥值相同
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String sKey,String siv) throws Exception {    
        try {    
            // 判断Key是否正确    
            if (sKey == null) {    
                System.out.print("Key为空null");      
                return null;    
            }    
            // 判断Key是否为16位    
            if (sKey.length() != 16) {    
                System.out.print("Key长度不是16位");    
                return null;    
            }    
            byte[] raw = sKey.getBytes("ASCII");    
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");    
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");    
            IvParameterSpec iv = new IvParameterSpec(siv.getBytes());    
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);    
            byte[] encrypted = Base64.decodeBase64(content);//先用BAES64解密成二进制字节数组 
            
            byte[] original = cipher.doFinal(encrypted);
            String originalString = new String(original); 
            return originalString;     
        } catch (Exception ex) {  
        	logger.info(ex.getMessage());
            System.out.println(ex.toString());    
            return null;    
        }    
    }    
    

    
    /**将二进制转换成16进制 
     * @param buf 
     * @return 
     */  
    public static String parseByte2HexStr(byte buf[]) {  
            StringBuffer sb = new StringBuffer();  
            for (int i = 0; i < buf.length; i++) {  
                    String hex = Integer.toHexString(buf[i] & 0xFF);  
                    if (hex.length() == 1) {  
                            hex = '0' + hex;  
                    }  
                    sb.append(hex.toUpperCase());  
            }  
            return sb.toString();  
    }  
 
    
    /**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
            if (hexStr.length() < 1)  
            { return null;  }
            byte[] result = new byte[hexStr.length()/2];  
            for (int i = 0;i< hexStr.length()/2; i++) {  
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                    result[i] = (byte) (high * 16 + low);  
            }  
            return result;  
    }  
    
    
    public static void main(String[] args) throws Exception {
   
  //  	my.Decrypt("iJ8ZC4hDHLOHU6swpqpjpA==","abcdefgabcdefg12","abcdefgabcdefg12");
    	String str = CryptoHelper.encrypt("12345678", "abcdefgabcdefg12","abcdefgabcdefg12");
    	System.out.println("加密："+str);
    	String str1 = CryptoHelper.decrypt(str, "abcdefgabcdefg12","abcdefgabcdefg12");
    	System.out.println("解密："+str1);
    	  
    }
  
}