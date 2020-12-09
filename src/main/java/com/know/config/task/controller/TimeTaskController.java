package com.know.config.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.know.config.basic.GetLoginInfoService;
import com.know.config.dto.PageObj;
import com.know.config.task.dto.SysTimerDto;
import com.know.config.task.service.init.QuartzService;
import com.know.config.task.service.init.TimeTaskService;
import com.know.util.CommonUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Map;

@RestController  
@RequestMapping(value ="/tasks")
public class TimeTaskController {
	public static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	

	@Resource
	private TimeTaskService taskService;
	@Resource
	private QuartzService quartzService;
	@Resource
	private GetLoginInfoService getLoginInfoService;
	
	
	
 /*	*//**查询所有任务job*//*
 	@RequestMapping(value = "/getJobs.do")
 	@ResponseBody
 	public List<SysJobDto> getJobs(HttpSession session,Integer belongProvince){
 		int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 		return taskService.getJobs(shardingId,belongProvince);
 	}
 	*//**查询所有节点node*//*
 	@RequestMapping(value = "/getNodes.do")
 	@ResponseBody
 	public List<SysNodeDto> getNodes(HttpSession session,Integer belongProvince){
 		int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 		List<SysNodeDto> list = taskService.getNodes(shardingId,belongProvince);
 		return  list;
 	}
 	*//**查询所有省份*//*
 	@RequestMapping(value = "/getProvinces.do")
 	@ResponseBody
 	public List<SysBelongProvince> getProvinces(HttpSession session){
 		int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 		return  taskService.getProvinces(shardingId);
 	}
 	*//**条件查询定时任务task*//*
 	@RequestMapping(value = "/getTask.do")
 	@ResponseBody
 	public List<SysTimerDto> getNodes(HttpSession session,SysTimerDto tt){
 		int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 		System.out.println("shardingId:"+shardingId);
 		tt.setShardingId(shardingId);
 		
 		return taskService.getTasks(tt);
 	}
 	*//**分页、条件查询定时任务task*//*
 	@RequestMapping(value = "/getTaskbypage.do")
 	@ResponseBody
 	public Map<String,Object> getNodesByPage(HttpSession session,SysTimerDto tt,PageObj pageObj){
 		int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 		tt.setShardingId(shardingId);
 		return taskService.getTasksByPage(tt,pageObj);
 	}
 	*//**新增任务*//*
 	@RequestMapping(value = "/addNjob.do")
 	@ResponseBody
 	public long addNjob(SysJobDto job,HttpSession session,Integer belongProvince){
 		try{
 			int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 			if(shardingId ==10){
 				shardingId = belongProvince;
 			}
 			job.setShardingId(shardingId);
 			return taskService.addJob(job);
 		}catch(Exception e){
 			e.printStackTrace();
 			return 0;
 		}
 	}
 	*//**新增节点*//*
 	@RequestMapping(value = "/addNode.do")
 	@ResponseBody
 	public long addNode(SysNodeDto node,HttpSession session,Integer belongProvince){
 		try{
 			int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 			if(shardingId ==10){
 				shardingId = belongProvince;
 			}
 			node.setShardingId(shardingId);
 			return taskService.addNode(node);
 		}catch(Exception e){
 			e.printStackTrace();
 			return 0;
 		}
 	}
 	*//**新增定时器*//*
 	@RequestMapping(value = "/addTask.do")
 	@ResponseBody
 	public long addTask(HttpServletRequest request,HttpSession session,SysTimerDto t){
 		try{
 			int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 			if(shardingId ==10){
 				shardingId = t.getBelongProvince();
 			}
 			t.setShardingId(shardingId);
 			String userId = (String) request.getSession().getAttribute("userId");
			SaveSysLogDto saveSysLogDto = new SaveSysLogDto();
			saveSysLogDto.setUserId(userId);
			saveSysLogDto.setOptObj("sys_timer");
			saveSysLogDto.setOptType("insert");
			InetAddress ia = null;
			ia = ia.getLocalHost();
			String localIp=ia.getHostAddress();//获取操作电脑的的ip地址
			LOGGER.mybatis("本机ip地址为【{}】",localIp);
			saveSysLogDto.setOptIp(localIp);
			saveSysLogDto.setOptContent("sys_timer:"+JacksonUtil.obj2json(t));
			saveSysLogService.insertSysLog(saveSysLogDto,shardingId);
 			return taskService.addTask(t);
 		}catch(Exception e){
 			e.printStackTrace();
 			return 0;
 		}
 	}
 	*//**修改任务*//*
 	@RequestMapping(value = "/modjob.do")
 	@ResponseBody
 	public int modNjob(SysJobDto job,HttpSession session){
 		try{
 			int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 			if(shardingId ==10){
 				shardingId = job.getBelongProvince();
 			}
 			job.setShardingId(shardingId);
 			return taskService.modJob(job);
 		}catch(Exception e){
 			e.printStackTrace();
 			return 0;
 		}
 	}
 	*//**修改节点*//*
 	@RequestMapping(value = "/modnode.do")
 	@ResponseBody
 	public int modNjob(SysNodeDto node,HttpSession session){
 		try{
 			int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 			if(shardingId ==10){
 				shardingId = node.getBelongProvince();
 			}
 			node.setShardingId(shardingId);
 			return taskService.modNode(node);
 		}catch(Exception e){
 			e.printStackTrace();
 			return 0;
 		}
 	}
 	
 	*//**修改定时器*//*
 	@RequestMapping(value = "/modtask.do")
 	@ResponseBody
 	public int modNjob(HttpServletRequest request,HttpSession session,SysTimerDto t){
 		try{
 			int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
 			if(shardingId ==10){
 				shardingId = t.getBelongProvince();
 			}
 			t.setShardingId(shardingId);
 			String userId = (String) request.getSession().getAttribute("userId");
			SaveSysLogDto saveSysLogDto = new SaveSysLogDto();
			saveSysLogDto.setUserId(userId);
			saveSysLogDto.setOptObj("sys_timer");
			saveSysLogDto.setOptType("update");
			InetAddress ia = null;
			ia = ia.getLocalHost();
			String localIp=ia.getHostAddress();//获取操作电脑的的ip地址
			LOGGER.mybatis("本机ip地址为【{}】",localIp);
			saveSysLogDto.setOptIp(localIp);
			SysTimerDto result = taskService.queryFormerTimer(t, shardingId);
			saveSysLogDto.setOptContent("sys_timer:原记录="+JacksonUtil.obj2json(result)+"新记录="+JacksonUtil.obj2json(t));
			saveSysLogService.insertSysLog(saveSysLogDto,shardingId);
 			return  taskService.modTask(t);
 		}catch(Exception e){
 			e.printStackTrace();
 			return 0;
 		}
 	}
 	
	*//**删除任务*//*
	@RequestMapping(value = "/deljob.do")
	@ResponseBody
	public int deljob(Integer jobId,HttpSession session,Integer belongProvince){
		try{
			int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
			if(shardingId ==10){
 				shardingId = belongProvince;
 			}
			return taskService.delJob(jobId, shardingId);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
 	
	*//**删除节点*//*
	@RequestMapping(value = "/delnode.do")
	@ResponseBody
	public int delnode(Integer appNodeId,int shardingId,Integer belongProvince){
		try{
			if(shardingId ==10){
 				shardingId = belongProvince;
 			}
			return taskService.delNode(appNodeId,shardingId);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	*//**删除定时器*//*
	@RequestMapping(value = "/deltask.do")
	@ResponseBody
	public int delTask(HttpServletRequest request,HttpSession session,Integer timerId,Integer belongProvince){
		try{
			int shardingId = accountingService.getUserInfo4SessionByUserId(session.getAttribute("userId").toString()).getShardingId();
			if(shardingId ==10){
 				shardingId = belongProvince;
 			}
			String userId = (String) request.getSession().getAttribute("userId");
			SaveSysLogDto saveSysLogDto = new SaveSysLogDto();
			saveSysLogDto.setUserId(userId);
			saveSysLogDto.setOptObj("sys_timer");
			saveSysLogDto.setOptType("delete");
			InetAddress ia = null;
			ia = ia.getLocalHost();
			String localIp=ia.getHostAddress();//获取操作电脑的的ip地址
			LOGGER.mybatis("本机ip地址为【{}】",localIp);
			saveSysLogDto.setOptIp(localIp);
			saveSysLogDto.setOptContent("sys_timer:UPDATE SYS_TIMER SET DEL_FLAG=1 WHERE TIMER_ID="+timerId);
			saveSysLogService.insertSysLog(saveSysLogDto,shardingId);
			return taskService.delTask(timerId,shardingId);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}*/
	
	
	@ApiOperation(value = "获取定时任务列表")
	@RequestMapping(value="/taskList.do",method= RequestMethod.POST)
	public Map<String,Object> queryManageController(String jsessionId, SysTimerDto sysTimerDto, PageObj pageObj){
		String province = "12";
		int shardingId = 12;
		
		return taskService.queryInfoTask(province,shardingId,sysTimerDto,pageObj);
	}
	
