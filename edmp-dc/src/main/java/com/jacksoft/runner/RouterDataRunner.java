package com.jacksoft.runner;

import com.jacksoft.data.RouterInfoConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

@Configuration
@Slf4j
@Order(1)
public class RouterDataRunner implements ApplicationRunner {

    @Resource
    private RouterInfoConfig routerInfoConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("现在开始加载路由数据");
        if(routerInfoConfig.setup()){
            log.info("路由数据加载完成!");
        }else{
            log.info("路由数据加载失败!");
        }
    }
}
