package com.jacksoft.runner;

import com.jacksoft.core.line.LineStatEngine;
import com.jacksoft.core.product.ProdStatEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

@Configuration
@Slf4j
@Order(2)
public class ProductRunner implements ApplicationRunner {

    @Resource
    private ProdStatEngine prodStatEngine;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始启动订单分析引擎...");
        prodStatEngine.start();
    }
}
