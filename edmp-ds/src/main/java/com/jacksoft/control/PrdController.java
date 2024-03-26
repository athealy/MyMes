package com.jacksoft.control;

import com.jacksoft.entity.*;
import com.jacksoft.entity.ds.ProductAccData;
import com.jacksoft.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品信息接口
 * @since 20240102
 */
@Slf4j
@RestController
public class PrdController {

    @Resource
    private ProductService productService;

    /**
     * 查询产品信息
     * @return
     */
    @RequestMapping("/ds/queryproducts")
    public EdmpResponse queryProducts(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                PagetationRes data = productService.queryProductByPage(para.parameters);
                if(null!=data) {
                    response.mes    = "查询产品信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，产品信息查询失败");
                response.mes =  "查询产品信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询产品信息发生异常",e);
            response.mes =  "查询产品信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 新增产品信息
     * @return
     */
    @RequestMapping("/ds/addproducts")
    public EdmpResponse addProducts(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(productService.insert(para.parameters)) {
                    response.mes = "新增产品信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增产品信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增产品信息");
                response.mes = "新增产品信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("新增产品信息发生异常",e);
            response.mes =  "新增产品信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除产品信息
     * @return
     */
    @RequestMapping("/ds/delproduct")
    public EdmpResponse delProduct(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(productService.delete(para.parameters)) {
                    response.mes = "删除产品信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除产品信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除产品信息");
                response.mes = "删除产品信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除产品信息发生异常",e);
            response.mes =  "删除产品信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新产品信息
     * @return
     */
    @RequestMapping("/ds/updproduct")
    public EdmpResponse updProduct(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(productService.update(para.parameters)) {
                    response.mes = "更新产品信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新产品信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新产品信息");
                response.mes = "更新产品信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("更新产品信息发生异常",e);
            response.mes =  "更新产品信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 取不重复的产品信息
     * @return
     */
    @RequestMapping("/ds/quyprodonly")
    public EdmpResponse quyProductOnly(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                List<Product> data = productService.quyProductOnly(para.parameters);
                if(null!=data) {
                    response.mes    = "查询产品信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询产品信息操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询产品信息");
                response.mes        = "查询产品信息操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询产品信息发生异常",e);
            response.mes            = "查询产品信息发生异常,请与管理员联系查看日志";
            response.state          = "-1";
        }
        return response;
    }

    /**
     * 查询已有的产品名称
     * @return
     */
    @RequestMapping("/ds/quyprodsimple")
    public EdmpResponse quyProdSimple(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                List<Product> data = productService.quyProdSimple(para.parameters);
                if(null!=data) {
                    response.mes    = "查询产品信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询产品信息操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询产品信息");
                response.mes        = "查询产品信息操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询产品信息发生异常",e);
            response.mes            = "查询产品信息发生异常,请与管理员联系查看日志";
            response.state          = "-1";
        }
        return response;
    }

    /**
     * 查询指定产线当前生产的产品统计信息
     * @return
     */
    @RequestMapping("/ds/quyprodacc")
    public EdmpResponse quyProdAcc(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                ProductAccData data = productService.quyProdAcc(para.parameters);
                if(null!=data) {
                    response.mes    = "查询产品信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询产品信息操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询产品信息");
                response.mes        = "查询产品信息操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询产品信息发生异常",e);
            response.mes            = "查询产品信息发生异常,请与管理员联系查看日志";
            response.state          = "-1";
        }
        return response;
    }

    /**
     * 查询指定产线当前生产的产品统计信息(多行)
     * @return
     */
    @RequestMapping("/ds/quyprodaccmult")
    public EdmpResponse quyProdAccMult(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                List<ProductAccData> data = productService.quyProdAccMult(para.parameters);
                if(null!=data) {
                    response.mes    = "查询产品信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询产品信息操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询产品信息");
                response.mes        = "查询产品信息操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询产品信息发生异常",e);
            response.mes            = "查询产品信息发生异常,请与管理员联系查看日志";
            response.state          = "-1";
        }
        return response;
    }

    /**
     * 查询当前未完成产品的完成情况
     * @return
     */
    @RequestMapping("/ds/quyprodfinish")
    public EdmpResponse quyProdFinish(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                Map<String,Object> data = productService.quyProdFinish(para.parameters);
                if(null!=data) {
                    response.mes    = "查询产品信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询产品信息操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询产品信息");
                response.mes        = "查询产品信息操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询产品信息发生异常",e);
            response.mes            = "查询产品信息发生异常,请与管理员联系查看日志";
            response.state          = "-1";
        }
        return response;
    }

    /**
     * 查询当前未完成产品的情况
     * @return
     */
    @RequestMapping("/ds/quyprodincomplete")
    public EdmpResponse quyProdIncomplete(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para) {
                List<Product> data = productService.quyProdInconplete(para.parameters);
                if(null!=data) {
                    response.mes    = "查询产品信息操作成功";
                    response.state  = "1";
                    response.res    = data;
                }else{
                    response.mes    = "查询产品信息操作失败";
                    response.state  = "0";
                }
            }else{
                log.info("没有必要的参数，无法查询产品信息");
                response.mes        = "查询产品信息操作失败";
                response.state      = "-1";
            }
        } catch (Exception e) {
            log.error("查询产品信息发生异常",e);
            response.mes            = "查询产品信息发生异常,请与管理员联系查看日志";
            response.state          = "-1";
        }
        return response;
    }

}