	@ApiOperation(value = "修改/增加定时任务")
	@RequestMapping(value="/updateTask.do",method= RequestMethod.POST)
	public String updateTime(String jsessionId,SysTimerDto sysTimerDto,int flag){
		Map<String , String > loginMap = getLoginInfoService.getLoginInfo(jsessionId);
		String province=loginMap.get("tenantId");
		int shardingId= Integer.parseInt(loginMap.get("shardingId"));
		sysTimerDto.setTenantId(province);
		sysTimerDto.setShardingId(shardingId);
		if(flag==0){
			return taskService.insetTask(sysTimerDto);
		}else{
			return taskService.updateTask(sysTimerDto);
		}
		
	}
	
	@ApiOperation(value = "删除定时任务")
	@RequestMapping(value="/deleteTask.do",method= RequestMethod.POST)
	public String deleteTime(String jsessionId,int timerId){
		Map<String , String > loginMap = getLoginInfoService.getLoginInfo(jsessionId);
		String province=loginMap.get("tenantId");
		int shardingId= Integer.parseInt(loginMap.get("shardingId"));
		return taskService.deleteTask(province,shardingId,timerId);
		
		
	}
	
	@ApiOperation(value = "停止单个定时任务")
	@RequestMapping(value="/stopTask.do",method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String stopSingleTask(String taskString,HttpServletRequest req) throws Exception{
		try {
			String uri=req.getRequestURI();
			SysTimerDto task = JSONObject.parseObject(taskString, SysTimerDto.class);
			return taskService.stopSingleTask(task,uri.substring(0, uri.lastIndexOf("/")));
		} catch (Exception e) {
			logger.info(e.getMessage());
			return  "fail";
		}
	}
	
	/** 停止所有定时任务 *//*
	@RequestMapping(value="/stopAllTask.do",method=RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String stopAllTasks(String taskStrings,HttpServletRequest req) throws Exception{
		String uri=req.getRequestURI();
		List<SysTimerDto> tasks = JSONObject.parseArray(taskStrings,SysTimerDto.class);
		return taskService.stopAllTasks(tasks,uri.substring(0, uri.lastIndexOf("/")));
	}*/
	

	@ApiOperation(value = "单个启动定时任务")
	@RequestMapping(value="/startTask.do",method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String startJobController(String taskString,HttpServletRequest req) throws Exception{
		try {
			String uri=req.getRequestURI();
			SysTimerDto task =JSONObject.parseObject(taskString, SysTimerDto.class);
			return  taskService.startSingleTask(task,uri.substring(0, uri.lastIndexOf("/")));
		} catch (Exception e) {
			return  "fail";
		}
	}
	
	/** 启动所定时任务 *//*
	@RequestMapping(value="/startAllTask.do",method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String startAllTasks(String taskStrings,HttpServletRequest req) throws Exception{
		String uri=req.getRequestURI();
		List<SysTimerDto> tasks = JSONObject.parseArray(taskStrings,SysTimerDto.class);
		return taskService.startAllTasks(tasks,uri.substring(0, uri.lastIndexOf("/")));
	}*/
	
	/** 远程启动本地定时任务 */
	@ApiOperation(value = "远程启动本地定时任务")
	@RequestMapping(value="/startTask.refresh",method=RequestMethod.POST ,produces="text/json;charset=UTF-8")
	public void startLocalJobController(HttpServletRequest request,String taskString,PrintWriter writer) throws Exception{
		SysTimerDto task = JSONObject.parseObject(taskString, SysTimerDto.class);
	    Map<String,Object> map =quartzService.startJob(task);
	    if((Boolean) map.get("flag")){
	    	writer.write("succ");
	    	
	    }else{
	    	writer.write((String)map.get("failCause"));
	    }
	}
	
	/** 远程停止本地定时任务 */
	@ApiOperation(value = "远程停止本地定时任务")
	@RequestMapping(value="/stopTask.refresh",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	public void stopLocalJobController(String taskString,PrintWriter writer) throws Exception{
		SysTimerDto task = JSONObject.parseObject(taskString, SysTimerDto.class);
	    Map<String,Object> map =quartzService.stopJob(task);    
	    if((Boolean) map.get("flag")){
	    	writer.write("succ");
	    	
	    }else{
	    	writer.write((String)map.get("failCause")); 
	    }
	}
	
}
