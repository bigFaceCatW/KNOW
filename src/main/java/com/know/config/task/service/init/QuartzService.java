package com.know.config.task.service.init;

import com.know.config.task.dao.TimeTaskDao;
import com.know.config.task.dto.SysTimerDto;
import com.know.util.CommonUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;


@Service
public class QuartzService implements ServletContextAware {
	public static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	@Resource
	private TimeTaskDao jobTaskDao;
	@Resource
	private TimeTaskService jobTaskService;
	
	private ServletContext servletContext;
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**设置全局的scheduler
	 * @throws InterruptedException */
	@PostConstruct  //用来修饰非静态void方法，在服务器加载servlet时运行并且只会运行一次
	public  void init(){
		try {
			SchedulerFactory schedulerFactory  = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			servletContext.setAttribute("scheduler", scheduler);
//			jobTaskDao.updateJobTask(0,"UNICOM");//服务器重启时重置库表定时器状态，和内存保持一致
		} catch (SchedulerException e) {
			logger.info(e.getMessage());
		}
	}

	
	/**
	 *启动任务 
	 */
	public Map<String,Object> startJob(SysTimerDto jobTaskDto){
		
		//从上下文中获取调度器
		Scheduler scheduler = (Scheduler) servletContext.getAttribute("scheduler");
		
		Boolean flag = true;
		String failCause = "";
		
		Map<String,Object> map = new HashMap<String, Object>(16);
		String jobName = jobTaskDto.getTimerId() + "Job";
		String groupName = jobTaskDto.getTimerId() + "Group";
		String triggerName = jobTaskDto.getTimerId() + "Trigger";
		
		JobDetail job = JobBuilder.newJob(TimerJob.class).withIdentity(jobName, groupName).build();
		job.getJobDataMap().put("jobTask", jobTaskDto);

		CronTrigger trigger = null;
		try {
			//利用框架自身的异常验证cron表达式
			trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(jobTaskDto.getCronExpression())).build();
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			map.put("flag", false);
			map.put("failCause", "非法的cron表达式！");
			return map;
		}
		
		try {
			if(scheduler.checkExists(JobKey.jobKey(jobName, groupName))){
				map.put("flag", false);
				map.put("failCause", "已经被启动！");
				return map;
			}
			scheduler.scheduleJob(job, trigger);
			if(!scheduler.isStarted()){
				scheduler.start();
			}
			
			flag = true;
			failCause = "启动成功！";
			
			//0表示定时任务的当前状态为停止，1表示定时任务的当前状态是运行
			jobTaskDao.updateJobTaskByJobId(jobTaskDto,1);
		} catch (SchedulerException e) {
			logger.info(e.getMessage());
			flag = false;
			failCause = "未知异常，启动失败！";
		}
		
		map.put("flag", flag);
		map.put("failCause", failCause);
		return map;
		
	}

	
	/**
	 * 停止任务
	 */
	public Map<String, Object> stopJob(SysTimerDto jobTaskDto) {

		Scheduler scheduler = (Scheduler) servletContext.getAttribute("scheduler");
		
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean flag = null;
		String failCause = "";
		
		String jobName = jobTaskDto.getTimerId() + "Job";
		String groupName = jobTaskDto.getTimerId() + "Group";
		String triggerName = jobTaskDto.getTimerId() + "Trigger";	
		
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,groupName);
		JobKey jobKey = JobKey.jobKey(jobName, groupName);
		
		try {
			if(!scheduler.checkExists(jobKey)) {
				map.put("flag", false);
				map.put("failCause", "定时器已经被停止，强制修改状态成功！");
				//防止数据库中任务状态与内存中不一致
				jobTaskDao.updateJobTaskByJobId(jobTaskDto,0);
				return map;
			}
			
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 移除触发器
			scheduler.deleteJob(jobKey);// 删除任务
			flag = true;
			failCause = "停止成功！";
			
			//0表示定时任务的当前状态为停止，1表示定时任务的当前状态是运行
			jobTaskDao.updateJobTaskByJobId(jobTaskDto,0);
		} catch (Exception e) {
			logger.info(e.getMessage());
			flag = false;
			failCause = "未知异常，停止失败！";
		}
		
		map.put("flag", flag);
		map.put("failCause", failCause);
		return map;
	}

//	修改
    public void modifyJobTime(SysTimerDto jobTaskDto) {
         try {  
        	 Scheduler scheduler = (Scheduler) servletContext.getAttribute("scheduler"); 
     		 String jobName = jobTaskDto.getTimerId() + "Job";
    		 String groupName = jobTaskDto.getTimerId() + "Group";
    		 String triggerName = jobTaskDto.getTimerId() + "Trigger";	
    		 
    		 TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,groupName);
    		 JobKey jobKey = JobKey.jobKey(jobName, groupName);
             CronTrigger triggerOld = (CronTrigger) scheduler.getTrigger(triggerKey);
             if (triggerOld == null) {
                 return;  
             }
             String oldTime = triggerOld.getCronExpression();  
             if (!oldTime.equalsIgnoreCase(jobTaskDto.getCronExpression())) {
            	 
                 JobDetail job = scheduler.getJobDetail(jobKey);
     			 scheduler.pauseTrigger(triggerKey);// 停止触发器
    			 scheduler.unscheduleJob(triggerKey);// 移除触发器
    			 scheduler.deleteJob(jobKey);// 删除任务

    			 CronTrigger trigger1 = TriggerBuilder.newTrigger().withIdentity(triggerName, groupName)
    						.withSchedule(CronScheduleBuilder.cronSchedule(jobTaskDto.getCronExpression())).build();
    			 
    			 scheduler.scheduleJob(job, trigger1);
    		//	 scheduler.start();
    			 
             }
         } catch (Exception e) {
        	 logger.info(e.getMessage());
         }  
     }  

	
}
