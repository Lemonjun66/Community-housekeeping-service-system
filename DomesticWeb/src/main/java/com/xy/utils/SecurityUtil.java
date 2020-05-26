package com.xy.utils;

import java.security.MessageDigest;

/**
 *  *
 * <p>
 * 公共方法类
 * </p>
 *  *
 * <p>
 * md5算法实现
 * </p>
 *  *  * @version 1.0  *  
 */
public class SecurityUtil {

	public static String MD5(String value) {
		try {
			//返回实现指定摘要算法的 MessageDigest对象"MD5"
			MessageDigest md = MessageDigest.getInstance("MD5");
			//使用指定的字节更新摘要
			md.update(value.getBytes("UTF8"));
			byte s[] = md.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString( (0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
			}
			return result;
		} catch(Exception e) {
			return null;
		}
 }
}