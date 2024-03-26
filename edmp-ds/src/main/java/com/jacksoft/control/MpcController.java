package com.jacksoft.control;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.service.MpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 生产计划与控制接口
 * @since 20240102
 */
@Slf4j
@RestController
public class MpcController {

    @Resource
    private MpcService mpcService;

    /**
     * 查询生产计划信息
     * @return
     */
    @RequestMapping("/ds/queryplans")
    public EdmpResponse queryPlans(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                PagetationRes data = mpcService.queryMpcByPage(para.parameters);
                if(null!=data) {
                    response.mes = "查询生产计划信息操作成功";
                    response.state = "1";
                    response.res = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，生产计划信息查询失败");
                response.mes =  "查询生产计划信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产计划信息操作发生异常",e);
            response.mes =  "查询生产计划信息操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 新增生产计划信息
     * @return
     */
    @RequestMapping("/ds/addmpc")
    public EdmpResponse addMpc(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(mpcService.insert(para.parameters)) {
                    response.mes = "新增生产计划信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增生产计划信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增生产线信息");
                response.mes = "新增生产计划信息操作失败";
                response.state = "-1";
            }
        }
        catch(DuplicateKeyException e){
            log.error("新增生产计划信息发生异常",e);
            response.mes =  "新增的生产计划时段已经有生产安排";
            response.state = "0";
        }
        catch (Exception e) {
            log.error("新增生产计划信息发生异常",e);
            response.mes =  "新增生产计划信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除生产计划信息
     * @return
     */
    @RequestMapping("/ds/delmpc")
    public EdmpResponse delMpc(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(mpcService.delete(para.parameters)) {
                    response.mes = "删除生产计划信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除生产计划信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除生产计划信息");
                response.mes = "删除生产计划信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除生产计划信息发生异常",e);
            response.mes =  "删除生产计划信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新生产计划信息
     * @return
     */
    @RequestMapping("/ds/updmpc")
    public EdmpResponse updMpc(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(mpcService.update(para.parameters)) {
                    response.mes = "更新生产计划信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新生产计划信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新生产计划信息");
                response.mes = "更新生产计划信息操作失败";
                response.state = "-1";
            }
        }
        catch(DuplicateKeyException e){
            log.error("修改生产计划信息发生异常",e);
            response.mes =  "修改的生产计划时段已经有生产安排";
            response.state = "0";
        } catch (Exception e) {
            log.error("更新生产计划信息发生异常",e);
            response.mes =  "更新生产计划信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询指定的生产线的当日生产计划信息
     * @return
     */
    @RequestMapping("/ds/quycapabyline")
    public EdmpResponse quyCapaByLine(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                Map<String, Object> data = mpcService.quyCapaByLine(para.parameters);
                if(null!=data) {
                    response.mes = "查询生产计划信息操作成功";
                    response.state = "1";
                    response.res = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，生产计划信息查询失败");
                response.mes =  "查询生产计划信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产计划信息操作发生异常",e);
            response.mes =  "查询生产计划信息操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询指定的生产线的当月产量计划信息
     * @return
     */
    @RequestMapping("/ds/quyproduction")
    public EdmpResponse quyProduction(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            //log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                Map<String, Object> data = mpcService.quyProduction(para.parameters);
                if(null!=data) {
                    response.mes = "查询产量信息操作成功";
                    response.state = "1";
                    response.res = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，产量信息查询失败");
                response.mes =  "查询产量信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询产量信息操作发生异常",e);
            response.mes =  "查询产量信息操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询指定的生产线的当月产量计划信息
     * @return
     */
    @RequestMapping("/ds/quyallproduction")
    public EdmpResponse quyAllProduction(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                Map<String,Object> data = mpcService.quyAllProduction(para.parameters);
                if(null!=data) {
                    response.mes = "查询产量信息操作成功";
                    response.state = "1";
                    response.res = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，产量信息查询失败");
                response.mes =  "查询产量信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询产量信息操作发生异常",e);
            response.mes =  "查询产量信息操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询所有生产线的当月产量信息
     * @return
     */
    @RequestMapping("/ds/quycapaofdate")
    public EdmpResponse quyCapaOfDate(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                Map<String,Object> data = mpcService.quyCapaOfDate(para.parameters);
                if(null!=data) {
                    response.mes = "查询产量信息操作成功";
                    response.state = "1";
                    response.res = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，产量信息查询失败");
                response.mes =  "查询产量信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询产量信息操作发生异常",e);
            response.mes =  "查询产量信息操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 一键编制生产计划
     * @return
     */
    @RequestMapping("/ds/autoeditplan")
    public EdmpResponse autoEditPlan(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(mpcService.autoEditPlan(para.parameters)) {
                    response.mes = "自动编制生产计划操作成功";
                    response.state = "1";
                }else{
                    response.mes = "自动编制生产计划操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法自动编制生产计划操作");
                response.mes = "自动编制生产计划操作失败";
                response.state = "-1";
            }
        }
        catch(DuplicateKeyException e){
            log.error("自动编制生产计划发生异常",e);
            response.mes =  "新增的生产计划时段已经有生产安排";
            response.state = "0";
        }
        catch (Exception e) {
            log.error("自动编制生产计划操作发生异常",e);
            response.mes =  "自动编制生产计划操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    @RequestMapping("/ds/getallplan")
    public EdmpResponse getAlldayPlan(@RequestBody EdmpRequertPara para) {
        EdmpResponse response = new EdmpResponse();
        log.info("接收到参数：{}",para);
        try {
            if(null!=para && null!=para.parameters) {
                List<Map> data = mpcService.getAlldayPlan(para.parameters);
                if(null!=data) {
                    response.mes = "查询全天生产计划操作成功";
                    response.res = data;
                    response.state = "1";
                }else{
                    response.mes = "查询全天生产计划操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，查询全天生产计划操作失败");
                response.mes = "缺少参数无法查询全天生产计划";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询全天生产计划操作发生异常",e);
            response.mes =  "查询全天生产计划操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 导出生产计划表
     * @return
     */
    @RequestMapping("/ds/expplan")
    public EdmpResponse expPlan() {
        EdmpResponse response = new EdmpResponse();
        return response;
    }

    /**
     * 导出生产日报表
     * @return
     */
    @RequestMapping("/ds/expdaily")
    public EdmpResponse expDaily() {
        EdmpResponse response = new EdmpResponse();
        return response;
    }
}
