package com.jacksoft.core;

import com.jacksoft.dao.MpcDao;
import com.jacksoft.dao.ProductDao;
import com.jacksoft.entity.Manufacture;
import com.jacksoft.entity.Product;
import com.jacksoft.entity.ds.ProductAccData;
import com.jacksoft.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 产品信息管理器
 * @since 2024/01/17
 */
@Slf4j
@Component
public class ProductManager {

    @Resource
    private MpcDao mpcDao;

    @Resource
    private ProductDao productDao;

    @Resource
    private TimeUtil    timeUtil;

    /**
     * 更新所有产品完成信息
     * @return
     * @throws Exception
     */
    public boolean updFinish() throws Exception{
        return updFinish(null,null);
    }

    /**
     * 更新指定产品批次的产品完成信息，由于使用同步锁这里有可能会比较容易形成堵塞，产生内存涉露
     * @param product
     * @param batch
     * @return
     * @throws Exception
     */
    public synchronized boolean updFinish(String product,String batch){
        boolean result = false;
        if(null!=product){
            if(null!=batch){
                try {
                    result = toAccFinish(product,batch);
                } catch (Exception e) {
                    log.error("计算产品完成量时出现异常,任务未完成",e);
                }
            }else{
                log.info("没有指定批次,将会把产品:{}的所有未完成批次的产量数据都更新",product);
                List<Product> products = productDao.quyProductOnly(product,null,"1",null);
                if(null!=products && !products.isEmpty()){
                    result = true;
                    for(Product row : products){
                        try {
                            toAccFinish(row.id,row.batch);
                        } catch (Exception e) {
                            log.error("统计"+row.id+"-"+row.batch+"的产量数据时发生异常",e);
                            result = false;
                            break;
                        }
                    }
                }else{
                    log.info("没有需要统计产量的产品");
                }
            }
        }else{
            log.info("没有指定产品、批次,将会把所有产品的所有未完成的批次的产量数据都更新");
            List<Product> products = productDao.quyProductOnly(null,null,"1",null);
            if(null!=products && !products.isEmpty()){
                result = true;
                for(Product row : products){
                    try {
                        toAccFinish(row.id,row.batch);
                    } catch (Exception e) {
                        log.error("统计"+row.id+"-"+row.batch+"的产量数据时发生异常",e);
                        result = false;
                        break;
                    }
                }
            }else{
                log.info("没有需要统计产量的产品");
            }
        }
        return result;
    }

    private boolean toAccFinish(String product,String batch) throws Exception{
        boolean result  =   false;
        String curDate = timeUtil.curDateStr("YYYYMMDD");
        ProductAccData todateAccData = mpcDao.quyProdAcc(null,null,product,curDate,batch);
        ProductAccData productAccData = mpcDao.quyProdAcc(null,null,product,null,batch);
        if(null!=productAccData){
            Product filter = new Product();
            filter.id = product;
            filter.batch = batch;
            Product replacement = new Product();
            replacement.finish  = productAccData.outputTotalToday;
            /**
             * 没有找出原因为什么在finish和actuality对比的话时候会出现对平的情况
             */
            //if(replacement.finish==filter.actuality) replacement.state = "4";
            if(productDao.updProduct(filter,replacement)==1){
                result  =   true;
            }else{
                log.error("更新产品:{}-{}的总完成量时出现异常",product,batch);
                throw new Exception();
            }
        }
        if(null!=todateAccData){
            Product filter = new Product();
            filter.id = product;
            filter.batch = batch;
            Product replacement = new Product();
            if(0!=todateAccData.planTotalToday){
                String rate = Math.round(((double)todateAccData.outputTotalToday/(double)todateAccData.planTotalToday)*100.0*100.0)/100.0 + "";
                log.info("产品:{}-{}当天完成率:{}",product,batch,rate);
                replacement.today   =   rate;
                if(productDao.updProduct(filter,replacement)==1){
                    result  =   true;
                }else{
                    log.error("更新产品:{}-{}的当天计划完成率时出现异常",product,batch);
                    throw new Exception();
                }
            }else{
                log.info("产品:{}-{}当天没有生产计划",product,batch);
            }
        }
        return result;
    }
}
