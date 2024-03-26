package com.jacksoft.core.line;

import com.jacksoft.core.Task;
import com.jacksoft.core.quartz.QuartzFactory;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;

import javax.xml.bind.ValidationException;

/**
 * 生产线状态定时任务指针生成类
 */
@Slf4j
public class LineStateTask implements Task {

    private String jobid   = "Analysis";
    private String cronStr = "0 */5 * * * ? ";

    //构造函数
    public LineStateTask(){

    }
    public LineStateTask(String engineName){
        this.jobid   = engineName + this.jobid;
    }
    public LineStateTask(String engineName, String cronStr){
        this.jobid   = engineName + this.jobid;
        this.cronStr = cronStr;
    }

    /**
     * 生成生产线状态定时任务指针
     * @param applicationContext
     * @return
     * @throws Exception
     */
    @Override
    public Scheduler build(ApplicationContext applicationContext) throws Exception {
        Scheduler scheduler = null;
        if(null!=applicationContext){
            if(CronExpression.isValidExpression(cronStr)) {
                QuartzFactory factory = applicationContext.getBean(QuartzFactory.class);
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("applicationContext", applicationContext);
                JobDetail job = factory.makeJobDetail(jobid, LineStatJob.class, jobDataMap);
                Trigger trigger = factory.makeTrigger(jobid, cronStr);
                scheduler = factory.makeSche(job, trigger);
            }else{
                throw new ValidationException("cron",cronStr);
            }
        }else{
            log.error("创建定时任务异常，缺少applicationContext");
            throw new NullPointerException();
        }
        return scheduler;
    }

    @Override
    public String getId() {
        return jobid;
    }
}
