package com.jacksoft.runner;

import com.jacksoft.core.line.LineStatEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

@Configuration
@Slf4j
@Order(1)
public class LineRunner implements ApplicationRunner {

    @Resource
    private LineStatEngine lineStatEngine;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始启动生产线分析引擎...");
        lineStatEngine.start();
    }
}
