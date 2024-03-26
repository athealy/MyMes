package com.jacksoft.core.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 定时任务工厂类
 */
@Slf4j
@Component
public class SchedulerFactory {

    private StdSchedulerFactory factory;

    public Scheduler build() throws Exception{
        if(null==factory) factory   =   new StdSchedulerFactory();
        //设置scheduler实例名称和线程数量
        Properties props = new Properties();
        props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, UUID.randomUUID());
        props.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS,"org.quartz.simpl.SimpleThreadPool");
        props.put(StdSchedulerFactory.PROP_THREAD_POOL_PREFIX+".threadCount",String.valueOf(5));
        factory.initialize(props);
        Scheduler scheduler =   factory.getScheduler();
        log.info("新建的scheduler:{}",scheduler.getSchedulerName());
        return scheduler;
    }

    public Scheduler build(String schedulerName) throws Exception{
        if(null==factory) factory   =   new StdSchedulerFactory();
        //设置scheduler实例名称和线程数量
        Properties props = new Properties();
        props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, schedulerName);
        props.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS,"org.quartz.simpl.SimpleThreadPool");
        props.put(StdSchedulerFactory.PROP_THREAD_POOL_PREFIX+".threadCount",String.valueOf(5));
        factory.initialize(props);
        Scheduler scheduler =   factory.getScheduler();
        log.info("新建的scheduler:{}",scheduler.getSchedulerName());
        return scheduler;
    }

    public Scheduler build(String schedulerName,int threadInt) throws Exception{
        if(null==factory) factory   =   new StdSchedulerFactory();
        //设置scheduler实例名称和线程数量
        Properties props = new Properties();
        props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, schedulerName);
        props.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS,"org.quartz.simpl.SimpleThreadPool");
        props.put(StdSchedulerFactory.PROP_THREAD_POOL_PREFIX+".threadCount",String.valueOf(threadInt));
        factory.initialize(props);
        Scheduler scheduler =   factory.getScheduler();
        log.info("新建的scheduler:{}",scheduler.getSchedulerName());
        return scheduler;
    }

    public Scheduler getScheduler(String schedulerName) throws Exception{
        return factory.getScheduler(schedulerName);
    }

    public Scheduler getScheduler() throws Exception{
        return factory.getScheduler();
    }

    public List<String> names() throws Exception{
        List<String> list = null;
        Collection<Scheduler> schedulers    = factory.getAllSchedulers();
        if(null!=schedulers){
            list    =   new ArrayList<>();
            for(Scheduler scheduler: schedulers){
                list.add(scheduler.getSchedulerName());
            }
        }
        return list;
    }

    public int count() throws Exception{
        return factory.getAllSchedulers().size();
    }

    public boolean remove(String scheName){
        SchedulerRepository repository  =   SchedulerRepository.getInstance();
        return repository.remove(scheName);
    }

}
