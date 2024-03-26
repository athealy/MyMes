package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.data.RouterInfoConfig;
import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import com.jacksoft.feign.DataExtraction;
import com.jacksoft.feign.HrDataExtraction;
import com.jacksoft.pojo.Router;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Slf4j
@Service
public class Transponder {

    @Resource
    private RouterInfoConfig routerInfoConfig;

    @Resource
    private DataExtraction dataExtraction;

    @Resource
    private HrDataExtraction hrDataExtraction;

    /**
     * 转发
     * @param para
     * @return
     * @throws Exception
     */
    public EdmpResponse toTrans(EdmpRequertPara para) throws Exception{
        EdmpResponse response  = null;
        String mapper          = routerInfoConfig.readMapper(para.serial);
        Router router          = JSON.parseObject(mapper,Router.class);
        switch(router.register){
            case "EDMP-DS":
                log.info("执行生产数据请求，使用参数:{}",router);
                response  = dataExtraction.consult(para,router.url);
                break;
            case "EDMP-HR":
                log.info("执行人事数据请求，使用参数:{}",router);
                response  = hrDataExtraction.consult(para,router.url);
                break;
        }
        return response;
    }
}
