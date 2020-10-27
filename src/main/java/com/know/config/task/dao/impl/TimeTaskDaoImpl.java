package com.know.config.task.dao.impl;

import com.know.config.dto.PageObj;
import com.know.config.task.dao.TimeTaskDao;
import com.know.config.task.dto.SysTimerDto;
import com.know.util.CommonUtil;
import com.know.util.JdbcDaoSupport4mysql;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;




@Repository
public class TimeTaskDaoImpl extends JdbcDaoSupport4mysql implements TimeTaskDao {

/*	@Override
	public List<SysJobDto> sercAllJob(Integer shardingId,Integer belongProvince) {//查询所有
		String sql="SELECT JOB_ID,JOB_CD,JOB_NAME,CONTENT,ENABLE FROM SYS_JOB WHERE DEL_FLAG=0 AND "
				+ " SHARDING_ID=? ";
//		ArrayList<Object> paralist = new ArrayList<Object>();
//		System.out.println("shardingId:"+shardingId);
		if(shardingId ==10){
			shardingId = belongProvince;
		}
//		System.out.println("shardingId:"+shardingId);
//		System.out.println("belongProvince:"+belongProvince);
		
		List<SysJobDto> list = 
		this.getJdbcTemplate().query(sql,new Object[]{shardingId}, new RowMapper<SysJobDto>(){
			@Override
			public SysJobDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SysJobDto j=new SysJobDto();
				j.setJobId(rs.getInt("JOB_ID"));
				j.setJobCd(rs.getString("JOB_CD"));
				j.setJobName(rs.getString("JOB_NAME"));
				j.setContent(rs.getString("CONTENT"));
				j.setEnAble(rs.getInt("ENABLE"));
				return j;
			}
		});
		
		paralist.add(shardingId);
		paralist.add(10);
		List<SysJobDto> list = 
		 this.getJdbcTemplate().query(sql, new RowMapper<SysJobDto>(){
			@Override
			public SysJobDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SysJobDto j=new SysJobDto();
				j.setJobId(rs.getInt("JOB_ID"));
				j.setJobCd(rs.getString("JOB_CD"));
				j.setJobName(rs.getString("JOB_NAME"));
				j.setContent(rs.getString("CONTENT"));
				j.setEnAble(rs.getInt("ENABLE"));
				return j;
			}
		},paralist);
//		System.out.println("list--"+list);
		return list;
	}

	@Override
	public long addNjob(final SysJobDto job) {//新增
		KeyHolder key=new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				 String sql="INSERT INTO SYS_JOB (JOB_CD,JOB_NAME,CONTENT,ENABLE,DEL_FLAG,SHARDING_ID) VALUES(?,?,?,?,?,?)";
				 PreparedStatement ps = con.prepareStatement(sql,new String[]{"JOB_ID"}); 
				 ps.setString(1, job.getJobCd());
				 ps.setString(2, job.getJobName());
				 ps.setString(3, job.getContent());
				 ps.setInt(4, job.getEnAble());
				 ps.setInt(5, 0);
				 ps.setInt(6, job.getShardingId());
				return ps;
			}}, key);
		return key.getKey().longValue();
	}

	@Override
	public int deljobById(Integer jobId,int shardingId) {//逻辑删除
		Object[] args=new Object[]{jobId,shardingId};
		String sql="UPDATE SYS_JOB SET DEL_FLAG=1 WHERE JOB_ID=? AND SHARDING_ID=?";
		return this.getJdbcTemplate().update(sql, args);
	}

	@Override
	public int updateJob(SysJobDto job) {//修改数据
		String sql="UPDATE SYS_JOB SET JOB_NAME=?,JOB_CD=?,ENABLE=?,CONTENT=? WHERE JOB_ID=? AND SHARDING_ID=?";
		Object[] args=new Object[]{job.getJobName(),job.getJobCd(),job.getEnAble(),job.getContent(),job.getJobId(),job.getShardingId()};
		return this.getJdbcTemplate().update(sql, args);
	}

	@Override
	public List<SysNodeDto> sercAllNode(Integer shardingId , Integer belongProvince) {//查询所有Node
		String sql="SELECT APP_NODE_ID,NODE_NAME,NODE_TYPE,IP_ADDRESS,PORT_VALUE,CONTENT,CONCAT(IP_ADDRESS,':',PORT_VALUE) IPPORT FROM SYS_NODE "
				+ " WHERE SHARDING_ID=? ";
		if(shardingId ==10){
			shardingId = belongProvince;
		}
		return this.getJdbcTemplate().query(sql, new RowMapper<SysNodeDto>(){
			@Override
			public SysNodeDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SysNodeDto n=new SysNodeDto();
				n.setAppNodeId(rs.getInt("APP_NODE_ID"));
				n.setNodeName(rs.getString("NODE_NAME"));
				n.setNodeType(rs.getInt("NODE_TYPE"));
				n.setIpAddress(rs.getString("IP_ADDRESS"));
				n.setPortValue(rs.getInt("PORT_VALUE"));
				n.setContent(rs.getString("CONTENT"));
				n.setIpport(rs.getString("IPPORT"));
				return n;
			}
		},shardingId);
	}

	@Override
	public long addNode(final SysNodeDto node) {//新增Node
		KeyHolder key=new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql="INSERT INTO SYS_NODE (NODE_NAME,NODE_TYPE,IP_ADDRESS,PORT_VALUE,CONTENT,SHARDING_ID) VALUES(?,?,?,?,?,?)";
				 PreparedStatement ps = con.prepareStatement(sql,new String[]{"APP_NODE_ID"});
				 ps.setString(1, node.getNodeName());
				 ps.setInt(2, node.getNodeType());
				 ps.setString(3, node.getIpAddress());
				 ps.setInt(4, node.getPortValue());
				 ps.setString(5, node.getContent());
				 ps.setInt(6, node.getShardingId());
				return ps;
			}},key);
		return key.getKey().longValue();
	}

	@Override
	public int delNodeById(Integer nodeId,int shardingId) {//物理删除
		Object[] args=new Object[]{nodeId,shardingId};
		String sql="DELETE FROM SYS_NODE WHERE APP_NODE_ID=? and sharding_id=?";
		return this.getJdbcTemplate().update(sql, args);
	}

	@Override
	public int updateNode(SysNodeDto node) {//修改Node
		String sql="UPDATE SYS_NODE SET IP_ADDRESS=?,PORT_VALUE=?,NODE_NAME=?,NODE_TYPE=?,CONTENT=? "
				+ " WHERE APP_NODE_ID=? AND SHARDING_ID=? ";
		Object[] args=new Object[]{node.getIpAddress(),node.getPortValue(),node.getNodeName(),node.getNodeType(),node.getContent(),node.getAppNodeId(),node.getShardingId()};
		return this.getJdbcTemplate().update(sql,args);
	}
	
	@Override//条件查询：不分页
	public List<SysTimerDto> search(SysTimerDto timer) {
		TaskRowMapper mapper=new TaskRowMapper();
		String sql="SELECT t1.TIMER_ID,t1.TIMER_NAME,t1.JOB_ID,t1.NODE_ID,t1.CLASS_NAME,t1.EXCUTE_METHOD,t1.TIMER_STATUS,"
				+ " t1.CRON_EXPRESSION,t1.TIMER_DESC,t1.BUSI_PARA,t1.ENABLE,CONCAT(t2.IP_ADDRESS,':',t2.PORT_VALUE) NODE,t1.ENABLE+t3.ENABLE ISENABLE "
				+ " FROM SYS_TIMER t1,SYS_NODE t2,SYS_JOB t3 "
				+ " WHERE t1.DEL_FLAG=0 AND t1.JOB_ID=t3.JOB_ID AND t1.NODE_ID=t2.APP_NODE_ID AND t1.SHARDING_ID=? ";
		if(timer.getJobId()!=null && timer.getJobId()>-1){//jobId
			sql+=" AND t3.JOB_ID="+timer.getJobId();
		}
		if(timer.getNodeId()!=null && timer.getNodeId()>-1){//nodeId
			sql+=" AND t2.APP_NODE_ID="+timer.getNodeId();
		}
		if(CommonUtil.hasValue(timer.getTimerName())){//timeName
			sql+=" AND t1.TIMER_NAME LIKE '%"+timer.getTimerName()+"%' ";
		}
		if( timer.getEnAble()!=null && timer.getEnAble()>-1){//是否启用：定时器和任务都为启用，则为启用，否则为停用状态
			if(timer.getEnAble()==0){
				sql+=" AND( t1.ENABLE=0 OR t3.ENABLE=0) ";
			}else if(timer.getEnAble()==1){
				sql+=" AND t1.ENABLE=1 AND t3.ENABLE=1 ";
			}
		}
		return this.getJdbcTemplate().query(sql, mapper,timer.getShardingId());
	}

	@Override//新增
	public int addTask(SysTimerDto t) {
		String sql="INSERT INTO SYS_TIMER (TIMER_NAME,JOB_ID,NODE_ID,CLASS_NAME,EXCUTE_METHOD,TIMER_STATUS,CRON_EXPRESSION,TIMER_DESC,BUSI_PARA,CREATE_TIME,ENABLE,DEL_FLAG,SHARDING_ID) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,0,?)";
		Object[] args=new Object[]{t.getTimerName(),t.getJobId(),t.getNodeId(),t.getClassName(),t.getExcuteMethod(),0,
				t.getCronExpression(),t.getTimerDesc(),t.getBusiPara(),t.getEnAble(),t.getShardingId()};
		return this.getJdbcTemplate().update(sql, args);
	}

	@Override//修改
	public int updateTask(SysTimerDto t) {
		String sql="UPDATE SYS_TIMER SET JOB_ID=?,NODE_ID=?,TIMER_NAME=?,CLASS_NAME=?,EXCUTE_METHOD=?,CRON_EXPRESSION=?,"
				+ "BUSI_PARA=?,ENABLE=?,TIMER_DESC=? WHERE TIMER_ID=? AND SHARDING_ID=?";
		Object[] args=new Object[]{t.getJobId(),t.getNodeId(),t.getTimerName(),t.getClassName(),t.getExcuteMethod(),t.getCronExpression(),
				t.getBusiPara(),t.getEnAble(),t.getTimerDesc(),t.getTimerId(),t.getShardingId()};
		return this.getJdbcTemplate().update(sql, args);
	}

	@Override//逻辑删除
	public int delTaskById(Integer timerId,int shardingId) {
		Object[] args=new Object[]{timerId,shardingId};
		String sql="UPDATE SYS_TIMER SET DEL_FLAG=1 WHERE TIMER_ID=? and SHARDING_ID=?";
		return this.getJdbcTemplate().update(sql, args);
	}*/

