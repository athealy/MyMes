package com.jacksoft.core.product;

import com.jacksoft.dao.LineinfoDao;
import com.jacksoft.dao.MpcDao;
import com.jacksoft.dao.ProductDao;
import com.jacksoft.entity.Lineinfo;
import com.jacksoft.entity.Manufacture;
import com.jacksoft.entity.Product;
import com.jacksoft.util.TimeUtil;
import com.jacksoft.util.TimezoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 分析所有产线当天是否有安排生产计划
 */
@Slf4j
@DisallowConcurrentExecution
public class ProductStatJob implements Job {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if(null!=applicationContext){
            ProductDao  productDao  =  applicationContext.getBean(ProductDao.class);
            TimeUtil    timeUtil    =  applicationContext.getBean(TimeUtil.class);
            int page = 1;
            List<Product> products  =  null;
            log.info("分析订单状态执行时间:{}",timeUtil.curTimeStamp());
            while((products=productDao.queryProductByPage(null,null,"1",null,20,page))!=null
                    && !products.isEmpty()){
                page++;
                for(Product product : products){
                    if(!product.state.equals("2") && !product.state.equals("3")) { //不调整2打样和3待产的订单状态
                        if (product.finish >= product.actuality) {
                            Product replacement = new Product();
                            replacement.id = product.id;
                            replacement.batch = product.batch;
                            replacement.today = product.today;
                            replacement.finish = product.finish;
                            replacement.actuality = product.actuality;
                            replacement.capacity = product.capacity;
                            replacement.order = product.order;
                            replacement.state = "4";
                            productDao.updProduct(product, replacement);
                            continue;
                        }
                        if (product.finish < product.actuality && product.state.equals("4")) {
                            Product replacement = new Product();
                            replacement.id = product.id;
                            replacement.batch = product.batch;
                            replacement.today = product.today;
                            replacement.finish = product.finish;
                            replacement.actuality = product.actuality;
                            replacement.capacity = product.capacity;
                            replacement.order = product.order;
                            replacement.state = "1";
                            productDao.updProduct(product, replacement);
                        }
                    }
                }
            }

        }
    }
}
