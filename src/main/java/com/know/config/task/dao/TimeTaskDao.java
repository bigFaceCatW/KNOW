package com.know.config.task.dao;


import com.know.config.dto.PageObj;
import com.know.config.task.dto.SysTimerDto;

import java.util.List;

public interface TimeTaskDao {
	
//	//查询所有的任务
//	List<SysJobDto> sercAllJob(Integer shardingId, Integer belongProvince);
//	//新增一个任务
//	long addNjob(SysJobDto job);
//	//删除一个任务
//	int deljobById(Integer jobId,int shardingId);
//	//修改一个任务
//	int updateJob(SysJobDto job);
//	
//	//查询所有的节点
//	List<SysNodeDto> sercAllNode(Integer shardingId, Integer belongProvince);
//	//新增一个任务
//	long addNode(SysNodeDto node);
//	//删除一个任务
//	int delNodeById(Integer nodeId,int shardingId);
//	//修改一个任务
//	int updateNode(SysNodeDto node);
//	
//	//分页查询定时任务
//		List<SysTimerDto> searchByPage(SysTimerDto tt, PageObj pageObj);
//	//条件查询所有定时任务
//	List<SysTimerDto> search(SysTimerDto timer);
//	//新增定时任务
//	int addTask(SysTimerDto timer);
//	//修改定时任务
//	int updateTask(SysTimerDto timer);
//	//删除
//	int delTaskById(Integer timerId,int shardingId);
	
	//更新所有的定时任务状态
	 void updateJobTask(int status, String shardingId);
	//根据定时器Id查询数据 
	SysTimerDto queryJobTaskByJobId(SysTimerDto jobTaskDto, int shardingId);
	//更新指定的定时任务状态
	void updateJobTaskByJobId(SysTimerDto jobTaskDto, int i);
	
	//查询定时器表中原有的记录
	 SysTimerDto queryFormerTask(SysTimerDto jobTaskDto, Integer shardingId);
	 List<SysTimerDto> queryInfoTask(String province, int shardingId,
									 SysTimerDto sysTimerDto, PageObj pageObj);
	 int inserTaskInfo(SysTimerDto sysTimerDto);
	 int updateTaskInfo(SysTimerDto sysTimerDto);
	 int deleteTaskInfo(String province, int shardingId, int timerId);
	
}
