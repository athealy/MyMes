package com.jacksoft.control;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.service.QualityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试管理接口
 */
@Slf4j
@RestController
public class QualityController {

    @Resource
    private QualityService qualityService;

    /**
     * 查询测试信息(分页)
     * @return
     */
    @RequestMapping("/ds/queryquality")
    public EdmpResponse queryQuality(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                PagetationRes data = qualityService.queryQualityByPage(para.parameters);
                if(null!=data){
                    response.mes    =   "查询成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，测试信息查询失败");
                response.mes =  "查询测试信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询测试信息发生异常",e);
            response.mes =  "查询测试信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 新增测试信息
     * @return
     */
    @RequestMapping("/ds/addquality")
    public EdmpResponse addQuality(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(qualityService.insert(para.parameters)) {
                    response.mes = "新增测试信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增测试信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增测试信息");
                response.mes = "新增测试信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("新增测试信息发生异常",e);
            response.mes =  "新增测试信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除测试信息
     * @return
     */
    @RequestMapping("/ds/delquality")
    public EdmpResponse delQuality(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(qualityService.delete(para.parameters)) {
                    response.mes = "删除测试信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除测试信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除测试信息");
                response.mes = "删除测试信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除测试信息发生异常",e);
            response.mes =  "删除测试信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新测试信息
     * @return
     */
    @RequestMapping("/ds/updquality")
    public EdmpResponse updQuality(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(qualityService.update(para.parameters)) {
                    response.mes = "更新测试信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新测试信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新测试信息");
                response.mes = "更新测试信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("更新测试信息发生异常",e);
            response.mes =  "更新测试信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 导出测试清单
     */
    @RequestMapping("/ds/expqualreport")
    public void expQualityReport(){

    }


}
