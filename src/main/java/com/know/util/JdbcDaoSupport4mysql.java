package com.know.util;


import com.know.config.dto.PageObj;
import net.sf.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mysql dao的父类
 * @author chen.kui
 * @date 2014年9月28日15:26:57
 */
@Repository
public class JdbcDaoSupport4mysql{
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
	 * 为当前的DAO返回 JdbcTemplate
	 */
	public final JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * sql执行  分页
	 * 2015-12-2 
	 */
	public Map<String, Object> queryByPage(String sql,ArrayList<Object> paralist,PageObj pageObj){
		Map<String, Object> modelMap = new HashMap<String, Object>(16);
		List<Object> list =this.queryByPage(sql, new RowMapper<Object>(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				JSONObject row = new JSONObject();  
				 ResultSetMetaData rowdata =rs.getMetaData();
				 for(int i = 1 ; i<= rowdata.getColumnCount() ; i++){
					 row.put(rowdata.getColumnLabel(i), rs.getObject(i)==null?"":rs.getObject(i));
				 }
				return row;
			}
		},pageObj,paralist.toArray());
		modelMap.put("pageObj", pageObj);
		modelMap.put("list", list);
		return modelMap;
	}
	
	/**
	 * 通用查询 返回list  JSONObject对象
	 * @param sql
	 * @param paralist
	 * @return
	 * 2015-12-2 
	 */
	public List<Object> queryForList(String sql,ArrayList<Object> paralist){
		return this.getJdbcTemplate().query(sql, paralist.toArray(),new RowMapper<Object>(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				JSONObject row = new JSONObject();  
				 ResultSetMetaData rowdata =rs.getMetaData();
				 for(int i = 1 ; i<= rowdata.getColumnCount() ; i++){
					 row.put(rowdata.getColumnLabel(i), rs.getObject(i)==null?"":rs.getObject(i));
				 }
				return row;
			}
		});
	}
	
