package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.dao.UserDao;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.hr.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自动化产线信息服务类
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private UserDao userDao;

    /**
     * 分页查询
     * @param parameters
     * @return
     */
    public PagetationRes queryUserByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String id   =   null;
            String department  = null;
            String username  = null;
            String state =  null;
            String company =  null;

            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                state = null!=filters.get("state")?filters.get("state").toString():null;
                username = null!=filters.get("username")?filters.get("username").toString():null;
                company = null!=filters.get("company")?filters.get("company").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    = userDao.countUser(id,username,department,state,company);
            if(result.total>0){
                result.rows =  userDao.queryUserByPage(id,username,department,state,company,row,page);
            }else{
                result.rows =   new ArrayList<>();
            }
            return result;
        }else{
            return null;
        }
    }

    /**
     * 新增
     * @param parameters
     * @return
     */
    public boolean insert(Object parameters) throws Exception{
        boolean result  = false;
        if(null!=parameters){
            Map<String,Object> paraMap  = JSON.parseObject(parameters.toString(),Map.class);
            if(null!=paraMap.get("content")){
                log.info("{}",paraMap.get("content"));
                //List<User> list = (List<User>)paraMap.get("content");
                List<Object> list = JSON.parseObject(paraMap.get("content").toString(),List.class);
                //log.info("将要写入的用户信息：{}",list);
                List<User> replace = new ArrayList<>();
                for(int i=0;i<list.size();i++){

                    Map<String,Object> map = JSON.parseObject(list.get(i).toString(),Map.class);
                    User user   =    new User();
                    user.setPasswd("12345678");
                    user.setId(map.get("id").toString());
                    user.setRole(map.get("role").toString());
                    user.setUsername(map.get("username").toString());
                    user.setCompany(map.get("company").toString());
                    user.setDepartment(map.get("department").toString());
                    user.setState(map.get("state").toString());

                    replace.add(user);
                }
                int resInt  =   userDao.insUser(replace);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增用户信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 更新
     * @param parameter
     * @return
     * @throws Exception
     */
    public boolean update(Object parameter) throws Exception{
        boolean result  = false;
        if(null!=parameter){
            Map<String,Object> paraMap  =   JSON.parseObject(parameter.toString(),Map.class);
            if(null!=paraMap.get("filter") && null!=paraMap.get("replacement")){

                User filter         =   JSON.parseObject(paraMap.get("filter").toString(),User.class);
                User replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),User.class);
                if(userDao.updUser(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新用户信息操作失败!");
                }

            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新用户信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 删除
     * @param parameter
     * @return
     * @throws Exception
     */
    public boolean delete(Object parameter) throws Exception{
        boolean result  = false;
        if(null!=parameter){
            Map<String,Object> paraMap  =   JSON.parseObject(parameter.toString(),Map.class);
            if(null!=paraMap.get("filter")){
                if(userDao.delUser((List<User>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除用户信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除用户信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 查询指定用户信息(无密码信息)
     * @param parameters
     * @return
     */
    public User queryUserinfo(Object parameters) throws Exception {
        User result = null;
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            String id           = null;
            String department   = null;
            String username     = null;
            String state        = null;
            String role         = null;
            String company      = null;
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                department = null!=filters.get("department")?filters.get("department").toString():null;
                state = null!=filters.get("state")?filters.get("state").toString():null;
                username = null!=filters.get("username")?filters.get("username").toString():null;
                company = null!=filters.get("company")?filters.get("company").toString():null;
                role = null!=filters.get("role")?filters.get("role").toString():null;
            }
            List<User> data = userDao.quyUserNopagetion(id,username,department,state,company,role);
            if(null!=data && !data.isEmpty()){
                result  =   new User();
                result.setId(data.get(0).getId());
                result.setDepartment(data.get(0).getDepartment());
                result.setUsername(data.get(0).getUsername());
                result.setCompany(data.get(0).getCompany());
                result.setRole(data.get(0).getRole());
                result.setState(data.get(0).getState());
            }else{
                log.info("没有找到指定的用户信息,条件:{}",filter);
            }

        }else{
            log.info("缺少查询用户信息的条件");
        }
        return result;
    }

    /**
     * 验证用户信息
     * @param parameters
     * @return
     */
    public int chkUserinfo(Object parameters) throws Exception {
        int result = -1;
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            String id           = null;
            String passwd       = null;
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                id  =   null!=filters.get("id")?filters.get("id").toString():null;
                passwd = null!=filters.get("passwd")?filters.get("passwd").toString():null;
            }
            result = userDao.chkUserinfo(id,passwd);
        }else{
            log.info("缺少查询用户信息的条件");
        }
        return result;
    }

    /**
     * 查询历史录入公司信息
     * @param parameters
     * @return
     */
    public List<User> quyCompany(Object parameters) throws Exception {
        List<User> result = null;
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            if(null!=filter.get("filters")){
            }
            result = userDao.quyCompany();
        }else{
            log.info("缺少查询用户信息的条件");
        }
        return result;
    }

    /**
     * 重置密码
     * @param parameter
     * @return
     * @throws Exception
     */
    public boolean reset(Object parameter) throws Exception{
        boolean result  = false;
        if(null!=parameter){
            Map<String,Object> paraMap  =   JSON.parseObject(parameter.toString(),Map.class);
            if(null!=paraMap.get("filter")){
                User filter         =   JSON.parseObject(paraMap.get("filter").toString(),User.class);
                User replacement    =   new User();
                replacement.setPasswd("12345678");
                if(userDao.updUser(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("重置用户信息操作失败!");
                }

            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("更新用户信息不能使用空指针参数");
        }
        return result;
    }

}
