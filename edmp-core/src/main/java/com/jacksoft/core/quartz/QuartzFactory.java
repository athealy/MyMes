package com.jacksoft.core.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * 定时任务工厂，处理定时任务的生成和加载
 */
@Slf4j
@Component
public class QuartzFactory {

    @Resource
    private ApplicationContext applicationContext;

    private String defaultCronString    =   "0 */1 * * * ?";

    @Resource
    private SchedulerFactory schedulerFactory;

    /**
     * 生成作业实例
     * @param scheId
     * @return
     */
    public JobDetail makeJobDetail(String scheId, Class<? extends Job> jobClass) throws SchedulerException{
        return makeJobDetail(scheId,jobClass,null);
    }

    /**
     * 生成作业实例
     * @param scheId
     * @param jobDataMap
     * @return
     */
    public JobDetail makeJobDetail(String scheId,Class<? extends Job> jobClass, JobDataMap jobDataMap) throws SchedulerException{
        JobDetail jobDetail =   null;
        if(null!=scheId && scheId.trim().length()>0) {
            if(null!=jobClass) {
                if (null == jobDataMap || jobDataMap.isEmpty()) {
                    jobDataMap = new JobDataMap();
                    jobDataMap.put("applicationContext", applicationContext);
                }
                // 创建job
                jobDetail = JobBuilder.newJob(jobClass)
                        .withIdentity(scheId, scheId + "Group")
                        .usingJobData(jobDataMap)
                        .usingJobData("concurrent", false)           //前序任务未完成不执行后序任务
                        .build();
            }else{
                log.info("缺少执行器实例类定义");
            }
        }else{
            log.info("缺少调度任务标识");
        }
        return jobDetail;
    }

    /**
     * 生成指针实例
     * @param tiggerId
     * @param cronStr
     * @return
     */
    public Trigger makeTrigger(String tiggerId, String cronStr) throws SchedulerException {
        Trigger trigger  =   null;
        if(null!=tiggerId && tiggerId.trim().length()>0){
            if(null!=cronStr && cronStr.trim().length()>0){
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(tiggerId,tiggerId+"Group")
                        .startNow()
                        //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronStr))
                        .build();
            }else{
                log.info("缺少时间定义标识，使用默认时间定义每分钟执行一次");
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(tiggerId,tiggerId+"Group")
                        .startNow()
                        //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                        .withSchedule(CronScheduleBuilder.cronSchedule(defaultCronString))
                        .build();
            }
        }else{
            log.info("缺少指针标识");
        }
        return trigger;
    }

    /**
     * 生成调度实例
     * @param jobDetail
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Scheduler makeSche(JobDetail jobDetail, Trigger trigger) throws Exception{
        Scheduler scheduler =   null;
        if(null!=jobDetail){
            if(null!=trigger){
                scheduler = schedulerFactory.build("AiQuartz");
                if(!scheduler.isStarted())scheduler.start();
                scheduler.scheduleJob(jobDetail,trigger);
            }else{
                log.info("缺少指针实例");
            }
        }else{
            log.info("缺少作业实例");
        }
        return scheduler;
    }

    /**
     * 多执行实例方式生成调度实例
     * @param registMap
     * @param threadCount
     * @return
     * @throws SchedulerException
     */
    public Scheduler makeSche(Map<JobDetail, Set<? extends Trigger>> registMap, int threadCount) throws Exception{
        Scheduler scheduler =   null;
        if(null!=registMap && !registMap.isEmpty()){
            scheduler = schedulerFactory.build("Aiquartz",threadCount);
            if(!scheduler.isStarted())scheduler.start();
            scheduler.scheduleJobs(registMap, true);
        }else{
            log.info("缺少实例Hash，无法生成调度实例");
        }
        return scheduler;
    }

}
