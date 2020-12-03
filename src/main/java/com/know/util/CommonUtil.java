package com.know.util;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;



/**
 * 常用工具类
 * @author fan.fan
 * @date 2014-3-31 上午11:18:45
 */
public class CommonUtil {
	public static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	/**
     * 验证一个字符串是否有值(既不是null,也不是空字符串)
     * @param value
     * @return
     */
    public static final boolean isValue(String value) {
    	return value != null && value.trim().length() > 0;
    }
    
    /**
     * 将字符串按格式，格式化成日期（采用阿帕奇的工具类，性能好，线程安全）
     * @param stringValue 字符串日期
     * @param formatPattern 要格式化成的日期
     * @return
     */
	public static final Date parse(String stringValue, String formatPattern) {
		if(stringValue==null || formatPattern==null) {
			return null;
		}
		Date finalDate = null;
		try {
			finalDate = DateUtils.parseDate(stringValue, new String [] {formatPattern});
		} catch (ParseException e) {
			logger.info(e.getMessage());
			//e.printStackTrace();
		}
		return finalDate;
	}
	
    /**
     * 格式化日期（采用阿帕奇的工具类，性能好，线程安全）
     * @param date 日期
     * @param formatPattern 要格式化的形式
     * @return
     */
    public static final String format(Date date, String formatPattern) {
		if (date == null) {
			return "";
		}
		return DateFormatUtils.format(date, formatPattern);
	}
    
    /**
	 * 将字符串类型的多个查询条件转为带单引号连接的字符串。如："a,b,c" 转为 "'a','b','c'"
	 * @param cond
	 */
	public static final String transCondWidthQuotation(String cond){
		String[] arr=cond.split(",");
		for(int i=0;i<arr.length;i++){
			arr[i]=new StringBuffer("'").append(arr[i]).append("'").toString();
		}
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<arr.length;i++){
			if(sb.length()==0){
				sb.append(arr[i]);
			}else{
				sb.append(",").append(arr[i]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 传入一个形如a.b.c.d......的字符串
	 * @param str 需要转换的字符串
	 * @param demiter 分割符号
	 * @param beginIndex 开始截取位置
	 * @param changeDemiter 转换后的分割符号
	 * @return 形如b-->c-->d.......的字符串
	 */
	public static final String changeString(String str , String demiter , int beginIndex , String  changeDemiter){
		String[] arr=str.split(demiter);
		StringBuffer change=new StringBuffer();
		if(arr.length>3){
			for(int i=beginIndex;i<arr.length;i++){
				if(i==arr.length-1){
					change.append( arr[i] );
				}else{
					change.append( arr[i]+changeDemiter );
				}
			}
			return change.toString();	
		}else{
			return change.append( arr[arr.length-1] ).toString();
		}
	}
	

    
}
