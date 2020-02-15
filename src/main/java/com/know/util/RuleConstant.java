package com.know.util;

/**
 * Demo class
 * 
 * @author test
 * @date 2018/10/31
 */

public class RuleConstant {
	/**
	 * 规则缓存名称
	 */
	public static final String CACHE_NAME_RULE = "ruleCache";
	/**
	 * 规则包部署路径
	 */
	public static final String RULE_PKG_DEPLOY_URL = "/sys/rule/ruleconfig/deployRulePkg.refresh";
	
	//清除缓存url
	public static final String CACHE_CONTROLLER_URL = "/cacheMgr/cacheClear.refresh";
	//获取缓存url
	public static final String CACHE_GET_CONTROLLER_URL = "/cacheMgr/getCacheData.refresh";
	
	/**
	 * 规则结果类型：规则
	 */
	public static final String RULE_VALUE_TYPE_RULE = "RULE_PACKAGE";
	
	/**
	 * 默认的shardingId取值
	 */
	public static final Long DEFALUT_SHARDING_ID = Long.parseLong(ConfigUtil.getValue("shardingId"));
	/**
	 * 规则部署超时时间（毫秒）
	 */
	public static final int RULE_DEPLOY_TIME_OUT = 10000;
	/**
	 * 规则全局对象ValueNode路径
	 */
	public static final String  VALUE_NODE_PKG= "com.usi.cmos.common.rule.dto.ValueNode";
}
