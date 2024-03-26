package com.jacksoft.control;

import com.jacksoft.entity.*;
import com.jacksoft.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 生产时段访问接口
 * @since 20240111
 */
@Slf4j
@RestController
public class TaskController {

    @Resource
    private TaskService taskService;

    /**
     * 查询生产线信息
     * @return
     */
    @RequestMapping("/ds/querytasks")
    public EdmpResponse queryTasks(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                PagetationRes data = taskService.queryTaskByPage(para.parameters);
                if(null!=data){
                    response.mes    =   "查询成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，生产时段查询失败");
                response.mes =  "生产时段信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产时段发生异常",e);
            response.mes =  "查询生产时段发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 新增
     * @return
     */
    @RequestMapping("/ds/addtask")
    public EdmpResponse addTask(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(taskService.insert(para.parameters)) {
                    response.mes = "新增生产时段操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增生产时段操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增生产时段");
                response.mes = "新增生产时段操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("新增生产时段发生异常",e);
            response.mes =  "新增生产时段发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("/ds/deltask")
    public EdmpResponse delTask(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(taskService.delete(para.parameters)) {
                    response.mes = "删除生产时段操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除生产时段操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除生产时段");
                response.mes = "删除生产时段操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除生产时段发生异常",e);
            response.mes =  "删除生产时段发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新
     * @return
     */
    @RequestMapping("/ds/updtask")
    public EdmpResponse updTask(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(taskService.update(para.parameters)) {
                    response.mes = "更新生产时段操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新生产时段操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新生产时段");
                response.mes = "更新生产时段操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("更新生产时段发生异常",e);
            response.mes =  "更新生产时段发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 不分页取生产时段信息
     * @return
     */
    @RequestMapping("/ds/quytaskonly")
    public EdmpResponse quyTaskOnly(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                List<Task> data = taskService.quyTaskOnly(para.parameters);
                if(null!=data) {
                    response.mes    = "查询生产时段操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询生产时段操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询生产时段");
                response.mes        = "查询生产时段操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产时段发生异常",e);
            response.mes =  "查询生产时段发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

}
