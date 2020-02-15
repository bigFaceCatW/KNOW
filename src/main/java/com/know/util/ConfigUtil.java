package com.know.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取classpath下面config.properties文件的工具类
 * @author lmwang
 * 创建时间：2014-7-14 上午10:16:15
 */
public class ConfigUtil {
	public static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	// 初始化配置文件
		private static String[] configfile = { "config.properties","localconfig.properties"};
		private static Map<String, Properties> configmp = new HashMap<String, Properties>();
		static {
			for(int i=1;i<=2;i++){//循环1处理localconfig.properties，循环2处理1中指定的配置文件
				for (String cfgfile : configfile) {
					if(i==2 && "localconfig.properties".equals(cfgfile)){//循环2不再处理localconfig.properties，循环1中已处理
						continue;
					}
					// Resource resource = new ClassPathResource("config.properties");
					Resource resource = new ClassPathResource(cfgfile);
					InputStreamReader ips = null;
					try {
						ips = new InputStreamReader(resource.getInputStream(), "UTF-8");
						// ips = resource.getInputStream();
						Properties pro = new Properties();
						pro.load(ips);
						configmp.put(cfgfile, pro);
						if("localconfig.properties".equals(cfgfile)){
							configfile =  pro.getProperty("configfiles").replaceAll(" ", "").trim().split(",");
						}
					} catch (IOException e) {
						logger.info(e.getMessage());
						//e.printStackTrace();
					} finally {// lmwang 20131223关闭输入流
						try {
							if (ips != null) {
								ips.close();
							}
						} catch (IOException e) {
							logger.info(e.getMessage());
						//	e.printStackTrace();
						}
					}
				}
			}
		}

		// 获取配置文件的中配置的值
		public static String getValue(String key) {// 按照配置文件顺序读取配置，读到则返回，否则读取下一个配置文件
			for (String cfgfile : configfile) {
				try {
					return configmp.get(cfgfile).getProperty(key).trim();
				} catch (Exception e) {
					logger.info(e.getMessage());
					continue;
				}
			}
			System.out.println("error：获取配置信息失败，没有配置文件包含此配置信息：" + key);
			return "";
		}

		// 获取指定配置文件的中配置的值
		public static String getFileValue(String filename, String key) {// 按照配置文件顺序读取配置，读到则返回，否则读取下一个配置文件
			return configmp.get(filename).getProperty(key).trim();
		}

		// 获取指定配置文件的所有内容
		public static Properties getFile(String filename) {
			return configmp.get(filename);
		}
}
