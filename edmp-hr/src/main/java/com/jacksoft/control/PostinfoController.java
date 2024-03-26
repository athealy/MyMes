package com.jacksoft.control;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.hr.Postinfo;
import com.jacksoft.service.PostinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 岗位信息管理接口
 */
@Slf4j
@RestController
public class PostinfoController {

    private PostinfoService postinfoService;

    /**
     * 分页查询岗位
     * @param para
     * @return
     */
    @RequestMapping("/hr/querypostinfo")
    public EdmpResponse queryPostByPage(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            if(null!=para){
                PagetationRes data = postinfoService.queryPostByPage(para.parameters);
                if(null!=data){
                    response.mes    = "查询岗位信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，岗位信息查询失败");
                response.mes =  "查询岗位信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询岗位信息发生异常",e);
            response.mes =  "查询岗位信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询岗位名称（不重复）
     * @param para
     * @return
     */
    @RequestMapping("/hr/quypostname")
    public EdmpResponse quyDistincTitle(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            if(null!=para){
                List<Postinfo> data = postinfoService.quyDistincTitle(para.parameters);
                if(null!=data){
                    response.mes    = "查询岗位名称操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，岗位名称查询失败");
                response.mes =  "查询岗位名称发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询岗位名称发生异常",e);
            response.mes =  "查询岗位名称发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 新增职员信息
     * @return
     */
    @RequestMapping("/hr/addpostinfo")
    public EdmpResponse addEmployee(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(postinfoService.insert(para.parameters)) {
                    response.mes = "新增岗位信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增岗位信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增岗位信息");
                response.mes = "新增岗位信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询岗位信息发生异常",e);
            response.mes =  "查询岗位信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除职员信息
     * @return
     */
    @RequestMapping("/hr/delpostinfo")
    public EdmpResponse delEmployee(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(postinfoService.delete(para.parameters)) {
                    response.mes = "删除岗位信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除岗位信息操作成功";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除岗位信息");
                response.mes = "删除岗位信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除岗位信息发生异常",e);
            response.mes =  "删除岗位信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新职员信息
     * @return
     */
    @RequestMapping("/hr/updpostinfo")
    public EdmpResponse updEmployee(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(postinfoService.update(para.parameters)) {
                    response.mes = "更新岗位信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新岗位信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新岗位信息");
                response.mes = "更新岗位信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("更新岗位信息发生异常",e);
            response.mes =  "更新岗位信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

}
