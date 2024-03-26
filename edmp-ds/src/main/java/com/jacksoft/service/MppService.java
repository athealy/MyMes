package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.core.runnable.ProductRunnable;
import com.jacksoft.dao.CapacityInfoDao;
import com.jacksoft.dao.MaterialDao;
import com.jacksoft.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 生产参数管理类
 */
@Slf4j
@Service
public class MppService {

    @Resource
    private CapacityInfoDao capacityInfoDao;

    @Resource
    private MaterialDao materialDao;

    /**
     * 新增生产参数
     * @param parameters
     * @return
     */
    public boolean insCapacityInfo(Object parameters){
        boolean result  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Capacity> list = (List<Capacity>)paraMap.get("content");
                log.info("将要写入的生产参数信息：{}",list);
                int resInt  =   capacityInfoDao.insCapacityInfo(list);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增生产参数信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 批量删除生产参数
     * @param parameters
     * @return
     */
    public boolean delCapacityInfo(Object parameters){
        boolean result  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  =   JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("filter")){
                if(capacityInfoDao.delCapacityInfo((List<Capacity>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除生产参数信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除生产参数信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 更新生产参数
     * @param parameters
     * @return
     */
    public boolean updCapacityInfo(Object parameters){
        boolean result  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  =   JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("filter") && null!=paraMap.get("replacement")){
                Capacity filter         =   JSON.parseObject(paraMap.get("filter").toString(),Capacity.class);
                Capacity replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Capacity.class);
                if(capacityInfoDao.updCapacityInfo(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新生产参数信息操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新生产参数信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 分页查询产能信息
     * @param parameters
     * @return
     */
    public PagetationRes queryCapacityByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String product      =   null;
            String department   =   null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                product = null!=filters.get("product")?filters.get("product").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  capacityInfoDao.countCapacityInfo(product,department);
            if(result.total>0){
                result.rows =  capacityInfoDao.queryCapacityByPage(product,department,row,page);
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

    /**
     * 查询生产参数中的产品标识
     * @param parameters
     * @return
     * @throws Exception
     */
    public List<Capacity> quyProductInCapa(Object parameters) throws Exception {
        List<Capacity> result    =   null;
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            String department   =   null;
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                department = null!=filters.get("department")?filters.get("department").toString():null;
            }
            result = capacityInfoDao.quyProdOnly(department);
        }else{
            log.debug("参数不全，无法正常查询生产参数中的产品标识");
        }
        return result;
    }

    /**
     * 新增物料
     * @param parameters
     * @return
     */
    public boolean insMaterial(Object parameters){
        boolean result  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Material> list = (List<Material>)paraMap.get("content");
                log.info("将要写入的物料清单信息：{}",list);
                int resInt  =   materialDao.insMaterial(list);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增物料清单信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 批量删除物料清单
     * @param parameters
     * @return
     */
    public boolean delMaterial(Object parameters){
        boolean result  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  =   JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("filter")){
                if(materialDao.delMaterial((List<Material>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除物料清单信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除物料清单信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 更新物料清单信息
     * @param parameters
     * @return
     */
    public boolean updMaterial(Object parameters){
        boolean result  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  =   JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("filter") && null!=paraMap.get("replacement")){
                Material filter         =   JSON.parseObject(paraMap.get("filter").toString(),Material.class);
                Material replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Material.class);
                if(materialDao.updMaterial(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新物料清单信息操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新物料清单信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 分页查询物料信息
     * @param parameters
     * @return
     */
    public PagetationRes queryMaterialByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String product      =   null;
            String department   =   null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                product = null!=filters.get("product")?filters.get("product").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  materialDao.countMaterial(product);
            if(result.total>0){
                result.rows =  materialDao.queryMaterialByPage(product,row,page);
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

    /**
     * 查询指定产品标识的等效元件数
     * @param parameters
     * @return
     * @throws Exception
     */
    public List<Material> quyEqualNum(Object parameters) throws Exception {
        List<Material> result = null;
        if(null!=parameters) {
            Map<String, Object> filter = JSON.parseObject(parameters.toString(), Map.class);
            if(null!=filter.get("filters")){
                List<Material> filters  =   (List<Material>)filter.get("filters");   //这里可能有个BUG
                result = materialDao.quyEqualNum(filters);
            }else {
                log.info("查询参数不合法,无法执行查询操作");
            }
        }else{
            log.info("缺少查询物料等效元件数的必要参数,无法查询到等效元件");
        }
        return result;
    }

    /**
     * 导入产能物料操作
     * @param parameters
     * @return
     * @throws Exception
     */
    public boolean impMafuPara(Object parameters) throws Exception{
        boolean result  =   false;
        if(null!=parameters) {
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            String fileType             =   null;
            String fileName             =   null;
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                fileType = null!=filters.get("fileType")?filters.get("fileType").toString():null;
                fileName = null!=filters.get("fileName")?filters.get("fileName").toString():null;
            }
            if(null!=fileType || null!=fileName){
                switch(fileType){
                    case "txt" :
                        break;
                    case "xls" :
                        break;
                    case "xlsx" :
                        break;
                }
            }else{
                throw new NullPointerException();
            }
        }else{
            log.info("缺少导入产能物料信息操作的必要参数,操作失败");
        }
        return result;
    }

}