//	public List<Map<String, Object>> queryByPageForList(String sql, PageObj pageObj, Object... params) {
//		int startIndex = (pageObj.getPage()-1) * pageObj.getRows();
//		pageObj.setTotal(this.getJdbcTemplate().queryForObject("SELECT COUNT(*) TOTAL FROM ("+sql+")PAGE_", params, Integer.class));
//		String pageSql = "SELECT PAGE_.* FROM("+sql+" )PAGE_ LIMIT "+startIndex+","+pageObj.getRows();
//		return this.jdbcTemplate.queryForList(pageSql, params);
//	}
//	
//	public List<Map<String, Object>> queryByPageForList(String sql, PageObj pageObj) {
//		int startIndex = (pageObj.getPage()-1) * pageObj.getRows();
//		pageObj.setTotal(this.getJdbcTemplate().queryForObject("SELECT COUNT(*) TOTAL FROM ("+sql+")PAGE_", Integer.class));
//		String pageSql = "SELECT PAGE_.* FROM("+sql+" )PAGE_ LIMIT "+startIndex+","+pageObj.getRows();
//		return this.jdbcTemplate.queryForList(pageSql);
//	}
	
	/**
	 * 通用的分页查询
	 * @param sql
	 * @param rowMapper
	 * @param pageObj
	 * @return
	 */
	public <T> List<T> queryByPage(String sql, Object[] params, RowMapper<T> rowMapper, PageObj pageObj) {
		StringBuffer sb3 = new StringBuffer();
		StringBuffer sb03 = new StringBuffer();
		int startIndex = (pageObj.getPage()-1) * pageObj.getRows();
		sb03.append("SELECT COUNT(*) TOTAL FROM ("+sql+") PAGE_");
		pageObj.setTotal(this.getJdbcTemplate().queryForObject(sb03.toString(), params, Integer.class));
		String pageSql = "SELECT PAGE_.* FROM("+sql+" )PAGE_ LIMIT "+startIndex+","+pageObj.getRows();
		sb3.append(pageSql);
		return this.jdbcTemplate.query(sb3.toString(), params, rowMapper);
	}
	
	/**
	 * 通用的分页查询
	 * @param sql
	 * @param rowMapper
	 * @param pageObj
	 * @return
	 */
	public <T> List<T> queryByPage(String sql, RowMapper<T> rowMapper, PageObj pageObj) {
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb02 = new StringBuffer();
		sb02.append("SELECT COUNT(*) TOTAL FROM ("+sql+")PAGE_");
		int startIndex = (pageObj.getPage()-1) * pageObj.getRows();
		pageObj.setTotal(this.getJdbcTemplate().queryForObject(sb02.toString(), Integer.class));
		String pageSql = "SELECT PAGE_.* FROM("+sql+" )PAGE_ LIMIT "+startIndex+","+pageObj.getRows();
		sb2.append(pageSql);
		return this.jdbcTemplate.query(sb2.toString(), rowMapper);
	}
	
	public <T> List<T> queryByPage(String sql, RowMapper<T> rowMapper, PageObj pageObj, Object... params) {
		StringBuffer sb01 = new StringBuffer();
		sb01.append("SELECT COUNT(*) TOTAL FROM ("+sql+")PAGE_");
		int startIndex = (pageObj.getPage()-1) * pageObj.getRows();
		pageObj.setTotal(this.getJdbcTemplate().queryForObject(sb01.toString(),params, Integer.class));
		String pageSql = "SELECT PAGE_.* FROM( "+sql+" LIMIT "+startIndex+","+pageObj.getRows()+")PAGE_" ;
		StringBuffer sb=new StringBuffer();
		sb.append(pageSql);
		return this.jdbcTemplate.query(sb.toString(), rowMapper,params);
	}
	public <T> List<T> queryByPage(String sql, RowMapper<T> rowMapper, PageObj pageObj,String type , Object... params) {
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb11 = new StringBuffer();
		int startIndex = (pageObj.getPage()-1) * pageObj.getRows();
		if(CommonUtil.hasValue(type)&&"1".equals(type)){
			String[] split = sql.split("GROUP BY");
			sql=split[0];
		}
		String[] split = sql.split("FROM");
		
		String newsql="SELECT COUNT(*) TOTAL FROM "+split[split.length-1];
		sb11.append(newsql);
		pageObj.setTotal(this.getJdbcTemplate().queryForObject(sb11.toString(),params, Integer.class));
		String pageSql = "SELECT PAGE_.* FROM("+sql+" LIMIT "+startIndex+","+pageObj.getRows()+")PAGE_" ;
		sb1.append(pageSql);
		return this.jdbcTemplate.query(sb1.toString(), rowMapper,params);
	}
	
	/**
	 * 只查询sql字段，不查询总数。
	 * @param sql
	 * @param params
	 * @param rowMapper
	 * @param pageObj
	 * @return
	 */
//	public <T> List<T> queryByPageForContent(String sql, RowMapper<T> rowMapper, PageObj pageObj, Object... params) {
//		int startIndex = (pageObj.getPage()-1) * pageObj.getRows();
//		String pageSql = "SELECT PAGE_.* FROM("+ sql+"  LIMIT "+startIndex+","+pageObj.getRows()+") PAGE_";
//		return this.jdbcTemplate.query(pageSql, params, rowMapper);
//	}
	
	
	/**
	 * 可以直接替换特殊符号的只用这个方法
	 * @param sql
	 * @param rowMapper
	 * @param pageObj
	 * @param params
	 * @return
	 */
//	public <T> List<T> queryByPageForCount(String sql, RowMapper<T> rowMapper, PageObj pageObj, Object... params) {
//		int startIndex = (pageObj.getPage()-1) * pageObj.getRows();
//		String sqlCount=sql.substring(0, sql.indexOf("&"))+" COUNT(1) " +sql.substring(sql.lastIndexOf("&")+1, sql.length());
//		pageObj.setTotal(this.getJdbcTemplate().queryForObject(sqlCount,params, Integer.class));
//		String pageSql = "SELECT PAGE_.* FROM("+ sql.replaceAll("&", "")+"  LIMIT "+startIndex+","+pageObj.getRows()+") PAGE_";
//		return this.jdbcTemplate.query(pageSql, rowMapper,params);
//	}
	/**
	 * 无法直接替换的，例如sql语句最后带order by 排序的要添加查询总数的SQLCount
	 * @param sql
	 * @param sqlCount
	 * @param rowMapper
	 * @param pageObj
	 * @param params
	 * @return
	 */
