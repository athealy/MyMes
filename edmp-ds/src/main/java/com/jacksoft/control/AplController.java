package com.jacksoft.control;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import com.jacksoft.entity.Lineinfo;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.service.AplService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生产线信息访问接口
 * @since 20240102
 */
@Slf4j
@RestController
public class AplController {

    @Resource
    private AplService aplService;

    /**
     * 查询生产线信息
     * @return
     */
    @RequestMapping("/ds/queryapl")
    public EdmpResponse queryApl(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                PagetationRes data = aplService.queryAplByPage(para.parameters);
                if(null!=data){
                    response.mes    =   "查询成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，生产线信息查询失败");
                response.mes =  "查询生产线信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产线信息发生异常",e);
            response.mes =  "查询生产线信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 新增生产线信息
     * @return
     */
    @RequestMapping("/ds/addapl")
    public EdmpResponse addApl(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(aplService.insert(para.parameters)) {
                    response.mes = "新增生产线信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增生产线信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增生产线信息");
                response.mes = "新增生产线信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("新增自动化生产线信息发生异常",e);
            response.mes =  "新增生产线信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除生产线信息
     * @return
     */
    @RequestMapping("/ds/delapl")
    public EdmpResponse delApl(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(aplService.delete(para.parameters)) {
                    response.mes = "删除生产线信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除生产线信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除生产线信息");
                response.mes = "删除生产线信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除生产线信息发生异常",e);
            response.mes =  "删除生产线信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新生产线信息
     * @return
     */
    @RequestMapping("/ds/updapl")
    public EdmpResponse updApl(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(aplService.update(para.parameters)) {
                    response.mes = "更新生产线信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新生产线信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新生产线信息");
                response.mes = "更新生产线信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("更新生产线信息发生异常",e);
            response.mes =  "更新生产线信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 取不重复的生产线信息
     * @return
     */
    @RequestMapping("/ds/quyaplonly")
    public EdmpResponse quyAplOnly(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                List<Lineinfo> data = aplService.quyAplOnly(para.parameters);
                if(null!=data) {
                    response.mes    = "查询生产线信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询生产线信息操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询生产线信息");
                response.mes        = "查询生产线信息操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产线信息发生异常",e);
            response.mes            = "查询生产线信息发生异常,请与管理员联系查看日志";
            response.state          = "-1";
        }
        return response;
    }

    /**
     * 查询指定生产车间生产线信息
     * @return
     */
    @RequestMapping("/ds/quydepartapl")
    public EdmpResponse quyDepartAplByPage(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                PagetationRes data = aplService.quyDepartAplByPage(para.parameters);
                if(null!=data){
                    response.mes    =   "查询成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，生产线信息查询失败");
                response.mes =  "查询生产线信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产线信息发生异常",e);
            response.mes =  "查询生产线信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 取生产线的生产线拓扑
     * @return
     */
    @RequestMapping("/ds/quyapltopo")
    public EdmpResponse quyAplTopo(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                List<Map> data = aplService.quyAplTopo(para.parameters);
                if(null!=data) {
                    response.mes    = "查询生产线信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询生产线信息操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询生产线信息");
                response.mes        = "查询生产线信息操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产线信息发生异常",e);
            response.mes            = "查询生产线信息发生异常,请与管理员联系查看日志";
            response.state          = "-1";
        }
        return response;
    }

    /**
     * 取生产线产品关系拓扑
     * @return
     */
    @RequestMapping("/ds/quyrelationtopo")
    public EdmpResponse quyRelationTopo(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                List<Map> data = aplService.quyRelationTopo(para.parameters);
                if(null!=data) {
                    response.mes    = "查询产线产品关系信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询产线产品关系信息操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询产线产品关系信息");
                response.mes        = "查询产线产品关系信息操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产线产品关系发生异常",e);
            response.mes            = "查询产线产品关系发生异常,请与管理员联系查看日志";
            response.state          = "-1";
        }
        return response;
    }

}
