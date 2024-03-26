package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.dao.MpcDao;
import com.jacksoft.dao.ProductDao;
import com.jacksoft.dao.RepairDao;
import com.jacksoft.entity.Manufacture;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.Product;
import com.jacksoft.entity.ds.ProductAccData;
import com.jacksoft.entity.ds.Repair;
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
public class RepairService {

    @Resource
    private RepairDao repairDao;

    /**
     * 分页查询
     * @param parameters
     * @return
     */
    public PagetationRes queryRepairByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String product      =  null;
            String department   =  null;
            String sdate        =  null;
            String barcode      =  null;
            String batch        =  null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                product  =   null!=filters.get("product")?filters.get("product").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                sdate = null!=filters.get("sdate")?filters.get("sdate").toString():null;
                barcode = null!=filters.get("barcode")?filters.get("barcode").toString():null;
                batch = null!=filters.get("batch")?filters.get("batch").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  repairDao.countRepair(product,department,sdate,barcode,batch);
            if(result.total>0){
                result.rows =  repairDao.queryRepairByPage(product,department,sdate,barcode,batch,row,page);
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
                List<Repair> list = (List<Repair>)paraMap.get("content");
                log.info("将要写入的维修信息：{}",list);
                int resInt  =   repairDao.insRepair(list);
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
                Repair filter =   JSON.parseObject(paraMap.get("filter").toString(),Repair.class);
                Repair replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Repair.class);

                if(repairDao.updRepair(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新维修信息操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新维修信息不能使用空指针参数");
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
                if(repairDao.delRepair((List<Repair>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除维修信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除维修信息不能使用空指针参数");
        }
        return result;
    }

}
