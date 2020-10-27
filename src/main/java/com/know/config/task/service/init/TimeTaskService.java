package com.know.config.task.service.init;

import com.alibaba.fastjson.JSONObject;
import com.know.config.dto.PageObj;
import com.know.config.task.dao.TimeTaskDao;
import com.know.config.task.dto.SysTimerDto;
import com.know.util.CommonUtil;
import com.know.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class TimeTaskService {
	public static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	
	@Resource
	private QuartzService  quartzService;
	@Resource
	private TimeTaskDao taskDao;
	
	/**查询所有任务
	 * @param shardingId *//*
	public List<SysJobDto> getJobs(Integer shardingId,Integer belongProvince) {
		List<SysJobDto> list = taskDao.sercAllJob(shardingId,belongProvince);
		return list;
	}
	
	*//**查询所有节点
	 * @param integer *//*
	public List<SysNodeDto> getNodes(Integer shardingId,Integer belongProvince) {
		return taskDao.sercAllNode(shardingId,belongProvince);
	}
	
	*//**查询所属省份
	 * @param integer *//*
	public List<SysBelongProvince> getProvinces(Integer shardingId) {
		List<SysBelongProvince> list=taskDao.sercAllProvinces(shardingId);
		for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if ((list.get(j).getProvinceName()).equals(list.get(i).getProvinceName())) {
                    list.remove(j);
                }
            }
        }
//		System.out.println("list:"+list.size());
		return list;
	}
	
	*//**分页查询所有定时器任务*//*
	public Map<String, Object> getTasksByPage(SysTimerDto tt, PageObj pageObj) {
		List<SysTimerDto> list= taskDao.searchByPage(tt,pageObj);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", list);
		map.put("total", pageObj.getTotal());
		return map;
	}
	
	*//**定时任务查询*//*
	public List<SysTimerDto> getTasks(SysTimerDto tt) {
		return taskDao.search(tt);
	}
	
	*//**新增一个任务*//*
	public long addJob(SysJobDto job) {
		return taskDao.addNjob(job);
	}
	
	*//**修改任务配置*//*
	public int modJob(SysJobDto job) {
		return taskDao.updateJob(job);
	}
	
	*//**删除一个任务配置*//*
	public int delJob(Integer jobId,int shardingId){
		return taskDao.deljobById(jobId, shardingId);
	}
	
	*//**新增一个节点*//*
	public long addNode(SysNodeDto node) {
		return taskDao.addNode(node);
	}
	
	*//**修改节点内容*//*
	public int modNode(SysNodeDto node) {
		return taskDao.updateNode(node);
	}
	
	*//**删除节点**//*
	public int delNode(Integer appNodeId,int shardingId) {
		return taskDao.delNodeById(appNodeId,shardingId);
	}
	
	*//**新增定时器***//*
	public long addTask(SysTimerDto t) {
		return taskDao.addTask(t);
	}
	
	*//**删除指定定时器**//*
	public int delTask(Integer timerId,int shardingId) {
		return taskDao.delTaskById(timerId,shardingId);
	}
	
	*//**修改定时器*//*
	@Transactional
	public int modTask(SysTimerDto t) {
		int flag=taskDao.updateTask(t);
		if(t.getTimerStatus()==1){
			//修改的是启动状态的任务，先修改数据，再重置内存中的任务运行时间
		    quartzService.modifyJobTime(t);
		}
		return flag;
	}
	*/
	/**
	 * 停止单个定时任务
	 * @param uri 当前项目的上下文路径
	 * @throws Exception 
	 */
	public String stopSingleTask(SysTimerDto task, String uri) throws Exception {
			if(task == null){
				return "未指定定时任务";
			}
			String url = "http://" + task.getNode()+ uri +  "/stopTask.refresh" ;
			// POST 请求
			return HttpRequestUtil.sendPost(url, "taskString="+JSONObject.toJSONString(task),10000);
	}
	
	/**启动单个定时任务
	 * @param uri 当前项目上下文环境
	 * @throws Exception 
	 */
	public String startSingleTask(SysTimerDto task, String uri) throws Exception {
			if(task == null){
				return "未指定定时任务";
			}
			String url = "http://" + task.getNode()+ uri +  "/startTask.refresh" ;
			// POST 请求
	        return HttpRequestUtil.sendPost(url, "taskString="+JSONObject.toJSONString(task),10000);
	}
	
	/**
	 * 当服务上某一节点重启时程序会直接将此节点所在的服务器上所有的定时器数据库状态都更新为0，此时如果有另外节点的话，就会造成定时器内存状态和数据库状态不一致,
	 * 内存里定时器还在跑，但是数据库显示不运行了。所以此方法为了实现将内存还在运行的定时器数据库状态更新过来，在定时器实现类(TimeJob)里调用此方法
	 * 先查询定时器数据库状态，如果是0，更新更定时器状态 为1 
	 */
	public void updateJobTaskByJobIdService(SysTimerDto task) {
		SysTimerDto task2 = taskDao.queryJobTaskByJobId(task,task.getShardingId());
		if(task2 !=null && task2.getTimerStatus() ==0){
			taskDao.updateJobTaskByJobId(task, 1);
		}
	}
	
	/***停止所有定时任务
	 * @param uri **/
	public String stopAllTasks(List<SysTimerDto> tasks, String uri) {
		int c=0;
		for(SysTimerDto s:tasks){
			try{
				if(s.getTimerStatus()==0) {
					continue;
				}
				this.stopSingleTask(s, uri);
			}catch(Exception e){
				logger.info(e.getMessage());
				c++;
				continue;
			}
		}
		if(c==0){
			return "定时任务已全部停止";
		}else{
			return "有"+c+"个定时器关闭异常";
		}
	}
	
	/**启动所有定时任务*/
	public String startAllTasks(List<SysTimerDto> tasks, String uri) {
		int c=0;
		for(SysTimerDto s:tasks){
			try{
				if(s.getTimerStatus()==1) {
					continue;
				}
				this.startSingleTask(s, uri);
			}catch(Exception e){
				logger.info(e.getMessage());
				c++;
				continue;
			}
		}
		if(c==0){
			return "定时任务已全部启动";
		}else{
			return "有"+c+"个定时器启动异常";
		}
	}
	
	//查询定时器表中已有的记录
	public SysTimerDto queryFormerTimer(SysTimerDto sysTimerDto,Integer shardingId){
		return taskDao.queryFormerTask(sysTimerDto, shardingId);
	}

	public Map<String, Object> queryInfoTask(String province, int shardingId,
			SysTimerDto sysTimerDto, PageObj pageObj) {

		Map<String,Object> map =new HashMap<String,Object>();
		map.put("code",0);
		map.put("msg", "");
		map.put("rows", taskDao.queryInfoTask(province,shardingId,sysTimerDto,pageObj));
		map.put("total", pageObj.getTotal());
		return map;
	
	}

	public String insetTask(SysTimerDto sysTimerDto) {
		String str="fail";
		int n = taskDao.inserTaskInfo(sysTimerDto);
		if(n>0){
			str="succ";
		}
		return str;
	}

	public String updateTask(SysTimerDto sysTimerDto) {
		String str="fail";
		int n = taskDao.updateTaskInfo(sysTimerDto);
		if(n>0){
			str="succ";
		}
		return str;
	}

	public String deleteTask(String province, int shardingId, int timerId) {
		String str="fail";
		int n = taskDao.deleteTaskInfo(province,shardingId,timerId);
		if(n>0){
			str="succ";
		}
		return str;
	}
}
