package com.jacksoft.core;

import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;

public interface Task {

    //调度实现生成
    public Scheduler build(ApplicationContext applicationContext) throws Exception;

    //线程id获取
    public String getId();
}