	@Override
	public SysTimerDto queryJobTaskByJobId(SysTimerDto jobTaskDto,
										   int shardingId) {
	
		String sql=" SELECT t1.TIMER_ID,t1.TIMER_NAME,t1.CLASS_NAME,t1.EXCUTE_METHOD,t1.TIMER_STATUS,"
				+ " t1.CRON_EXPRESSION,t1.TIMER_DESC,t1.BUSI_PARA,t1.ENABLE,t1.NODE,t1.SHARDING_ID,t1.TENANT_ID "
				+ " FROM SYS_TIMER t1"
				+ " WHERE t1.DEL_FLAG=0  "
				+ "AND t1.SHARDING_ID=? AND t1.TIMER_ID=? AND t1.TENANT_ID=?";
		return this.getJdbcTemplate().query(sql, new Object[]{shardingId,jobTaskDto.getTimerId(),jobTaskDto.getTenantId()},new RowMapper<SysTimerDto>(){

			@Override
			public SysTimerDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SysTimerDto t=new SysTimerDto();
				t.setTimerId(rs.getInt("TIMER_ID"));
				t.setTimerName(rs.getString("TIMER_NAME"));
				t.setClassName(rs.getString("CLASS_NAME"));
				t.setBusiPara(rs.getString("BUSI_PARA"));
				t.setExcuteMethod(rs.getString("EXCUTE_METHOD"));
				t.setTimerStatus(rs.getInt("TIMER_STATUS"));
				t.setCronExpression(rs.getString("CRON_EXPRESSION"));
				t.setTimerDesc(rs.getString("TIMER_DESC"));
				t.setBusiPara(rs.getString("BUSI_PARA"));
				t.setEnAble(rs.getInt("ENABLE"));
				t.setNode(rs.getString("NODE"));
				t.setShardingId(rs.getInt("SHARDING_ID"));
				t.setTenantId(rs.getString("TENANT_ID"));
				return t;
			}}).get(0);
	}
