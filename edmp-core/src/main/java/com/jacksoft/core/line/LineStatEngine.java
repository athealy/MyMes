package com.jacksoft.core.line;

import com.jacksoft.core.Engine;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 生产线数据分析引擎
 * @since 2024/01/17
 */
@Slf4j
@Component
public class LineStatEngine implements Engine {

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
    public void start() throws Exception {
        LineStateTask lineStateTask = new LineStateTask(engineId);
        scheduler = lineStateTask.build(applicationContext);
        scheduler.start();
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
