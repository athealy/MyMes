package com.jacksoft.control;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.service.RepairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 维修管理接口
 */
@Slf4j
@RestController
public class RepairController {

    @Resource
    private RepairService repairService;

    /**
     * 查询维修情况信息(分页)
     * @return
     */
    @RequestMapping("/ds/queryrepair")
    public EdmpResponse queryRepair(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                PagetationRes data = repairService.queryRepairByPage(para.parameters);
                if(null!=data){
                    response.mes    =   "查询成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，查询失败");
                response.mes =  "查询信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询信息发生异常",e);
            response.mes =  "查询发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 新增维修信息
     * @return
     */
    @RequestMapping("/ds/addrepair")
    public EdmpResponse addRepair(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(repairService.insert(para.parameters)) {
                    response.mes = "新增操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增测试信息");
                response.mes = "新增操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("新增发生异常",e);
            response.mes =  "新增发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除测试信息
     * @return
     */
    @RequestMapping("/ds/delrepair")
    public EdmpResponse delRepair(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(repairService.delete(para.parameters)) {
                    response.mes = "删除操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除信息");
                response.mes = "删除操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除发生异常",e);
            response.mes =  "删除发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新测试信息
     * @return
     */
    @RequestMapping("/ds/updrepair")
    public EdmpResponse updRepair(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(repairService.update(para.parameters)) {
                    response.mes = "更新操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新");
                response.mes = "更新操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("更新发生异常",e);
            response.mes =  "更新操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 导出维修报告
     */
    @RequestMapping("/ds/expreparireport")
    public void expRepairReport(){

    }

    /**
     * 维修件转测试
     */
    @RequestMapping("/ds/recheck")
    public void reCheck(){

    }

}
