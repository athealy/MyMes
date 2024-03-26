package com.jacksoft.service;

import com.alibaba.fastjson.JSON;
import com.jacksoft.dao.PostinfoDao;
import com.jacksoft.entity.PagetationRes;
import com.jacksoft.entity.hr.Employee;
import com.jacksoft.entity.hr.Postinfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 岗位信息服务类
 */
@Slf4j
@Service
public class PostinfoService {

    @Resource
    private PostinfoDao postinfoDao;

    /**
     * 分页查询
     * @param parameters
     * @return
     */
    public PagetationRes queryPostByPage(Object parameters) throws Exception {
        if(null!=parameters){
            Map<String,Object> filter   =   JSON.parseObject(parameters.toString(),Map.class);
            int row     =   10;
            int page    =   1;
            String postid       = null;
            String posttitle    = null;
            String department   = null;
            if(null!=filter.get("row") && null!=filter.get("page")){
                row     =   Integer.parseInt(filter.get("row").toString());
                page    =   Integer.parseInt(filter.get("page").toString());
            }
            if(null!=filter.get("filters")){
                Map<String,Object> filters  =   JSON.parseObject(filter.get("filters").toString(),Map.class);
                postid      = null!=filters.get("postid")?filters.get("postid").toString():null;
                posttitle   = null!=filters.get("posttitle")?filters.get("posttitle").toString():null;
                department  = null!=filters.get("department")?filters.get("department").toString():null;
            }
            PagetationRes   result =  new PagetationRes();
            result.total    =  postinfoDao.countPost(postid,posttitle,department);
            if(result.total>0){
                result.rows =  postinfoDao.queryPostByPage(postid,posttitle,department,row,page);
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
                List<Postinfo> list = (List<Postinfo>)paraMap.get("content");
                log.info("将要写入的岗位信息：{}",list);
                int resInt  =   postinfoDao.insPostinfo(list);
                if(resInt>0){
                    result = true;
                }else if(resInt==0) {
                    result = true;
                }else{
                    result =false;
                }
            }
        }else{
            log.info("新增岗位信息不能使用空指针参数");
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
                Postinfo filter =   JSON.parseObject(paraMap.get("filter").toString(),Postinfo.class);
                Postinfo replacement    =   JSON.parseObject(paraMap.get("replacement").toString(),Postinfo.class);

                if(postinfoDao.updPostinfo(filter,replacement)>0){
                    result  =   true;
                }else{
                    log.info("更新岗位信息操作失败!");
                }
            }else{
                log.info("没有规范使用参数，缺少filter或者replacement");
            }
        }else{
            log.info("更新岗位信息不能使用空指针参数");
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
                if(postinfoDao.delPostinfo((List<Postinfo>)paraMap.get("filter"))>=0){
                    result  =   true;
                }else{
                    log.info("SQL语句执行异常，无法删除岗位信息");
                }
            }else{
                log.info("没有规范使用参数，缺少filter");
            }
        }else{
            log.info("删除岗位信息不能使用空指针参数");
        }
        return result;
    }

    /**
     * 取岗位名称
     * @param parameter
     * @return
     * @throws Exception
     */
    public List<Postinfo> quyDistincTitle(Object parameter) throws Exception{
        List<Postinfo> result = null;
        if(null!=parameter){
            Map<String,Object> paraMap  =   JSON.parseObject(parameter.toString(),Map.class);
            if(null!=paraMap.get("filter")){
                Map<String,String> filter = JSON.parseObject(paraMap.get("filter").toString(),Map.class);
                result = postinfoDao.quyDistincTitle(filter.get("department"));
            }else{
                result = postinfoDao.quyDistincTitle(null);
            }
        }else{
            result = postinfoDao.quyDistincTitle(null);
        }
        return result;
    }

}