//	public <T> List<T> queryByPageForCount(String sql,String sqlCount, RowMapper<T> rowMapper, PageObj pageObj, Object... params) {
//		int startIndex = (pageObj.getPage()-1) * pageObj.getRows();
//		sqlCount=sqlCount.substring(0, sqlCount.indexOf("&"))+" COUNT(1) " +sqlCount.substring(sqlCount.lastIndexOf("&")+1, sqlCount.length());
//		pageObj.setTotal(this.getJdbcTemplate().queryForObject(sqlCount,params, Integer.class));
//		String pageSql = "SELECT PAGE_.* FROM("+ sql.replaceAll("&", "")+"  LIMIT "+startIndex+","+pageObj.getRows()+") PAGE_";
//		return this.jdbcTemplate.query(pageSql, rowMapper,params);
//	}
	

	/**
	 * sql执行  分页
	 * 2015-12-2 此方法有漏洞，直接个数据库字段起别名不生效，以后不再使用，请使用此方法 Map<String, Object> queryByPage(String sql,ArrayList<Object> paralist,PageObj pageObj)
	 */
//	@Deprecated
//	public Map<String, Object> excuteSelectSqlByPage(String sql,ArrayList<Object> paralist,PageObj pageObj){
//		Map<String, Object> modelMap = new HashMap<String, Object>();
//		List<Object> list =this.queryByPage(sql, new RowMapper<Object>(){
//			@Override
//			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//				JSONObject row = new JSONObject();  
//				 ResultSetMetaData rowdata =rs.getMetaData();
//				 for(int i = 1 ; i<= rowdata.getColumnCount() ; i++){
//					 String columnName = rowdata.getColumnName(i); 
//					 row.put(columnName, rs.getObject(i)==null?"":rs.getObject(i));
//				 }
//				return row;
//			}
//		},pageObj,paralist.toArray());
//		modelMap.put("pageObj", pageObj);
//		modelMap.put("list", list);
//		return modelMap;
//	}
	
	
	/**
	 * 通用查询 返回Map JSONObject对象
	 * @param sql
	 * @param paralist
	 * @return
	 * 2015-12-2 此方法有漏洞，直接个数据库字段起别名不生效，以后不再使用，请使用此方法 List<Object> queryForList(String sql,ArrayList<Object> paralist)
	 */
//	@Deprecated
//	public Map<String, Object> excuteSelectSqlForMap(String sql,ArrayList<Object> paralist){
//		Map<String, Object> modelMap = new HashMap<String, Object>();
//		List<Object> list = this.getJdbcTemplate().query(sql, paralist.toArray(),new RowMapper<Object>(){
//			@Override
//			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//				JSONObject row = new JSONObject();  
//				 ResultSetMetaData rowdata =rs.getMetaData();
//				 for(int i = 1 ; i<= rowdata.getColumnCount() ; i++){
//					 String columnName = rowdata.getColumnName(i); 
//					 row.put(columnName, (rs.getObject(i) == null||"null".equals(rs.getObject(i)))?"":rs.getObject(i));
//				 }
//				return row;
//			}
//		});
//		modelMap.put("list", list);
//		return modelMap;
//	}
	
	/**
	 * 通用查询 返回Map JSONObject对象
	 * @param sql
	 * @param paralist
	 * @return
	 * 2015-12-2 此方法有漏洞，直接个数据库字段起别名不生效，以后不再使用，请使用此方法 List<Object> queryForList(String sql,ArrayList<Object> paralist)
	 */
//	@Deprecated
//	public List<Object> excuteSelectSqlForList(String sql,ArrayList<Object> paralist){
// 		return this.getJdbcTemplate().query(sql, paralist.toArray(),new RowMapper<Object>(){
//			@Override
//			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//				JSONObject row = new JSONObject();  
//				 ResultSetMetaData rowdata =rs.getMetaData();
//				 for(int i = 1 ; i<= rowdata.getColumnCount() ; i++){
//					 String columnName = rowdata.getColumnName(i); 
//					 row.put(columnName, rs.getObject(i)==null?"":rs.getObject(i));
//				 }
//				return row;
//			}
//		});
//	}
    
}
