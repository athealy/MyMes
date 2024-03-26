package com.jacksoft.destroy;

import com.jacksoft.core.line.LineStatEngine;
import com.jacksoft.core.product.ProdStatEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * 优雅停止
 * @since 2024/0125
 */
@Configuration
@Slf4j
public class DestroyHandler {

    @Resource
    private LineStatEngine lineStatEngine;

    @Resource
    private ProdStatEngine prodStatEngine;

    @PreDestroy
    public void destroy(){
        log.info("停止引擎");
        try {
            lineStatEngine.stop();
            prodStatEngine.stop();
        } catch (Exception e) {
            log.error("停止引擎操作发生异常",e);
        }
    }
}
