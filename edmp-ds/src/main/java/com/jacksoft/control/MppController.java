package com.jacksoft.control;

import com.jacksoft.entity.*;
import com.jacksoft.service.MppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 生产参数管理接口
 */
@Slf4j
@RestController
public class MppController {

    @Resource
    private MppService mppService;

    @RequestMapping("/ds/addcapacity")
    public EdmpResponse insCapacityInfo(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(mppService.insCapacityInfo(para.parameters)) {
                    response.mes = "新增生产参数信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增生产参数信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增生产参数信息");
                response.mes = "新增生产参数信息操作失败";
                response.state = "-1";
            }
        }
        catch(DuplicateKeyException e){
            log.error("新增生产参数信息发生异常",e);
            response.mes =  "新增的生产参数已经有记录";
            response.state = "0";
        }
        catch (Exception e) {
            log.error("新增生产参数信息发生异常",e);
            response.mes =  "新增生产参数信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除生产参数信息
     * @return
     */
    @RequestMapping("/ds/delcapacityInfo")
    public EdmpResponse delCapacityInfo(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(mppService.delCapacityInfo(para.parameters)) {
                    response.mes = "删除生产参数信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除生产参数信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除生产参数信息");
                response.mes = "删除生产参数信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除生产参数信息发生异常",e);
            response.mes =  "删除生产参数信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新生产计划信息
     * @return
     */
    @RequestMapping("/ds/updcapacityInfo")
    public EdmpResponse updCapacityInfo(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(mppService.updCapacityInfo(para.parameters)) {
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
     * 查询生产参数
     * @return
     */
    @RequestMapping("/ds/querycapacity")
    public EdmpResponse quyCapacityInfo(@RequestBody EdmpRequertPara para) {
        EdmpResponse response = new EdmpResponse();
        log.info("接收到参数：{}",para);
        try {
            if(null!=para && null!=para.parameters) {
                PagetationRes data = mppService.queryCapacityByPage(para.parameters);
                if(null!=data) {
                    response.mes = "查询生产参数信息操作成功";
                    response.state = "1";
                    response.res = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，生产参数信息查询失败");
                response.mes =  "查询生产参数信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产参数信息操作发生异常",e);
            response.mes =  "查询生产参数信息操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询生产参数表中的产品标识
     * @return
     */
    @RequestMapping("/ds/quyprodincapa")
    public EdmpResponse quyProdInCapa(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                List<Capacity> data = mppService.quyProductInCapa(para.parameters);
                if(null!=data) {
                    response.mes = "查询生产参数中产品标识信息操作成功";
                    response.state = "1";
                    response.res = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，生产参数中产品标识信息查询失败");
                response.mes =  "查询生产参数中产品标识信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询生产参数中产品标识信息操作发生异常",e);
            response.mes =  "查询生产参数中产品标识信息操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    @RequestMapping("/ds/addmaterial")
    public EdmpResponse insMaterial(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(mppService.insMaterial(para.parameters)) {
                    response.mes = "新增物料清单信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "新增物料清单信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法新增物料清单信息");
                response.mes = "新增物料清单信息操作失败";
                response.state = "-1";
            }
        }
        catch(DuplicateKeyException e){
            log.error("新增物料清单信息发生异常",e);
            response.mes =  "新增的物料清单已经有记录";
            response.state = "0";
        }
        catch (Exception e) {
            log.error("新增物料清单信息发生异常",e);
            response.mes =  "新增物料清单信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 删除物料清单信息
     * @return
     */
    @RequestMapping("/ds/delmaterial")
    public EdmpResponse delMaterial(@RequestBody EdmpRequertPara para){
        EdmpResponse response = new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(mppService.delMaterial(para.parameters)) {
                    response.mes = "删除物料清单信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "删除物料清单信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法删除物料清单信息");
                response.mes = "删除物料清单信息操作失败";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("删除物料清单信息发生异常",e);
            response.mes =  "删除物料清单信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 更新生产计划信息
     * @return
     */
    @RequestMapping("/ds/updmaterial")
    public EdmpResponse updMaterial(@RequestBody EdmpRequertPara para){
        EdmpResponse response =  new EdmpResponse();
        try {
            log.info("接收到参数：{}",para);
            if(null!=para && null!=para.parameters) {
                if(mppService.updMaterial(para.parameters)) {
                    response.mes = "更新物料清单信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "更新物料清单信息操作失败";
                    response.state = "0";
                }
            }else{
                log.info("没有必要的参数，无法更新物料清单信息");
                response.mes = "更新物料清单信息操作失败";
                response.state = "-1";
            }
        }catch (Exception e) {
            log.error("更新物料清单信息发生异常",e);
            response.mes =  "更新物料清单信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询物料清单信息
     * @return
     */
    @RequestMapping("/ds/querymaterial")
    public EdmpResponse quyMaterial(@RequestBody EdmpRequertPara para) {
        EdmpResponse response = new EdmpResponse();
        log.info("接收到参数：{}",para);
        try {
            if(null!=para && null!=para.parameters) {
                PagetationRes data = mppService.queryMaterialByPage(para.parameters);
                if(null!=data) {
                    response.mes = "查询物料清单信息操作成功";
                    response.state = "1";
                    response.res = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，物料清单信息查询失败");
                response.mes =  "查询物料清单信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询物料清单信息操作发生异常",e);
            response.mes =  "查询物料清单信息操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 查询产品单位等效元件
     * @return
     */
    @RequestMapping("/ds/quyequalnum")
    public EdmpResponse quyEqualNum(@RequestBody EdmpRequertPara para) {
        EdmpResponse response = new EdmpResponse();
        log.info("接收到参数：{}",para);
        try {
            if(null!=para && null!=para.parameters) {
                List<Material> data = mppService.quyEqualNum(para.parameters);
                if(null!=data) {
                    response.mes = "查询产品单位等效元件信息操作成功";
                    response.state = "1";
                    response.res = data;
                }else{
                    response.mes =  "SQL语句失败,请与管理员联系查看日志";
                    response.state = "0";
                }
            }else{
                log.error("没有查询条件，产品单位等效元件信息查询失败");
                response.mes =  "查询产品单位等效元件信息发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("查询物料清单信息操作发生异常",e);
            response.mes =  "查询物料清单信息操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

    /**
     * 导入产能物料清单信息
     * @return
     */
    @RequestMapping("/ds/impmafupara")
    public EdmpResponse impMafuPara(@RequestBody EdmpRequertPara para) {
        EdmpResponse response = new EdmpResponse();
        log.info("接收到参数：{}", para);
        try {
            if(null!=para && null!=para.parameters) {
                if(mppService.impMafuPara(para.parameters)) {
                    response.mes = "导入产能物料清单信息操作成功";
                    response.state = "1";
                }else{
                    response.mes = "导入产能物料清单信息操作失败";
                    response.state = "0";
                }
            }else{
                log.error("没有必要条件，导入产能物料清单信息失败");
                response.mes =  "导入产能物料清单操作发生异常,请与管理员联系查看日志";
                response.state = "-1";
            }
        } catch (Exception e) {
            log.error("导入产能物料清单信息操作发生异常",e);
            response.mes =  "导入产能物料清单信息操作发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }
}
