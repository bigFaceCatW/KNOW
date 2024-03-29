package com.know.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类，不可再添加方法，此类供使用框架的系统覆盖，以实现其他系统的加密方式（包名、类名、方法名都要相同）
 * 
 * @author lmwang 创建时间：2014-7-2 下午4:53:51
 */
public class EncryptUtil {
	public static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	/**
	 * 默认实现为MD5加密
	 * 
	 * @return
	 */
	public static String doEncrypt(String plainText) {

		if (plainText != null) {
			String md5Str = "";
			try {
				// JDK 6 支持以下6种消息摘要算法，不区分大小写
				// md5,sha(sha-1),md2,sha-256,sha-384,sha-512
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(plainText.getBytes());
				byte b[] = md.digest();
				int i;
				StringBuilder builder = new StringBuilder(32);
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						{i += 256;}
					if (i < 16)
						{builder.append("0");}
					builder.append(Integer.toHexString(i));
				}
				md5Str = builder.toString();
				// LogUtil.println("result: " + buf.toString());// 32位的加密
			} catch (NoSuchAlgorithmException e) {
				logger.info(e.getMessage());
				//e.printStackTrace();
			}
			return md5Str;
		} else {
			return "";
		}

	}

	public static void main(String[] args) {
		System.out.println("after md5=" + doEncrypt("admin000000"));
	}

}
