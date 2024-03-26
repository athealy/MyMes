package com.jacksoft.control;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.hr.User;
import com.jacksoft.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息访问接口
 * @since 20240102
 */
@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 查询用户信息
     * @return
     */
    @RequestMapping("/hr/queryuser")
    public EdmpResponse queryUser(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                PagetationRes data = userService.queryUserByPage(para.parameters);
                if(null!=data) {
                    response.mes    =   "查询用户信息操作成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes    =   "SQL语句失败,请与管理员联系查看日志";
                    response.state  =   "0";
                }
            }else{
                log.error("没有查询条件，用户信息查询失败");
                response.mes =  "查询用户信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询用户信息发生异常",e);
            response.mes =  "查询用户信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 新增用户信息
     * @return
     */
    @RequestMapping("/hr/adduser")
    public EdmpResponse addUser(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(userService.insert(para.parameters)) {
                    response.mes = "新增用户信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增用户信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增用户信息");
                response.mes = "新增用户信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("新增用户信息发生异常",e);
            response.mes =  "新增用户信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除用户信息
     * @return
     */
    @RequestMapping("/hr/deluser")
    public EdmpResponse delUser(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(userService.delete(para.parameters)) {
                    response.mes = "删除用户信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除用户信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除用户信息");
                response.mes = "删除用户信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除用户信息发生异常",e);
            response.mes =  "删除用户信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新用户信息
     * @return
     */
    @RequestMapping("/hr/upduser")
    public EdmpResponse updUser(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(userService.update(para.parameters)) {
                    response.mes = "更新用户信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新用户信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新用户信息");
                response.mes = "更新用户信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("更新用户信息发生异常",e);
            response.mes =  "更新用户信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询用户信息
     * @return
     */
    @RequestMapping("/hr/queryuserinfo")
    public EdmpResponse queryUserinfo(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                User data = userService.queryUserinfo(para.parameters);
                if(null!=data) {
                    response.mes    =   "查询用户信息操作成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes    =   "SQL语句失败,请与管理员联系查看日志";
                    response.state  =   "0";
                }
            }else{
                log.error("没有查询条件，用户信息查询失败");
                response.mes =  "查询用户信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询用户信息发生异常",e);
            response.mes =  "查询用户信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询用户信息
     * @return
     */
    @RequestMapping("/hr/chkuserinfo")
    public EdmpResponse chkUserinfo(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                int data = userService.chkUserinfo(para.parameters);
                if(1==data) {
                    response.mes    =   "查询用户信息操作成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes    =   "SQL语句失败,请与管理员联系查看日志";
                    response.state  =   "0";
                }
            }else{
                log.error("没有查询条件，用户信息查询失败");
                response.mes =  "查询用户信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询用户信息发生异常",e);
            response.mes =  "查询用户信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询公司历史录入信息
     * @return
     */
    @RequestMapping("/hr/quycompany")
    public EdmpResponse quyCompany(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                List<User> data = userService.quyCompany(para.parameters);
                if(null!=data) {
                    response.mes    =   "查询历史公司信息操作成功";
                    response.state  =   "1";
                    response.res    =   data;
                }else{
                    response.mes    =   "SQL语句失败,请与管理员联系查看日志";
                    response.state  =   "0";
                }
            }else{
                log.error("没有查询条件，历史公司信息查询失败");
                response.mes =  "查询历史公司信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询历史公司信息发生异常",e);
            response.mes =  "查询历史公司信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 重置用户密码
     * @return
     */
    @RequestMapping("/hr/resetuser")
    public EdmpResponse ressetPwd(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            //log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(userService.reset(para.parameters)) {
                    response.mes = "重置密码操作成功";
                    response.state = "1";
                }else{
                    response.mes = "重置密码操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法执行重置操作");
                response.mes = "重置密码操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("重置密码发生异常",e);
            response.mes =  "重置密码发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }
}