//修改过定时器的状态
	@Override
	public void updateJobTaskByJobId(SysTimerDto jobTaskDto, int status) {
		String sql = "UPDATE SYS_TIMER SET TIMER_STATUS=? WHERE TIMER_ID=? AND SHARDING_ID=? AND TENANT_ID=? ";
		this.getJdbcTemplate().update(sql, new Object[]{status,jobTaskDto.getTimerId(),jobTaskDto.getShardingId(),jobTaskDto.getTenantId()});
	}

	@Override
	public void updateJobTask(int status, String tanentId) {
		String updateJobTaskStatus = "UPDATE SYS_TIMER SET TIMER_STATUS=? WHERE  TENANT_ID=? ";
		this.getJdbcTemplate().update(updateJobTaskStatus, new Object[]{status,tanentId});		
	}
//
//	@Override//分页查询
//	public List<SysTimerDto> searchByPage(SysTimerDto timer, PageObj pageObj) {
//		TaskRowMapper mapper=new TaskRowMapper();
//		String sql="SELECT t1.TIMER_ID,t1.TIMER_NAME,t1.JOB_ID,t1.NODE_ID,t1.CLASS_NAME,t1.EXCUTE_METHOD,t1.TIMER_STATUS,"
//				+ "t1.CRON_EXPRESSION,t1.TIMER_DESC,t1.BUSI_PARA,t1.ENABLE,CONCAT(t2.IP_ADDRESS,':',t2.PORT_VALUE) NODE,t1.ENABLE+t3.ENABLE ISENABLE "
//				+ " FROM SYS_TIMER t1,SYS_NODE t2,SYS_JOB t3 "
//				+ " WHERE t1.DEL_FLAG=0 AND t1.JOB_ID=t3.JOB_ID AND t1.NODE_ID=t2.APP_NODE_ID AND t1.SHARDING_ID= ?  ";
//
//		if(CommonUtil.hasValue(timer.getJob())){//job
//			sql+=" AND t3.JOB_NAME like '%"+timer.getJob() +"%' ";
//		}
//		if(CommonUtil.hasValue(timer.getNode())){//node
//			sql+=" AND t2.NODE_NAME LIKE '%"+timer.getNode()+"%' ";
//		}
//		if(CommonUtil.hasValue(timer.getTimerName())){//timeName
//			sql+=" AND t1.TIMER_NAME LIKE '%"+timer.getTimerName()+"%' ";
//		}
//		if( timer.getEnAble()!=null && timer.getEnAble()>-1){//是否启用：定时器和任务都为启用，则为启用，否则为停用状态
//			if(timer.getEnAble()==0){
//				sql+=" AND ( t1.ENABLE=0 OR t3.ENABLE=0) ";
//			}else if(timer.getEnAble()==1){
//				sql+=" AND t1.ENABLE=1 AND t3.ENABLE=1 ";
//			}
//		}
//		if(timer.getShardingId() ==10 ){
//			return this.queryByPage(sql,mapper, pageObj,timer.getBelongProvince());
//		}else{
//			return this.queryByPage(sql,mapper, pageObj,timer.getShardingId());
//		}
//		
//	}
	
	
	//查询定时器表中原有的记录
	@Override
	public SysTimerDto queryFormerTask(SysTimerDto jobTaskDto,Integer shardingId) {
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT TIMER_ID,TIMER_NAME,JOB_ID,NODE_ID,CLASS_NAME,EXCUTE_METHOD,CRON_EXPRESSION,TIMER_DESC,BUSI_PARA,CREATE_TIME FROM sys_timer WHERE TIMER_ID=? AND SHARDING_ID=?";
		return this.getJdbcTemplate().queryForObject(sql, new Object[]{jobTaskDto.getTimerId(),shardingId}, new RowMapper<SysTimerDto>(){
			@Override
			public SysTimerDto mapRow(ResultSet rs, int rowNum)throws SQLException {
				SysTimerDto sysTimerDto = new SysTimerDto();
				sysTimerDto.setTimerId(rs.getInt("TIMER_ID"));
				sysTimerDto.setTimerName(rs.getString("TIMER_NAME"));
				sysTimerDto.setJobId(rs.getInt("JOB_ID"));
				sysTimerDto.setNodeId(rs.getInt("NODE_ID"));
				sysTimerDto.setClassName(rs.getString("CLASS_NAME"));
				sysTimerDto.setExcuteMethod(rs.getString("EXCUTE_METHOD"));
				sysTimerDto.setCronExpression(rs.getString("CRON_EXPRESSION"));
				sysTimerDto.setTimerDesc(rs.getString("TIMER_DESC"));
				sysTimerDto.setBusiPara(rs.getString("BUSI_PARA"));
				try {
					sysTimerDto.setCreateTime(new Timestamp(format.parse(rs.getString("CREATE_TIME")).getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return sysTimerDto;
			}
		});
	}
	@Override
	public List<SysTimerDto> queryInfoTask(String province, int shardingId,
			SysTimerDto sysTimerDto, PageObj pageObj) {
		ArrayList<Object> list = new  ArrayList<Object>();
		list.add(shardingId);
		list.add(province);
		
		String sql="SELECT t1.TIMER_ID,t1.TIMER_NAME,t1.CLASS_NAME,t1.EXCUTE_METHOD,t1.TIMER_STATUS,"
				+ " t1.CRON_EXPRESSION,t1.TIMER_DESC,t1.BUSI_PARA,t1.ENABLE,t1.NODE,t1.SHARDING_ID,t1.TENANT_ID  "
				+ " FROM sys_timer t1 "
				+ " WHERE t1.DEL_FLAG=0 AND t1.SHARDING_ID=? AND t1.TENANT_ID=? ";
	
		if (CommonUtil.hasValue(sysTimerDto.getTimerName())) {
			sql += " AND t1.TIMER_NAME LIKE ? ";
			list.add("%" +sysTimerDto.getTimerName()+"%");
		}


		if (CommonUtil.hasValue(sysTimerDto.getNode())) {
			sql += " AND t1.NODE LIKE ? ";
			list.add("%" +sysTimerDto.getNode()+"%");
		}
			return this.queryByPage(sql,new RowMapper<SysTimerDto>() {

				@Override
				public SysTimerDto mapRow(
						ResultSet rs, int rowNum) throws SQLException {
					SysTimerDto t=new SysTimerDto();
					t.setTimerId(rs.getInt("TIMER_ID"));
					t.setTimerName(rs.getString("TIMER_NAME"));
					t.setClassName(rs.getString("CLASS_NAME"));
					t.setBusiPara(rs.getString("BUSI_PARA"));
					t.setExcuteMethod(rs.getString("EXCUTE_METHOD"));
					t.setTimerStatus(rs.getInt("TIMER_STATUS"));
					t.setCronExpression(rs.getString("CRON_EXPRESSION"));
					t.setTimerDesc(rs.getString("TIMER_DESC"));
					t.setEnAble(rs.getInt("ENABLE"));
					t.setNode(rs.getString("NODE"));
					t.setShardingId(rs.getInt("SHARDING_ID"));
					t.setTenantId(rs.getString("TENANT_ID"));
					return t;
				}},pageObj,list.toArray());
		}
	@Override
	public int inserTaskInfo(SysTimerDto sysTimerDto) {
		String sql="INSERT INTO SYS_TIMER (TIMER_NAME,CLASS_NAME,EXCUTE_METHOD,CRON_EXPRESSION,TIMER_DESC,BUSI_PARA,NODE,TENANT_ID,SHARDING_ID) "
				+ "VALUES (?,?,?,?,?,?,?,?,?)";
		ArrayList<Object> list = new  ArrayList<Object>();
		list.add(sysTimerDto.getTimerName());
		list.add(sysTimerDto.getClassName());
		list.add(sysTimerDto.getExcuteMethod());
		list.add(sysTimerDto.getCronExpression());
		list.add(sysTimerDto.getTimerDesc());
		list.add(sysTimerDto.getBusiPara());
		list.add(sysTimerDto.getNode());
		list.add(sysTimerDto.getTenantId());
		list.add(sysTimerDto.getShardingId());
		
		return this.getJdbcTemplate().update(sql, list.toArray());
	}
	@Override
	public int updateTaskInfo(SysTimerDto sysTimerDto) {

		String sql="UPDATE SYS_TIMER SET TIMER_NAME=?,CLASS_NAME=?,EXCUTE_METHOD=?,CRON_EXPRESSION=?,TIMER_DESC=?,BUSI_PARA=?,NODE=? "
				+" WHERE TIMER_ID=? and TENANT_ID=? and SHARDING_ID=?";
		ArrayList<Object> list = new  ArrayList<Object>();
		list.add(sysTimerDto.getTimerName());
		list.add(sysTimerDto.getClassName());
		list.add(sysTimerDto.getExcuteMethod());
		list.add(sysTimerDto.getCronExpression());
		list.add(sysTimerDto.getTimerDesc());
		list.add(sysTimerDto.getBusiPara());
		list.add(sysTimerDto.getNode());
		list.add(sysTimerDto.getTimerId());
		list.add(sysTimerDto.getTenantId());
		list.add(sysTimerDto.getShardingId());
		
		return this.getJdbcTemplate().update(sql, list.toArray());
	
	}
	@Override
	public int deleteTaskInfo(String province, int shardingId, int timerId) {
		
		String sql="UPDATE sys_timer SET DEL_FLAG=1  WHERE TENANT_ID=? AND sharding_id=? AND TIMER_ID=?";
		ArrayList<Object> list = new  ArrayList<Object>();
		list.add(province);
		list.add(shardingId);
		list.add(timerId);
		return this.getJdbcTemplate().update(sql,list.toArray());
	}

	
	

	

//	@Override
//	public List<SysBelongProvince> sercAllProvinces(Integer shardingId) {
//		String sql="SELECT SUBSTR(dic_id ,1,2) AS provinceId , dic_name AS provinceName FROM sys_scenes_dic WHERE bs_cd = 'mc.province' ";
//		if(shardingId != 10){
//			sql += " AND SUBSTR(dic_id ,1,2) ="+ shardingId;
//		}
//		return this.getJdbcTemplate().query(sql, new RowMapper<SysBelongProvince>(){
//			@Override
//			public SysBelongProvince mapRow(ResultSet rs, int rowNum)
//					throws SQLException {
//				SysBelongProvince n=new SysBelongProvince();
//				n.setProvinceId(Integer.parseInt(rs.getString("provinceId")));
//				n.setProvinceName(rs.getString("provinceName"));
//				return n;
//			}
//		});
//	}
}
