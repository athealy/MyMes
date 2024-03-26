package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.dao.TaskDao;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门信息服务类
 */
@Slf4j
@Service
public class TaskService {

    @Resource
    private TaskDao taskDao;

    /**
     * 分页查询
     * @param parameters
     * @return
     */
    public PagetationRes queryTaskByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String workType     = null;
            String serial       = null;
            String department       = null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                workType    =   null!=filters.get("workType")?filters.get("workType").toString():null;
                serial      =   null!=filters.get("serial")?filters.get("serial").toString():null;
                department  =   null!=filters.get("department")?filters.get("department").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  taskDao.countTask(workType,serial,department);
            if(result.total>0){
                result.rows =  taskDao.queryTaskByPage(workType,serial,department,row,page);
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
                List<Task> list = (List<Task>)paraMap.get("content");
                log.info("将要写入的部门信息：{}",list);
                int resInt  =   taskDao.insTask(list);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增生产时段不能使用空指针参数");
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
                Task filter         =   JSON.parseObject(paraMap.get("filter").toString(),Task.class);
                Task replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Task.class);
                if(taskDao.updTask(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新生产时段操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新生产时段不能使用空指针参数");
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
                if(taskDao.delTask((List<Task>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除生产时段");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除生产时段不能使用空指针参数");
        }
        return result;
    }

    /**
     * 查询所有的生产时段
     * @param parameters
     * @return
     */
    public List<Task> quyTaskOnly(Object parameters){
        List<Task> result     =   null;
        Map<String,Object> filter   =   null;
        String workType     = null;
        String serial       = null;
        String department       = null;
        if(null!=parameters){
            filter = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                workType    =  null!=filters.get("workType")?filters.get("workType").toString():null;
                serial      =  null!=filters.get("serial")?filters.get("serial").toString():null;
                department  =  null!=filters.get("department")?filters.get("department").toString():null;
            }
        }
        result  =  taskDao.quyTaskOnly(workType,serial,department);
        return result;
    }

}
