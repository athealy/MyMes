package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.dao.DepartmentDao;
import com.jacksoft.dao.LineinfoDao;
import com.jacksoft.entity.Department;
import com.jacksoft.entity.Lineinfo;
import com.jacksoft.entity.PagetationRes;
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
public class DepartService {

    @Resource
    private DepartmentDao departmentDao;

    /**
     * 分页查询
     * @param parameters
     * @return
     */
    public PagetationRes queryDepartByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String id   =   null;
            String name  = null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id   =   null!=filters.get("id")?filters.get("id").toString():null;
                name =   null!=filters.get("name")?filters.get("name").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  departmentDao.countDepart(id,name);
            if(result.total>0){
                result.rows =  departmentDao.queryDepartByPage(id,name,row,page);
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
                List<Department> list = (List<Department>)paraMap.get("content");
                log.info("将要写入的部门信息：{}",list);
                int resInt  =   departmentDao.insDepart(list);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增部门信息不能使用空指针参数");
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
                Department filter         =   JSON.parseObject(paraMap.get("filter").toString(),Department.class);
                Department replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Department.class);

                if(departmentDao.updDepart(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新部门信息操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新部门信息不能使用空指针参数");
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
                if(departmentDao.delDepart((List<Department>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除部门信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除部门信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 查询所有的部门
     * @param parameters
     * @return
     */
    public List<Department> quyDepartOnly(Object parameters){
        List<Department> result     =   null;
        Map<String,Object> filter   =   null;
        String id    = null;
        String name  = null;
        if(null!=parameters){
            filter = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id   =  null!=filters.get("id")?filters.get("id").toString():null;
                name =  null!=filters.get("name")?filters.get("name").toString():null;
            }
        }
        result  =  departmentDao.quyDepartOnly(id,name);
        return result;
    }

}
