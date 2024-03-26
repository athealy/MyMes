package com.jacksoft.control;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.Result;
import com.jacksoft.service.RepairService;
import com.jacksoft.service.WmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 库存管理接口
 */
@Slf4j
@RestController
public class WmsController {

    @Resource
    private WmsService wmsService;

    /**
     * 产成品手工入库操作
     * @return
     */
    @RequestMapping("/ds/fginbound")
    public EdmpResponse fgInbound(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                Result data = wmsService.fgInbound(para.parameters);
                if(null!=data.resData){
                    response.mes    =   "产成品手工入库操作成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有必要条件，手工入库操作失败");
                response.mes =  "手工入库操作发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("手工入库操作发生异常",e);
            response.mes =  "手工入库操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 产成品出库
     */
    @RequestMapping("/ds/fgoutbound")
    public EdmpResponse fgOutbound(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                Result data = wmsService.fgOutbound(para.parameters);
                if(null!=data.resData){
                    response.mes    =   "半成品手工入库操作成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有必要条件，半成品手工入库操作失败");
                response.mes =  "半成品手工入库操作发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("半成品手工入库操作发生异常",e);
            response.mes =  "半成品手工入库操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 半成品入库暂存操作
     * @return
     */
    @RequestMapping("/ds/sginbound")
    public EdmpResponse sgInbound(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                Result data = wmsService.sgInbound(para.parameters);
                if(null!=data.resData){
                    response.mes    =   "半成品手工入库操作成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有必要条件，半成品手工入库操作失败");
                response.mes =  "半成品手工入库操作发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("半成品手工入库操作发生异常",e);
            response.mes =  "半成品手工入库操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

}
