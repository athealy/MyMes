package com.jacksoft.control;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import com.jacksoft.service.Transponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
public class Dispense {

    @Resource
    private Transponder transponder;

    @CrossOrigin
    @RequestMapping(value = "/dc/control")
    public EdmpResponse skip(EdmpRequertPara para) {

        EdmpResponse mes = new EdmpResponse();
        try {
            if(isPass(para)) {
                mes = transponder.toTrans(para);
                log.debug("返回值:{}",mes);
            }else{
                mes.setState("-1");
                mes.setMes("参数异常,联系管理员查看日志");
                log.error("数据请求出现异常,参数:{}", para);
            }
        } catch (Exception e) {
            mes.setState("-1");
            mes.setMes("操作异常,联系管理员查看日志");
            log.error("数据请求出现异常", e);
        }
        return mes;
    }

    /**
     * 验证参数
     * @param para
     * @return
     */
    private boolean isPass(EdmpRequertPara para){
        boolean result  =   false;
        if(null!=para && null!=para.serial && null!=para.parameters){
            result  =   true;
        }
        return result;
    }
}
