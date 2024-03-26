package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.dao.*;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.Result;
import com.jacksoft.entity.wms.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 库存管理服务
 */
@Slf4j
@Service
public class WmsService {

    @Resource
    private WarehouseDao warehouseDao;

    @Resource
    private StckingareaDao stckingareaDao;

    @Resource
    private ShelvesDao shelvesDao;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private IcsDao icsDao;

    @Resource
    private IcdDao icdDao;


    /**
     * 产成品入库
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result fgInbound(Object parameters) throws Exception{
        Result result = null;
        return result;
    }

    /**
     * 产成品出库
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result fgOutbound(Object parameters) throws Exception{
        Result result = null;
        return result;
    }

    /**
     * 半成品暂存
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result sgInbound(Object parameters) throws Exception{
        Result result = null;
        return result;
    }

    /**
     * 新建仓库信息
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result insWarehouse(Object parameters) throws Exception {
        Result result   =    null;
        boolean res  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Warehouse> list = (List<Warehouse>)paraMap.get("content");
                log.info("将要写入的仓库信息：{}",list);
                int resInt  =   warehouseDao.insWarehouse(list);
                if(resInt>0){
                    res = true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   res;
                }else if(resInt==0) {
                    res = false;
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有成功插入记录";
                    result.resData  =   0;
                }else{
                    res =false;
                    result  =    new Result();
                    result.mes  =   "操作失败";
                    result.resData  =   -1;
                }
            }else{
                result  =    new Result();
                result.mes  =   "参数格式不合法，无法新增";
                result.resData  =   res;
            }
        }else{
            log.info("新增仓库信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "新增仓库信息不能使用空指针参数";
            result.resData  =   res;
        }
        return result;
    }

    /**
     * 删除仓库信息
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result delWarehouse(Object parameters) throws Exception {
        Result result   =    null;
        boolean res  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Warehouse> list = (List<Warehouse>)paraMap.get("content");
                log.info("将要删除的仓库信息：{}",list);
                int resInt  =   warehouseDao.delWarehouse(list);
                if(resInt>0){
                    res = true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   res;
                }else if(resInt==0) {
                    res = false;
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有成功删除记录";
                    result.resData  =   0;
                }else{
                    res =false;
                    result  =    new Result();
                    result.mes  =   "操作失败";
                    result.resData  =   -1;
                }
            }else{
                result  =    new Result();
                result.mes  =   "参数格式不合法，无法新增";
                result.resData  =   res;
            }
        }else{
            log.info("删除仓库信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "删除仓库信息不能使用空指针参数";
            result.resData  =   res;
        }
        return result;
    }

    /**
     * 更新仓库信息
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result updWarehouse(Object parameters) throws Exception {
        Result result   =    null;
        boolean resData  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  =   JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("filter") && null!=paraMap.get("replacement")){
                Warehouse filter =   JSON.parseObject(paraMap.get("filter").toString(),Warehouse.class);
                Warehouse replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Warehouse.class);

                if(warehouseDao.updWarehouse(filter,replacement)>0){
                    resData  =   true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   resData;
                }else{
                    log.info("更新仓库信息操作失败!");
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有更新数据";
                    result.resData  =   resData;
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
                result  =    new Result();
                result.mes  =   "没有规范使用参数，缺少filter或者replacement";
                result.resData  =   resData;
            }
        }else{
            log.info("更新仓库信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "更新仓库信息不能使用空指针参数";
            result.resData  =   resData;
        }
        return result;
    }

    /**
     * 分页查询
     * @param parameters
     * @return
     */
    public PagetationRes queryWarehouseByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row         =   10;
            int page        =   1;
            String id       =  null;
            String title    =  null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id    =   null!=filters.get("id")?filters.get("id").toString():null;
                title = null!=filters.get("title")?filters.get("title").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  warehouseDao.countWarehouse(id,title);
            if(result.total>0){
                result.rows =  warehouseDao.queryWarehouseByPage(id,title,row,page);
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

    /**
     * 新建堆存区信息
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result insStcking(Object parameters) throws Exception {
        Result result   =    null;
        boolean res  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Stckingarea> list = (List<Stckingarea>)paraMap.get("content");
                log.info("将要写入的堆存区信息：{}",list);
                int resInt  =   stckingareaDao.insStckingarea(list);
                if(resInt>0){
                    res = true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   res;
                }else if(resInt==0) {
                    res = false;
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有成功插入记录";
                    result.resData  =   0;
                }else{
                    res =false;
                    result  =    new Result();
                    result.mes  =   "操作失败";
                    result.resData  =   -1;
                }
            }else{
                result  =    new Result();
                result.mes  =   "参数格式不合法，无法新增";
                result.resData  =   res;
            }
        }else{
            log.info("新增堆存区信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "新增堆存区信息不能使用空指针参数";
            result.resData  =   res;
        }
        return result;
    }

    /**
     * 删除堆存区信息
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result delStcking(Object parameters) throws Exception {
        Result result   =    null;
        boolean res  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Stckingarea> list = (List<Stckingarea>)paraMap.get("content");
                log.info("将要删除的堆存区信息：{}",list);
                int resInt  =   stckingareaDao.delStckingarea(list);
                if(resInt>0){
                    res = true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   res;
                }else if(resInt==0) {
                    res = false;
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有成功删除记录";
                    result.resData  =   0;
                }else{
                    res =false;
                    result  =    new Result();
                    result.mes  =   "操作失败";
                    result.resData  =   -1;
                }
            }else{
                result  =    new Result();
                result.mes  =   "参数格式不合法，无法新增";
                result.resData  =   res;
            }
        }else{
            log.info("删除堆存区信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "删除堆存区信息不能使用空指针参数";
            result.resData  =   res;
        }
        return result;
    }

    /**
     * 更新堆存区信息
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result updStcking(Object parameters) throws Exception {
        Result result   =    null;
        boolean resData  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  =   JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("filter") && null!=paraMap.get("replacement")){
                Stckingarea filter         =   JSON.parseObject(paraMap.get("filter").toString(),Stckingarea.class);
                Stckingarea replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Stckingarea.class);

                if(stckingareaDao.updStckingarea(filter,replacement)>0){
                    resData  =   true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   resData;
                }else{
                    log.info("更新堆存区信息操作失败!");
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有更新数据";
                    result.resData  =   resData;
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
                result  =    new Result();
                result.mes  =   "没有规范使用参数，缺少filter或者replacement";
                result.resData  =   resData;
            }
        }else{
            log.info("更新堆存区信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "更新堆存区信息不能使用空指针参数";
            result.resData  =   resData;
        }
        return result;
    }

    /**
     * 分页堆存区查询
     * @param parameters
     * @return
     */
    public PagetationRes queryStckingByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row         =   10;
            int page        =   1;
            String id           = null;
            String title        = null;
            String warehouse    = null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id    =   null!=filters.get("id")?filters.get("id").toString():null;
                title = null!=filters.get("title")?filters.get("title").toString():null;
                warehouse = null!=filters.get("warehouse")?filters.get("warehouse").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  stckingareaDao.countStckingarea(warehouse,id,title);
            if(result.total>0){
                result.rows =  stckingareaDao.queryStckingareaByPage(warehouse,id,title,row,page);
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

    /**
     * 新建货架信息
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result insShelves(Object parameters) throws Exception {
        Result result   =    null;
        boolean res  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Shelves> list = (List<Shelves>)paraMap.get("content");
                log.info("将要写入的货架信息：{}",list);
                int resInt  =   shelvesDao.insShelves(list);
                if(resInt>0){
                    res = true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   res;
                }else if(resInt==0) {
                    res = false;
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有成功插入记录";
                    result.resData  =   0;
                }else{
                    res =false;
                    result  =    new Result();
                    result.mes  =   "操作失败";
                    result.resData  =   -1;
                }
            }else{
                result  =    new Result();
                result.mes  =   "参数格式不合法，无法新增";
                result.resData  =   res;
            }
        }else{
            log.info("新增货架信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "新增货架信息不能使用空指针参数";
            result.resData  =   res;
        }
        return result;
    }

    /**
     * 删除货架信息
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result delShelves(Object parameters) throws Exception {
        Result result   =    null;
        boolean res  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Shelves> list = (List<Shelves>)paraMap.get("content");
                log.info("将要删除的货架信息：{}",list);
                int resInt  =   shelvesDao.delShelves(list);
                if(resInt>0){
                    res = true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   res;
                }else if(resInt==0) {
                    res = false;
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有成功删除记录";
                    result.resData  =   0;
                }else{
                    res =false;
                    result  =    new Result();
                    result.mes  =   "操作失败";
                    result.resData  =   -1;
                }
            }else{
                result  =    new Result();
                result.mes  =   "参数格式不合法，无法新增";
                result.resData  =   res;
            }
        }else{
            log.info("删除货架信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "删除货架信息不能使用空指针参数";
            result.resData  =   res;
        }
        return result;
    }

    /**
     * 更新货架信息
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result updShelves(Object parameters) throws Exception {
        Result result   =    null;
        boolean resData  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  =   JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("filter") && null!=paraMap.get("replacement")){
                Shelves filter         =   JSON.parseObject(paraMap.get("filter").toString(),Shelves.class);
                Shelves replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Shelves.class);

                if(shelvesDao.updShelves(filter,replacement)>0){
                    resData  =   true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   resData;
                }else{
                    log.info("更新货架信息操作失败!");
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有更新数据";
                    result.resData  =   resData;
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
                result  =    new Result();
                result.mes  =   "没有规范使用参数，缺少filter或者replacement";
                result.resData  =   resData;
            }
        }else{
            log.info("更新货架信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "更新货架信息不能使用空指针参数";
            result.resData  =   resData;
        }
        return result;
    }

    /**
     * 分页货架信息查询
     * @param parameters
     * @return
     */
    public PagetationRes queryShelvesByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row         =   10;
            int page        =   1;
            String id           = null;
            String title        = null;
            String warehouse    = null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id    =   null!=filters.get("id")?filters.get("id").toString():null;
                title = null!=filters.get("title")?filters.get("title").toString():null;
                warehouse = null!=filters.get("warehouse")?filters.get("warehouse").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  shelvesDao.countShelves(warehouse,id,title);
            if(result.total>0){
                result.rows =  shelvesDao.queryShelvesByPage(warehouse,id,title,row,page);
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

    /**
     * 新建库存记录
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result insIcd(Object parameters) throws Exception {
        Result result   =    null;
        boolean res  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Icd> list = (List<Icd>)paraMap.get("content");
                log.info("将要写入的库存信息：{}",list);
                int resInt  =   icdDao.insIcd(list);
                if(resInt>0){
                    res = true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   res;
                }else if(resInt==0) {
                    res = false;
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有成功插入记录";
                    result.resData  =   0;
                }else{
                    res =false;
                    result  =    new Result();
                    result.mes  =   "操作失败";
                    result.resData  =   -1;
                }
            }else{
                result  =    new Result();
                result.mes  =   "参数格式不合法，无法新增";
                result.resData  =   res;
            }
        }else{
            log.info("新增库存信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "新增库存信息不能使用空指针参数";
            result.resData  =   res;
        }
        return result;
    }

    /**
     * 删除库存记录
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result delIcd(Object parameters) throws Exception {
        Result result   =    null;
        boolean res  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                List<Icd> list = (List<Icd>)paraMap.get("content");
                log.info("将要删除的库存信息：{}",list);
                int resInt  =   icdDao.delIcd(list);
                if(resInt>0){
                    res = true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   res;
                }else if(resInt==0) {
                    res = false;
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有成功删除记录";
                    result.resData  =   0;
                }else{
                    res =false;
                    result  =    new Result();
                    result.mes  =   "操作失败";
                    result.resData  =   -1;
                }
            }else{
                result  =    new Result();
                result.mes  =   "参数格式不合法，无法新增";
                result.resData  =   res;
            }
        }else{
            log.info("删除库存信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "删除库存信息不能使用空指针参数";
            result.resData  =   res;
        }
        return result;
    }

    /**
     * 更新库存记录
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result updIcd(Object parameters) throws Exception {
        Result result   =    null;
        boolean resData  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  =   JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("filter") && null!=paraMap.get("replacement")){
                Icd filter         =   JSON.parseObject(paraMap.get("filter").toString(),Icd.class);
                Icd replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Icd.class);

                if(icdDao.updIcd(filter,replacement)>0){
                    resData  =   true;
                    result  =    new Result();
                    result.mes  =   "操作成功";
                    result.resData  =   resData;
                }else{
                    log.info("更新库存信息操作失败!");
                    result  =    new Result();
                    result.mes  =   "操作完成,但没有更新数据";
                    result.resData  =   resData;
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
                result  =    new Result();
                result.mes  =   "没有规范使用参数，缺少filter或者replacement";
                result.resData  =   resData;
            }
        }else{
            log.info("更新库存信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "更新库存信息不能使用空指针参数";
            result.resData  =   resData;
        }
        return result;
    }

    /**
     * 分页库存明细信息查询
     * @param parameters
     * @return
     */
    public PagetationRes queryIcdByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row         =   10;
            int page        =   1;
            String id           = null;
            String title        = null;
            String warehouse    = null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id    =   null!=filters.get("id")?filters.get("id").toString():null;
                title = null!=filters.get("title")?filters.get("title").toString():null;
                warehouse = null!=filters.get("warehouse")?filters.get("warehouse").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  icdDao.countIcd(warehouse,id,title);
            if(result.total>0){
                result.rows =  icdDao.queryIcdByPage(warehouse,id,title,row,page);
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

    /**
     * 汇总库存明细记录（日结）
     * @param parameters
     * @return
     * @throws Exception
     */
    public Result account(Object parameters) throws Exception {
        Result result   =    null;
        boolean res  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){

            }else{
                result  =    new Result();
                result.mes  =   "参数格式不合法，无法新增";
                result.resData  =   res;
            }
        }else{
            log.info("新增库存日结信息不能使用空指针参数");
            result  =    new Result();
            result.mes  =   "新增库存日结信息不能使用空指针参数";
            result.resData  =   res;
        }
        return result;
    }

    /**
     * 分页库存汇总信息查询
     * @param parameters
     * @return
     */
    public PagetationRes queryIcsByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row         =   10;
            int page        =   1;
            String id           = null;
            String title        = null;
            String warehouse    = null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id    =   null!=filters.get("id")?filters.get("id").toString():null;
                title = null!=filters.get("title")?filters.get("title").toString():null;
                warehouse = null!=filters.get("warehouse")?filters.get("warehouse").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  icsDao.countIcs(warehouse,id,title);
            if(result.total>0){
                result.rows =  icsDao.queryIcsByPage(warehouse,id,title,row,page);
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

}
