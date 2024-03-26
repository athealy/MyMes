package com.jacksoft.core.runnable;

import com.jacksoft.core.ProductManager;
import com.jacksoft.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.List;

@Slf4j
public class ProductRunnable implements Runnable{

    private ApplicationContext applicationContext;
    private List<Product> products;

    public ProductRunnable(ApplicationContext applicationContext, List<Product> products){
        this.applicationContext = applicationContext;
        this.products           = products;
    }

    @Override
    public void run() {
        if(null!=applicationContext && null!=products && !products.isEmpty()) {
            ProductManager productManager = applicationContext.getBean(ProductManager.class);
            for(Product product : products){
                productManager.updFinish(product.id,product.batch);
            }
        }
    }
}
