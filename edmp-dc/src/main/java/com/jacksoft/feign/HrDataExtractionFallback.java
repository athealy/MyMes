package com.jacksoft.feign;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * HrDataExtraction熔断类
 */
@Slf4j
@Component
public class HrDataExtractionFallback implements HrDataExtraction {

    private String TIMEOUT  =   "3000";             //重试间隔步长

    @Override
    public EdmpResponse consult(EdmpRequertPara analypara, String path) {
        log.error("调用远程数据服务出现熔断");
        EdmpResponse response   =   new EdmpResponse();
        response.state   =   "-1";
        response.mes     =   "远程数据服务请求出现熔断,稍后再试！";
        response.res     =   TIMEOUT;
        return response;
    }
}
