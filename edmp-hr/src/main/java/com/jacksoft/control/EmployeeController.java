package com.jacksoft.control;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 职员信息访问接口
 * @since 20240102
 */
@Slf4j
@RestController
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 查询职员信息
     * @return
     */
    @RequestMapping("/hr/queryemployee")
    public EdmpResponse queryEmployeeByPage(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                PagetationRes data = employeeService.queryEmployeeByPage(para.parameters);
                if(null!=data) {
                    response.mes    = "查询职员信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，职员信息查询失败");
                response.mes =  "查询职员信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询职员信息发生异常",e);
            response.mes =  "查询职员信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 新增职员信息
     * @return
     */
    @RequestMapping("/hr/addemployee")
    public EdmpResponse addEmployee(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(employeeService.insert(para.parameters)) {
                    response.mes = "新增职员信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增职员信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增职员信息");
                response.mes = "新增职员信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询职员信息发生异常",e);
            response.mes =  "查询职员信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除职员信息
     * @return
     */
    @RequestMapping("/hr/delemployee")
    public EdmpResponse delEmployee(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(employeeService.delete(para.parameters)) {
                    response.mes = "删除职员信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除职员信息操作成功";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除职员信息");
                response.mes = "删除职员信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除职员信息发生异常",e);
            response.mes =  "删除职员信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新职员信息
     * @return
     */
    @RequestMapping("/hr/updemployee")
    public EdmpResponse updEmployee(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(employeeService.update(para.parameters)) {
                    response.mes = "更新职员信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新职员信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新生产线信息");
                response.mes = "更新职员信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("更新职员信息发生异常",e);
            response.mes =  "更新职员信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

}
