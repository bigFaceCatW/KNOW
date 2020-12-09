package com.know.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Demo class
 * 
 * @author Test
 * @date 2018/10/31
 */
public class Md5Util {
	public static final Logger logger = LoggerFactory.getLogger(Md5Util.class);
	/**
	 * MD5加密
	 * @param plainText
	 */
	public static String Md5(String plainText) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					{i += 256;}
				if (i < 16)
					{buf.append("0");}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.info(e.getMessage());
			return null;
		}
	}

}
