package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.dao.MpcDao;
import com.jacksoft.dao.ProductDao;
import com.jacksoft.entity.Manufacture;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.Product;
import com.jacksoft.entity.ds.ProductAccData;
import com.jacksoft.util.StringUtil;
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
public class ProductService {

    @Resource
    private ProductDao productDao;

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
    public PagetationRes queryProductByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String id   =   null;
            String department  = null;
            String state =  null;
            String batch =  null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                state = null!=filters.get("state")?filters.get("state").toString():null;
                batch = null!=filters.get("batch")?filters.get("batch").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  productDao.countProduct(id,department,state,batch);
            if(result.total>0){
                result.rows =  productDao.queryProductByPage(id,department,state,batch,row,page);
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
                List<Product> list = (List<Product>)paraMap.get("content");
                log.info("将要写入的产品信息：{}",list);
                int resInt  =   productDao.insProduct(list);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增产品信息不能使用空指针参数");
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
                Product filter =   JSON.parseObject(paraMap.get("filter").toString(),Product.class);
                Product replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Product.class);

                if(productDao.updProduct(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新产品信息操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新产品信息不能使用空指针参数");
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
                if(productDao.delProduct((List<Product>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除产品信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除产品信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 查询所有不重复定义的产品
     * @param parameters
     * @return
     */
    public List<Product> quyProductOnly(Object parameters) throws Exception{
        List<Product> result       =   null;
        Map<String,Object> filter   =   null;
        String id           = null;
        String department   = null;
        String state        = null;
        String batch        = null;
        if(null!=parameters){
            filter = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                state = null!=filters.get("state")?filters.get("state").toString():null;
                batch = null!=filters.get("batch")?filters.get("batch").toString():null;
            }
        }
        result  =  productDao.quyProductOnly(id,department,state,batch);
        return result;
    }

    /**
     * 查询指定的产线当前在生产的产品的统计信息
     * @param parameters
     * @return
     */
    public ProductAccData quyProdAcc(Object parameters) throws Exception{
        ProductAccData result   =   null;
        Map<String,Object> filter   =   null;
        String line           = null;
        String department   = null;
        String mfg          = null;
        String productId    = null;
        String classes      = null;
        String batch        = null;
        if(null!=parameters){
            filter = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                line  =   null!=filters.get("line")?filters.get("line").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                mfg = null!=filters.get("mfg")?filters.get("mfg").toString():null;
                classes = null!=filters.get("classes")?filters.get("classes").toString():null;
            }
        }
        //String curDate       =  timeUtil.curDateStr("YYYYMMDD");
        String curDate       =  mfg;
        String strTimezone   =  timezoneUtil.getTimezoneOfDay(department,null)+"";
        Manufacture mpc = mpcDao.quyMakingProduct(line,curDate,classes,strTimezone);
        if(null!=mpc) {
            if(!"-".equals(mpc.getProduct())) {
                String[] prodArray  = mpc.getProduct().split(",");
                String[] batchArray = mpc.getBatch().split(",");
                if (null!=prodArray && prodArray.length>0 && prodArray.length==batchArray.length) {
                    result  = new ProductAccData();
                    for(int i=0;i<prodArray.length;i++) {
                        productId = prodArray[i];
                        batch = batchArray[i];
                        List<Product> products = productDao.quyProductOnly(productId, department, null, mpc.getBatch());
                        if (null != products && !products.isEmpty()) {
                            ProductAccData tmpData = mpcDao.quyProdAcc(line, department, productId, mfg, batch);
                            if (null != tmpData) {
                                //result.id = products.get(0).id;
                                //result.department = products.get(0).department;
                                result.actuality += products.get(0).actuality;
                                //result.batch = products.get(0).batch;
                                //result.capacity = products.get(0).capacity;
                                result.finish += products.get(0).finish;
                                result.order += products.get(0).order;
                                //result.state = products.get(0).state;
                                result.today += products.get(0).today;
                                result.outputTotalToday += tmpData.outputTotalToday;
                                result.planTotalToday += tmpData.planTotalToday;
                            } else {
                                log.info("产品:{}没有对应的生产计划信息，请检查参数{}", productId, parameters);
                            }
                        } else {
                            log.info("查询的产品信息不存在，请检查参数:{}-{}-{}", productId, department, mpc.getBatch());
                        }
                    }
                } else {
                    log.info("产品标识为空无法查询");
                }
            }else{
                log.info("生产线正在转线，没有产品信息");
            }
        }else{
            log.info("没有定义指定生产线的生产计划，生产线:{},日期:{},执行时段:{},班制:{}",line,curDate,strTimezone,classes);
        }
        return result;
    }

    /**
     * 查询指定的产线当前在生产的产品的统计信息(多行)
     * @param parameters
     * @return
     */
    public List<ProductAccData> quyProdAccMult(Object parameters) throws Exception{
        List<ProductAccData> result   =   null;
        Map<String,Object> filter   =   null;
        String line           = null;
        String department   = null;
        String mfg          = null;
        String productId    = null;
        String classes      = null;
        String batch        = null;
        if(null!=parameters){
            filter = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                line  =   null!=filters.get("line")?filters.get("line").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                mfg = null!=filters.get("mfg")?filters.get("mfg").toString():null;
                classes = null!=filters.get("classes")?filters.get("classes").toString():null;
            }
        }
        //String curDate       =  timeUtil.curDateStr("YYYYMMDD");
        String curDate       =  mfg;
        String strTimezone   =  timezoneUtil.getTimezoneOfDay(department,null)+"";
        //Manufacture mpc = mpcDao.quyMakingProduct(line,curDate,classes,strTimezone);
        Manufacture mpc = mpcDao.quyMakingProduct(line,curDate,classes,null);
        if(null!=mpc) {
            if(!"-".equals(mpc.getProduct())) {
                String[] prodArray  = mpc.getProduct().split(",");
                String[] batchArray = mpc.getBatch().split(",");
                if (null!=prodArray && prodArray.length>0 && prodArray.length==batchArray.length) {
                    result  =   new ArrayList<>();
                    for(int i=0;i<prodArray.length;i++) {
                        productId = prodArray[i];
                        batch = batchArray[i];
                        if(!"-".equals(productId)) {
                            List<Product> products = productDao.quyProductOnly(productId, department, null, batch);
                            if (null != products && !products.isEmpty()) {
                                ProductAccData accData = mpcDao.quyProdAcc(line, department, productId, mfg, batch);
                                if (null != accData) {
                                    accData.id = products.get(0).id;
                                    accData.department = products.get(0).department;
                                    accData.actuality = products.get(0).actuality;
                                    accData.batch = products.get(0).batch;
                                    accData.capacity = products.get(0).capacity;
                                    accData.finish = products.get(0).finish;
                                    accData.order = products.get(0).order;
                                    accData.state = products.get(0).state;
                                    accData.today = products.get(0).today;
                                    result.add(accData);
                                } else {
                                    log.info("产品:{}没有对应的生产计划信息，请检查参数{}", productId, parameters);
                                }
                            } else {
                                log.info("查询的产品信息不存在，请检查参数:{}-{}-{}", productId, department, batch);
                            }
                        }
                    }
                } else {
                    log.info("产品标识为空无法查询");
                }
            }else{
                log.info("生产线正在转线，没有产品信息");
            }
        }else{
            log.info("没有定义指定生产线的生产计划，生产线:{},日期:{},执行时段:{},班制:{}",line,curDate,strTimezone,classes);
        }
        return result;
    }

    /**
     * 查询所有在产品的生产情况
     * @return
     * @throws Exception
     */
    public Map<String,Object> quyProdFinish(Object parameters) throws Exception{
        Map<String,Object> result = null;
        List<Product> data = productDao.quyProductOnly(null,null,null,null);
        if(null!=data && !data.isEmpty()){

            result = new HashMap<>();
            List<String>    xAxis  = new ArrayList<>();
            List<Integer>   finish = new ArrayList<>();
            List<Integer>   incomplete = new ArrayList<>();
            for(Product product : data){
                if(product.actuality!=product.finish){
                    xAxis.add(product.id+"-"+product.batch);
                    finish.add(product.finish);
                    incomplete.add(product.actuality - product.finish);
                }
            }
            result.put("xAxis",xAxis);
            result.put("finish",finish);
            result.put("incomplete",incomplete);

        }else{
            log.info("没有定义产品信息,无法查询");
        }
        return result;
    }

    /**
     * 查询所有未完成产品情况
     * @return
     * @throws Exception
     */
    public List<Product> quyProdInconplete(Object parameters) throws Exception{
        List<Product> result = null;
        List<Product> data = productDao.quyProductOnly(null,null,null,null);
        if(null!=data && !data.isEmpty()){
            result = new ArrayList<>();
            for(Product product : data){
                if(product.actuality!=product.finish){
                    result.add(product);
                }
            }
        }else{
            log.info("没有定义产品信息,无法查询");
        }
        return result;
    }

    /**
     * 查询历史产品标识
     * @param parameters
     * @return
     */
    public List<Product> quyProdSimple(Object parameters) throws Exception{
        List<Product> result       =   null;
        /*Map<String,Object> filter   =   null;
        String id           = null;
        String department   = null;
        String state        = null;
        String batch        = null;
        if(null!=parameters){
            filter = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                state = null!=filters.get("state")?filters.get("state").toString():null;
                batch = null!=filters.get("batch")?filters.get("batch").toString():null;
            }
        }*/
        result  =  productDao.quyProdSimple();
        return result;
    }

}
