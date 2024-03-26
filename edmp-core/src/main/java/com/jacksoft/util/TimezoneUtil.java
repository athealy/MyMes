package com.jacksoft.util;

import com.jacksoft.dao.TaskDao;
import com.jacksoft.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 时间分
 */
@Slf4j
@Component
public class TimezoneUtil {

    @Resource
    private TaskDao taskDao;

    /**
     * 取指定部门日班
     * @param department
     * @param date
     * @return
     * @throws Exception
     */
    public int getTimezoneOfDay(String department,Date date) throws Exception{
        int intTimeZone = -1;
        int intCurTime;
        SimpleDateFormat sdf  =   new SimpleDateFormat("HHmm");
        if(null!=date){
            intCurTime   = Integer.parseInt(sdf.format(date).length()<6 ? "0"+sdf.format(date) : sdf.format(date));
        }else{
            Date curdate             =   new Date();
            intCurTime   = Integer.parseInt(sdf.format(curdate).length()<6 ? "0"+sdf.format(curdate) : sdf.format(curdate));
        }
        List<Task> tasks  = taskDao.quyTaskOnly(getClasses(),null,department);
        for(Task task : tasks){
            String[] wtz    =  task.getWtz().split("-");
            int left        =  Integer.parseInt(wtz[0].replaceAll(":",""));
            int right       =  Integer.parseInt(wtz[1].replaceAll(":",""));
            if(left<right){
                if(intCurTime>=left && intCurTime<right){
                    intTimeZone = task.getSerial();
                    break;
                }
            }else{
                if(intCurTime>=left || (intCurTime>=0 && intCurTime<right)){
                    intTimeZone = task.getSerial();
                    break;
                }
            }
        }
        return intTimeZone;
    }

    /**
     * 取指定部门日班指定班制
     * @param department
     * @param date
     * @return
     * @throws Exception
     */
    public int getTimezone(String department,Date date,String workType) throws Exception{
        int intTimeZone = -1;
        int intCurTime;
        SimpleDateFormat sdf  =   new SimpleDateFormat("HHmm");
        if(null!=date){
            intCurTime   = Integer.parseInt(sdf.format(date).length()<4 ? "0"+sdf.format(date):sdf.format(date));
        }else{
            Date curdate             =   new Date();
            intCurTime   = Integer.parseInt(sdf.format(curdate).length()<4 ? "0"+sdf.format(curdate):sdf.format(curdate));
        }
        List<Task> tasks  = taskDao.quyTaskOnly(workType,null,department);
        for(Task task : tasks){
            String[] wtz    =  task.getWtz().split("-");
            int left        =  Integer.parseInt(wtz[0].replaceAll(":",""));
            int right       =  Integer.parseInt(wtz[1].replaceAll(":",""));
            if(left<right){
                if(intCurTime>=left && intCurTime<right){
                    intTimeZone = task.getSerial();
                    break;
                }
            }else{
                if(intCurTime>=left || (intCurTime>=0 && intCurTime<right)){
                    intTimeZone = task.getSerial();
                    break;
                }
            }
        }
        return intTimeZone;
    }

    /**
     * 取当前时间的班制
     * @return
     */
    public String getClasses(){
        String result = null;
        Date date             =   new Date();
        SimpleDateFormat sdf  =   new SimpleDateFormat("HHmm");
        if(Integer.parseInt(sdf.format(date).length()<4 ? "0"+sdf.format(date):sdf.format(date))>=800
                && Integer.parseInt(sdf.format(date).length()<4 ? "0"+sdf.format(date):sdf.format(date))<2030){
            result  =   "1";
        }else{
            result  =   "2";
        }
        return result;
    }

}
