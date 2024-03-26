package com.jacksoft.core.line;

import com.jacksoft.dao.LineinfoDao;
import com.jacksoft.dao.MpcDao;
import com.jacksoft.entity.Lineinfo;
import com.jacksoft.entity.Manufacture;
import com.jacksoft.util.TimeUtil;
import com.jacksoft.util.TimezoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 分析所有产线当天是否有安排生产计划
 */
@Slf4j
@DisallowConcurrentExecution
public class LineStatJob implements Job {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if(null!=applicationContext){
            TimeUtil timeUtil           =  applicationContext.getBean(TimeUtil.class);
            LineinfoDao lineinfoDao     =  applicationContext.getBean(LineinfoDao.class);
            MpcDao mpcDao               =  applicationContext.getBean(MpcDao.class);
            TimezoneUtil timezoneUtil   =  applicationContext.getBean(TimezoneUtil.class);
            String mfg              =  timeUtil.curDateStr("yyyyMMdd");
            List<Lineinfo> lines    =  lineinfoDao.quyActivity();
            log.info("分析生产线状态执行时间:{}",timeUtil.curTimeStamp());
            if(null!=lines && !lines.isEmpty()){
                for(Lineinfo line : lines){
                    Lineinfo replacement = new Lineinfo();
                    replacement.id  = line.id;
                    replacement.department  = line.department;
                    replacement.linedesc    = line.linedesc;
                    replacement.state       = line.state;
                    try {
                        String classes  =  timezoneUtil.getClasses();
                        int timezone =  timezoneUtil.getTimezone("SMD",null,classes);
                        //log.info("当前班制:{}",classes);
                        //log.info("当前执行时段:{}",timezone);
                        //log.info("当前时点:{}",mfg);
                        //log.info("当前生产线:{}",line.id);
                        List<Manufacture> manufactures = mpcDao.queryMpcByPage(null,line.department,null,classes,timezone+"",line.id,mfg,null,20,1);
                        if(null==manufactures || manufactures.isEmpty()){
                            replacement.state = "2";
                        }else{
                            //if(manufactures.get(0).getProduct().equals("-")){
                            if(manufactures.get(0).getBatch().equals("-")){
                                replacement.state = "4";
                            }else{
                                replacement.state = "1";
                            }
                        }
                        if(lineinfoDao.updLineinfo(line,replacement)!=1) {
                            log.error("生产线:{}状态更新失败",replacement);
                        }
                    } catch (Exception e) {
                        log.error("更新生产线:{}状态发生异常",line);
                    }
                }
            }else{
                log.debug("当前没有需要分析状态的生产线数据");
            }
        }
    }
}
