package com.jacksoft.control;

import com.jacksoft.entity.*;
import com.jacksoft.service.DepartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门信息访问接口
 * @since 20240102
 */
@Slf4j
@RestController
public class DepartController {

    @Resource
    private DepartService departService;

    /**
     * 查询生产线信息
     * @return
     */
    @RequestMapping("/ds/querydepart")
    public EdmpResponse queryApl(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                PagetationRes data = departService.queryDepartByPage(para.parameters);
                if(null!=data){
                    response.mes    =   "查询成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，部门信息查询失败");
                response.mes =  "查询部门信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询部门信息发生异常",e);
            response.mes =  "查询部门信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 新增部门信息
     * @return
     */
    @RequestMapping("/ds/adddepart")
    public EdmpResponse addDepart(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(departService.insert(para.parameters)) {
                    response.mes = "新增部门信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增部门信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增部门信息");
                response.mes = "新增部门信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("新增部门信息发生异常",e);
            response.mes =  "新增部门信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除部门信息
     * @return
     */
    @RequestMapping("/ds/deldepart")
    public EdmpResponse delDepart(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(departService.delete(para.parameters)) {
                    response.mes = "删除部门信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除部门信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除部门信息");
                response.mes = "删除部门信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除部门信息发生异常",e);
            response.mes =  "删除部门信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新部门信息
     * @return
     */
    @RequestMapping("/ds/upddepart")
    public EdmpResponse updDepart(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(departService.update(para.parameters)) {
                    response.mes = "更新部门信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新部门信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新部门信息");
                response.mes = "更新部门信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("更新部门信息发生异常",e);
            response.mes =  "更新部门信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 取不重复的部门信息
     * @return
     */
    @RequestMapping("/ds/quydepartonly")
    public EdmpResponse quyDepartOnly(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                List<Department> data = departService.quyDepartOnly(para.parameters);
                if(null!=data) {
                    response.mes    = "查询部门信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询部门信息操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询生产线信息");
                response.mes        = "查询部门信息操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询部门信息发生异常",e);
            response.mes =  "查询部门信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

}
