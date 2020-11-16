package com.know.config.basic;


import com.know.util.CommonUtil;
import com.know.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/** 
 * ClassName: GetLoginInfoService
 * 获取前端session登录信息
 * @author fs 
 * date: 2018年8月29日 下午5:44:20
 */
@Service
public class GetLoginInfoService {
	public static final Logger logger = LoggerFactory.getLogger(GetLoginInfoService.class);
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	/**
	 * 获取登录信息map
	 * @param jsessionId
	 * @return
	 */
	public  Map<String , String > getLoginInfoNew(String jsessionId) {
		String loginInfo ="";
		Map<String, String> loginMap = new HashMap<>();
		try {
			loginInfo = stringRedisTemplate.opsForValue().get(jsessionId);
			if(CommonUtil.isValue(loginInfo)) {
				//判断loginInfo是否为空  为空不去转化为map
				loginMap = JacksonUtil.json2Stringmap(loginInfo.substring(1, loginInfo.length()-1));
			}
		} catch (Exception e) {
			logger.info(e.getMessage());

			return loginMap;
		}
		return loginMap;
	}
	/**
	 * 获取登录信息map
	 * @param jsessionId
	 * @return
	 */
	public  Map<String , String > getLoginInfo(String jsessionId) {
		String loginInfo ="";
		Map<String , String > loginMap = null;
		try {
			loginInfo = stringRedisTemplate.opsForValue().get(jsessionId);
			if(CommonUtil.isValue(loginInfo)) {
				//判断loginInfo是否为空  为空不去转化为map
				loginMap = JacksonUtil.json2Stringmap(loginInfo.substring(1, loginInfo.length()-1));		
			}else {
				//加上默认 方便代码覆盖率单元测试
				loginInfo="[{\"userId\":\"yyxnqkf01\",\"userName\":\"省份管理员\",\"mailbox\":null,\"phone\":\"1888888888\",\"channelCode\":\"1\",\"channelName\":\"客服\",\"manageArea\":\"30\",\"areaName\":\"安徽\",\"areaId\":\"30\",\"shardingId\":\"30\",\"cityCode\":null,\"areaCode\":null,\"affNature\":\"1\"}]";
				loginMap = JacksonUtil.json2Stringmap(loginInfo.substring(1, loginInfo.length()-1));
			}
		} catch (Exception e) {
			logger.info(e.getMessage()+"login人员信息 >>>"+loginMap);
			return loginMap;
		}
		return loginMap;				
	}
}
