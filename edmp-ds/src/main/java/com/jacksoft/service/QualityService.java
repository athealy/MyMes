package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.dao.MpcDao;
import com.jacksoft.dao.ProductDao;
import com.jacksoft.dao.QualityDao;
import com.jacksoft.entity.Manufacture;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.Product;
import com.jacksoft.entity.ds.ProductAccData;
import com.jacksoft.entity.ds.Quality;
import com.jacksoft.util.TimeUtil;
import com.jacksoft.util.TimezoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品信息服务类
 */
@Slf4j
@Service
public class QualityService {

    @Resource
    private QualityDao qualityDao;

    /**
     * 分页查询
     * @param parameters
     * @return
     */
    public PagetationRes queryQualityByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String product   =   null;
            String department  = null;
            String sdate =  null;
            String stime =  null;
            String batch =  null;

            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                product  =   null!=filters.get("product")?filters.get("product").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                sdate = null!=filters.get("sdate")?filters.get("sdate").toString():null;
                stime = null!=filters.get("stime")?filters.get("stime").toString():null;
                batch = null!=filters.get("batch")?filters.get("batch").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  qualityDao.countQuality(product,department,sdate,stime,batch);
            if(result.total>0){
                result.rows =  qualityDao.queryQulityByPage(product,department,sdate,stime,batch,row,page);
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
                List<Quality> list = (List<Quality>)paraMap.get("content");
                log.info("将要写入的测试信息：{}",list);
                int resInt  =  qualityDao.insQulity(list);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增测试信息不能使用空指针参数");
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
                Quality filter =   JSON.parseObject(paraMap.get("filter").toString(),Quality.class);
                Quality replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Quality.class);
                if(qualityDao.updQulity(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新测试信息操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新测试信息不能使用空指针参数");
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
                if(qualityDao.delQulity((List<Quality>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除测试信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除测试信息不能使用空指针参数");
        }
        return result;
    }

}
