package com.jacksoft.core.plan;

import com.jacksoft.core.Engine;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class PlanStatEngine implements Engine {

    private String engineId = "LineEngine";
    private Scheduler scheduler;
    private String cronStr;

    @Resource
    private ApplicationContext applicationContext;

    public void setCronStr(String cronStr){
        this.cronStr    =   cronStr;
    }
    public String getCronStr(){
        return this.cronStr;
    }

    @Override
    public void start() throws Exception{

    }

    @Override
    public void stop() throws Exception{
        if(null!=scheduler){
            scheduler.pauseAll();
            scheduler.shutdown();
            scheduler.clear();
            scheduler = null;
        }
    }
}
