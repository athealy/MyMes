package com.jacksoft.core.plan;

import com.jacksoft.core.Task;
import com.jacksoft.core.line.LineStatJob;
import com.jacksoft.core.quartz.QuartzFactory;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;

/**
 * 生产计划定时任务指针生成类
 */
@Slf4j
public class PlanTask implements Task {

    private String jobid   = "Analysis";
    private String cronStr = "*/30 * * * * ? ";

    //构造函数
    public PlanTask(){

    }
    public PlanTask(String engineName){
        this.jobid   = engineName + this.jobid;
    }
    public PlanTask(String engineName, String cronStr){
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
            QuartzFactory factory   =   applicationContext.getBean(QuartzFactory.class);
            JobDataMap jobDataMap   =   new JobDataMap();
            jobDataMap.put("applicationContext",applicationContext);
            JobDetail job   =   factory.makeJobDetail(jobid, LineStatJob.class,jobDataMap);
            Trigger trigger =   factory.makeTrigger(jobid,cronStr);
            scheduler       =   factory.makeSche(job,trigger);
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
