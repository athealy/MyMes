package com.jacksoft.core;

import com.jacksoft.dao.LineinfoDao;
import com.jacksoft.dao.MpcDao;
import com.jacksoft.dao.TaskDao;
import com.jacksoft.entity.Lineinfo;
import com.jacksoft.entity.Manufacture;
import com.jacksoft.entity.Product;
import com.jacksoft.entity.Task;
import com.jacksoft.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.*;

@Slf4j
@Component
public class PlanManager {

    @Resource
    private MpcDao mpcDao;

    @Resource
    private LineinfoDao lineinfoDao;

    @Resource
    private TimeUtil timeUtil;

    @Resource
    private TaskDao taskDao;

    /**
     * 执行自动排产
     * @return
     * @throws Exception
     */
    public boolean toEditPlan(Product product, String bgdate, String classes,
                              int plan, String[] lines,Boolean change, String eddate,String timezone ) throws Exception{
        boolean result = false;
        if(product.actuality>0){
            if(product.finish<product.actuality){
                if(plan>0){
                    List<Task> tasks    =   null;
                    String tmpProduct   =   "-";
                    int    curdate      =   -1; //
                    int    workType     =   -1;
                    int    iAllotment   =   product.actuality - product.finish;
                    int    iTimeZone    =   null!=timezone ? Integer.parseInt(timezone) : 1; //实际执行时段
                    String curLine      =   null;
                    int    line_index   =   0;
                    if(iTimeZone >=  12) {
                        workType = 2;
                    }else{
                        workType = 1;
                    }
                    if(classes.equals("3")){
                        tasks   =   taskDao.quyTaskOnly("1",null,product.department);
                        List<Task> nightTasks   =    taskDao.quyTaskOnly("2",null, product.department);
                        tasks.addAll(nightTasks);
                        log.info("当前task:{}",tasks);
                    }else if(classes.equals("2")){
                        tasks = taskDao.quyTaskOnly("2",null, product.department);
                    }else if(classes.equals("1")){
                        tasks = taskDao.quyTaskOnly("1",null, product.department);
                    }
                    curdate = Integer.parseInt(bgdate);

                    if(null!=tasks){
                        while(iAllotment > 0){ //如果排产数没有分配完成之前继续循环
                            if(null!=eddate && eddate.length()>0 && curdate>Integer.parseInt(eddate)) break; //如果日期已经超出指定的日期停止循环
                            if(iTimeZone >=  12) {  //动态切换班制
                                workType = 2;
                            }else{
                                workType = 1;
                            }
                            curLine = lines[line_index];
                            if(iAllotment > plan){  //判断是否是最后一轮分配
                                if(change && iAllotment == product.actuality - product.finish
                                   && tmpProduct.equals("-")){
                                    //插入转线的排产记录
                                    Manufacture manufacture = new Manufacture();
                                    manufacture.setLine(curLine);
                                    manufacture.setBatch(tmpProduct);
                                    //manufacture.setProduct(tmpProduct);
                                    manufacture.setProduct(product.id);
                                    manufacture.setClasses(workType+"");
                                    manufacture.setPlan(0);
                                    manufacture.setDepartment(product.department);
                                    manufacture.setMfg(curdate+"");
                                    manufacture.setTimezone(iTimeZone+"");
                                    List<Manufacture> tmpList = new ArrayList<>();
                                    tmpList.add(manufacture);

                                    if(mpcDao.insMpc(tmpList)>0){
                                        //执行时段往前推进一步，如果是两班制的话当遇到22时需要重置成1同时班制重置为1，如果是夜班制时遇到22重置为12，如果是白班制遇到11重置为1；
                                        switch (classes){
                                            case "1":
                                                if(iTimeZone+1==12){
                                                    iTimeZone = 1;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                           line_index++;
                                                        }else{
                                                           line_index = 0;
                                                           //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                           curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                            case "2":
                                                if(iTimeZone+1==23){
                                                    iTimeZone = 12;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                            case "3":
                                                if(iTimeZone+1==23){
                                                    iTimeZone = 12;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                        }
                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                        tmpProduct = product.id;
                                        continue;
                                    }else{
                                        log.error("新增转换计划出现异常,订单:{}",product);
                                        throw new Exception();
                                    }
                                }else{
                                    //插入生产计划
                                    Manufacture manufacture = new Manufacture();
                                    manufacture.setLine(curLine);
                                    manufacture.setBatch(product.batch);
                                    manufacture.setProduct(product.id);
                                    manufacture.setClasses(workType+"");
                                    manufacture.setPlan(plan);
                                    manufacture.setDepartment(product.department);
                                    manufacture.setMfg(curdate+"");
                                    manufacture.setTimezone(iTimeZone+"");
                                    List<Manufacture> tmpList = new ArrayList<>();
                                    tmpList.add(manufacture);

                                    if(mpcDao.insMpc(tmpList)>0){
                                        //执行时段往前推进一步，如果是两班制的话当遇到22时需要重置成1同时班制重置为1，如果是夜班制时遇到22重置为12，如果是白班制遇到11重置为1；
                                        switch (classes){
                                            case "1":
                                                if(iTimeZone+1==12){
                                                    iTimeZone = 1;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                            case "2":
                                                if(iTimeZone+1==22){
                                                    iTimeZone = 12;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                            case "3":
                                                if(iTimeZone+1==23){
                                                    iTimeZone = 12;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                        }
                                        tmpProduct = product.id;
                                    }else{
                                        log.error("新增生产计划出现异常,订单:{}",product);
                                        throw new Exception();
                                    }
                                }
                            }else{ //最后一轮
                                if(change && iAllotment == product.actuality - product.finish
                                   && tmpProduct.equals("-")){
                                    //插入转线的排产记录
                                    Manufacture manufacture = new Manufacture();
                                    manufacture.setLine(curLine);
                                    manufacture.setBatch(tmpProduct);
                                    manufacture.setProduct(product.id);
                                    manufacture.setClasses(workType+"");
                                    manufacture.setPlan(0);
                                    manufacture.setDepartment(product.department);
                                    manufacture.setMfg(curdate+"");
                                    manufacture.setTimezone(iTimeZone+"");
                                    List<Manufacture> tmpList = new ArrayList<>();
                                    tmpList.add(manufacture);

                                    if(mpcDao.insMpc(tmpList)>0){
                                        //执行时段往前推进一步，如果是两班制的话当遇到22时需要重置成1同时班制重置为1，如果是夜班制时遇到22重置为12，如果是白班制遇到11重置为1；
                                        switch (classes){
                                            case "1":
                                                if(iTimeZone+1==12){
                                                    iTimeZone = 1;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                            case "2":
                                                if(iTimeZone+1==22){
                                                    iTimeZone = 12;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                            case "3":
                                                if(iTimeZone+1==23){
                                                    iTimeZone = 12;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                        }
                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                        tmpProduct = product.id;
                                        continue;
                                    }else{
                                        log.error("新增转换计划出现异常,订单:{}",product);
                                        throw new Exception();
                                    }
                                }else{
                                    //插入生产计划
                                    Manufacture manufacture = new Manufacture();
                                    manufacture.setLine(curLine);
                                    manufacture.setBatch(product.batch);
                                    manufacture.setProduct(product.id);
                                    manufacture.setClasses(workType+"");
                                    manufacture.setPlan(iAllotment);
                                    manufacture.setDepartment(product.department);
                                    manufacture.setMfg(curdate+"");
                                    manufacture.setTimezone(iTimeZone+"");
                                    List<Manufacture> tmpList = new ArrayList<>();
                                    tmpList.add(manufacture);

                                    if(mpcDao.insMpc(tmpList)>0){
                                        //执行时段往前推进一步，如果是两班制的话当遇到22时需要重置成1同时班制重置为1，如果是夜班制时遇到22重置为12，如果是白班制遇到11重置为1；
                                        switch (classes){
                                            case "1":
                                                if(iTimeZone+1==12){
                                                    iTimeZone = 1;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                            case "2":
                                                if(iTimeZone+1==23){
                                                    iTimeZone = 12;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                            case "3":
                                                if(iTimeZone+1==23){
                                                    iTimeZone = 12;
                                                    //判断生产线是否需要切换；
                                                    if(lines.length>1){
                                                        if(line_index<lines.length){
                                                            line_index++;
                                                        }else{
                                                            line_index = 0;
                                                            //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                            curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                        }
                                                    }else{
                                                        //判断日期是否需要往前推进一步,如果当前生产线下标小于生产线数组length时不需要换日;
                                                        curdate = Integer.parseInt(timeUtil.getCustmDay(curdate+"",-1));
                                                    }
                                                    curLine = lines[line_index];
                                                }else{
                                                    iTimeZone++;
                                                }
                                                break;
                                        }
                                        tmpProduct = product.id;
                                    }else{
                                        log.error("新增生产计划出现异常,订单:{}",product);
                                        throw new Exception();
                                    }
                                    break;
                                }
                            }
                            iAllotment = iAllotment - plan;
                        }
                        result = true;
                    }else{
                        log.info("订单:{}-{}没有识别到合适的执行时段",product.id,product.batch);
                    }
                }else{
                    log.info("订单:{}-{}没有指定产能，无法排班",product.id,product.batch);
                }
            }else{
                log.info("订单:{}-{}已经完工",product.id,product.batch);
            }
        }else{
            log.info("订单:{}-{}没有排产计划",product.id,product.batch);
        }
        return result;
    }

    /**
     * 查询指定产线的某日生产线信息
     * @param line
     * @param department
     * @param mfg
     * @return
     */
    public List<Map> getAlldayPlan(String line, String department, String mfg){
        List<Map> result    =    null;
        List<Manufacture> data = mpcDao.quyAllDayPlan(line,department,mfg);
        Map<String,Map<String,String>> scheduleMap = new HashMap<>();
        if(null!=data && !data.isEmpty()){
            result   =   new ArrayList<>();
            for(Manufacture manufacture : data){
               if(!"-".equals(manufacture.getBatch())) {
                   if (scheduleMap.get(manufacture.getProduct()) == null) {
                       Map<String, String> rec = new HashMap<>();
                       rec.put("d1", "0");
                       rec.put("d2", "0");
                       rec.put("d3", "0");
                       rec.put("d4", "0");
                       rec.put("d5", "0");
                       rec.put("d6", "0");
                       rec.put("d7", "0");
                       rec.put("d8", "0");
                       rec.put("d9", "0");
                       rec.put("d10", "0");
                       rec.put("d11", "0");
                       rec.put("n12", "0");
                       rec.put("n13", "0");
                       rec.put("n14", "0");
                       rec.put("n15", "0");
                       rec.put("n16", "0");
                       rec.put("n17", "0");
                       rec.put("n18", "0");
                       rec.put("n19", "0");
                       rec.put("n20", "0");
                       rec.put("n21", "0");
                       rec.put("t22", "0");
                       rec.put("t23", "0");
                       rec.put("t24", "0");
                       rec.put("product", manufacture.getProduct());
                       scheduleMap.put(manufacture.getProduct(), rec);
                   }
                   switch (manufacture.getClasses()) {
                       case "1":
                           scheduleMap.get(manufacture.getProduct()).put("d" + manufacture.getTimezone(), manufacture.getPlan() + "");
                           break;
                       case "2":
                           scheduleMap.get(manufacture.getProduct()).put("n" + manufacture.getTimezone(), manufacture.getPlan() + "");
                           break;
                       case "3":
                           scheduleMap.get(manufacture.getProduct()).put("t" + manufacture.getTimezone(), manufacture.getPlan() + "");
                           break;
                   }
               }
            }
            Iterator it  = scheduleMap.entrySet().iterator();
            while(it.hasNext()){
               Map.Entry entry  = (Map.Entry)it.next();
                result.add((Map<String,String>)entry.getValue());
            }
        }else if(data.isEmpty()){
            log.debug("没有查询到有效的全天计划数据");
            result = new ArrayList<>();
        }
        return result;
    }

}
