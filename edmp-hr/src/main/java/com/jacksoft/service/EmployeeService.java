package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.dao.EmployeeDao;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.hr.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自动化产线信息服务类
 */
@Slf4j
@Service
public class EmployeeService {

    @Resource
    private EmployeeDao employeeDao;

    /**
     * 分页查询
     * @param parameters
     * @return
     */
    public PagetationRes queryEmployeeByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String id   =   null;
            String username  = null;
            String duties =  null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                username = null!=filters.get("username")?filters.get("username").toString():null;
                duties = null!=filters.get("duties")?filters.get("duties").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  employeeDao.countEmployee(id,username,duties);
            if(result.total>0){
                result.rows =  employeeDao.queryEmployeeByPage(id,username,duties,row,page);
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
                List<Employee> list = (List<Employee>)paraMap.get("content");
                log.info("将要写入的职员信息：{}",list);
                int resInt  =   employeeDao.insEmployee(list);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增职员信息不能使用空指针参数");
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
                Employee filter =   JSON.parseObject(paraMap.get("filter").toString(),Employee.class);
                Employee replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Employee.class);

                if(employeeDao.updEmployee(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新职员信息操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新职员信息不能使用空指针参数");
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
                if(employeeDao.delEmployee((List<Employee>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除职员信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除职员信息不能使用空指针参数");
        }
        return result;
    }

}
