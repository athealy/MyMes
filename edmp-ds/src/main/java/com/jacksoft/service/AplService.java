package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.dao.LineinfoDao;
import com.jacksoft.dao.MpcDao;
import com.jacksoft.entity.Lineinfo;
import com.jacksoft.entity.Manufacture;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.ds.DepartLineInfo;
import com.jacksoft.util.TimeUtil;
import com.jacksoft.util.TimezoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 自动化产线信息服务类
 */
@Slf4j
@Service
public class AplService {

    @Resource
    private LineinfoDao lineinfoDao;

    @Resource
    private MpcDao mpcDao;

    @Resource
    private TimezoneUtil timezoneUtil;

    @Resource
    private TimeUtil timeUtil;

    /**
     * 分页查询
     * @param parameters
     * @return
     */
    public PagetationRes queryAplByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String id   =   null;
            String department  = null;
            String state =  null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                state = null!=filters.get("state")?filters.get("state").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  lineinfoDao.accountLineinfo(id,department,state);
            if(result.total>0){
                result.rows =  lineinfoDao.queryLineinfoByPage(id,department,state,row,page);
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

    /**
     * 新增
     * @param parameters
     * @return
     */
    public boolean insert(Object parameters) throws Exception{
        boolean result  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Lineinfo> list = (List<Lineinfo>)paraMap.get("content");
                log.info("将要写入的生产线信息：{}",list);
                int resInt  =   lineinfoDao.insLineinfo(list);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增生产线信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 更新
     * @param parameter
     * @return
     * @throws Exception
     */
    public boolean update(Object parameter) throws Exception{
        boolean result  = false;
        if(null!=parameter){
            Map<String,Object> paraMap  =   JSON.parseObject(parameter.toString(),Map.class);
            if(null!=paraMap.get("filter") && null!=paraMap.get("replacement")){
                Lineinfo filter =   JSON.parseObject(paraMap.get("filter").toString(),Lineinfo.class);
                Lineinfo replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Lineinfo.class);
                if(lineinfoDao.updLineinfo(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新生产线信息操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新生产线信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 删除
     * @param parameter
     * @return
     * @throws Exception
     */
    public boolean delete(Object parameter) throws Exception{
        boolean result  = false;
        if(null!=parameter){
            Map<String,Object> paraMap  =   JSON.parseObject(parameter.toString(),Map.class);
            if(null!=paraMap.get("filter")){
                if(lineinfoDao.delLineinfo((List<Lineinfo>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除生产线信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除生产线信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 查询所有不重复定义的生产线
     * @param parameters
     * @return
     */
    public List<Lineinfo> quyAplOnly(Object parameters) throws Exception{
        List<Lineinfo> result       =   null;
        Map<String,Object> filter   =   null;
        String id   =   null;
        String department  = null;
        String state =  null;
        if(null!=parameters){
            filter = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                state = null!=filters.get("state")?filters.get("state").toString():null;
            }
        }
        result  =  lineinfoDao.quyAplOnly(id,department,state);
        return result;
    }

    /**
     * 分页查询指定部门生产线信息
     * @param parameters
     * @return
     */
    public PagetationRes quyDepartAplByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String id   =   null;
            String department  = null;
            String classes = null;
            String state =  null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                classes = null!=filters.get("classes")?filters.get("classes").toString():timezoneUtil.getClasses();
                state = null!=filters.get("state")?filters.get("state").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  lineinfoDao.accountLineinfo(id,department,state);
            if(result.total>0){
                List<Lineinfo> lines = lineinfoDao.queryLineinfoByPage(id,department,state,row,page);
                String curDate       =  timeUtil.curDateStr("yyyyMMdd");
                String strTimezone   =  timezoneUtil.getTimezoneOfDay(department,null)+"";
                List<DepartLineInfo> rows  =  new ArrayList<>();
                for(Lineinfo line : lines){
                    DepartLineInfo departLineInfo = new DepartLineInfo();
                    departLineInfo.id = line.id;
                    departLineInfo.department = line.department;
                    departLineInfo.linedesc   = line.linedesc;
                    departLineInfo.state      = line.state;
                    Manufacture mpc = mpcDao.quyMakingProduct(line.getId(),curDate,classes,strTimezone);
                    if(null!=mpc){
                        departLineInfo.making = mpc.getProduct();
                    }else{
                        departLineInfo.making = "";
                    }
                    rows.add(departLineInfo);
                }
                result.rows = rows;
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

    /**
     * 绘制生产线拓扑
     * @param parameters
     * @return
     * @throws Exception
     */
    public List<Map> quyAplTopo(Object parameters) throws Exception{
        List<Map> result = null;
        Map<String,Object> filter   =   null;
        String id   =   null;
        String department  = null;
        String state =  null;
        if(null!=parameters){
            filter = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                state = null!=filters.get("state")?filters.get("state").toString():null;
            }
        }
        List<Lineinfo> data  =  lineinfoDao.quyAplOnly(id,department,state);
        if(null!=data && !data.isEmpty())
        {
            result  =   new ArrayList<>();
            Map<String,List> tempLine = new HashMap<>();
            for(Lineinfo line : data){
                if(null==tempLine.get(line.department)){
                    tempLine.put(line.department,new ArrayList<>());
                    Map<String,Object> topo = new HashMap<>();
                    topo.put("name",line.id);
                    topo.put("value",line.state);
                    tempLine.get(line.department).add(topo);
                }else{
                    Map<String,Object> topo = new HashMap<>();
                    topo.put("name",line.id);
                    topo.put("value",line.state);
                    tempLine.get(line.department).add(topo);
                }
            }
            Iterator<Map.Entry<String,List>> it = tempLine.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,List> entry = it.next();
                Map<String,Object> point     = new HashMap<>();
                point.put("name",entry.getKey());
                point.put("children",entry.getValue());
                result.add(point);
            }
        }else{
            log.info("产线信息没有定义");
        }
        return result;
    }

    /**
     * 绘制生产线拓扑
     * @param parameters
     * @return
     * @throws Exception
     */
    public List<Map> quyRelationTopo(Object parameters) throws Exception{
        List<Map> result = null;
        Map<String,Object> filter   =   null;
        String id   =   null;
        String department  = null;
        String state =  null;
        if(null!=parameters){
            filter = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                state = null!=filters.get("state")?filters.get("state").toString():null;
            }
        }
        List<Lineinfo> data  =  lineinfoDao.quyAplOnly(id,department,state);
        if(null!=data && !data.isEmpty())
        {
            result                      = new ArrayList<>();
            Map<String,List> tempLine   = new HashMap<>();
            String mfg                  = timeUtil.curDateStr("YYYYMMDD");
            for(Lineinfo line : data){
                if(null==tempLine.get(line.department)){
                    tempLine.put(line.department,new ArrayList<>());
                    Map<String,Object> topo = new HashMap<>();
                    topo.put("name",line.id);
                    topo.put("children",new ArrayList<>());
                    List<Manufacture> manufactures = mpcDao.quyProdByLineMfg(line.id,mfg);
                    if(null!=manufactures && !manufactures.isEmpty()){
                        for(Manufacture manufacture : manufactures){
                            Map<String,Object> leave = new HashMap<>();
                            leave.put("name",manufacture.getProduct()+"-"+manufacture.getBatch());
                            leave.put("value",manufacture.getOutput());
                            ((List)topo.get("children")).add(leave);
                        }
                    }
                    tempLine.get(line.department).add(topo);
                }else{
                    Map<String,Object> topo = new HashMap<>();
                    topo.put("name",line.id);
                    topo.put("children",new ArrayList<>());
                    List<Manufacture> manufactures = mpcDao.quyProdByLineMfg(line.id,mfg);
                    if(null!=manufactures && !manufactures.isEmpty()){
                        for(Manufacture manufacture : manufactures){
                            Map<String,Object> leave = new HashMap<>();
                            leave.put("name",manufacture.getProduct()+"-"+manufacture.getBatch());
                            leave.put("value",manufacture.getOutput());
                            ((List)topo.get("children")).add(leave);
                        }
                    }
                    tempLine.get(line.department).add(topo);
                }
            }
            Iterator<Map.Entry<String,List>> it = tempLine.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,List> entry = it.next();
                Map<String,Object> point     = new HashMap<>();
                point.put("name",entry.getKey());
                point.put("children",entry.getValue());
                result.add(point);
            }
        }else{
            log.info("产线信息没有定义");
        }
        return result;
    }

}
