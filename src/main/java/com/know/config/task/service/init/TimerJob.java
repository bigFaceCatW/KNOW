package com.know.config.task.service.init;

import com.know.config.task.dto.SysTimerDto;
import com.know.util.CommonUtil;
import com.know.util.GlobalApplicationContextHolder;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;




/**
 *现场定时任务，注意事项：
 * 1、下面方法上面的注解不要删除 ，用于保证任务串行执行；
 * 2、引用其他类时不要用@Resource，用下面的方法类的方式（只对service有效）
 * @author lxci
 */


@PersistJobDataAfterExecution //保存在JobDataMap传递的参数
@DisallowConcurrentExecution  //保证多个任务间不会同时执行.所以在多任务执行时最好加上
public class TimerJob  implements Job {
	
	public static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		TimeTaskService jobTaskService = (TimeTaskService) GlobalApplicationContextHolder.getBean("timeTaskService");
		SysTimerDto jobTaskDto = (SysTimerDto) context.getMergedJobDataMap().get("jobTask");
		
		//更新当内存定时器状态和数据库状态不一致时 的数据库状态
		try {
			jobTaskService.updateJobTaskByJobIdService(jobTaskDto);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
			//testService.t(jobTask);
		//利用反射的机制实现定时任务动态配置和调用，即不需要事先写好哪个定时任务调用哪个方法，通过配置实现
			String cls    = jobTaskDto.getClassName();//"usi.pwm.task.service.TestService";//map.get("URL").toString();
			String method = jobTaskDto.getExcuteMethod();//"t";//map.get("INVOKE_METHOD").toString();
			//String beanId = "testService";
			try{
				Class<?>  c   = Class.forName(cls);//生成名称为cls的类对象
				//Object o   = c.newInstance(); 
				 Object bean =GlobalApplicationContextHolder.getBean(c);//获取上下文中的c对象，和加注解类似
				Method m   = c.getMethod(method, new Class[]{Class.forName("com.usi.cmos.config.task.dto.SysTimerDto") });
				m.invoke(bean,jobTaskDto);//调用方法，传入对象和参数
				 
			}catch(Exception e){
				logger.info(e.getMessage());
			}
		//}
	}

}
