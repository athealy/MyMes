package com.jacksoft.control;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * SMD生产车间信息接口
 * @Since 20240102
 */
@Slf4j
@RestController
public class SmdController {

    /**
     * 查询SMD车间信息
     * @return
     */
    @RequestMapping("/ds/querysmd")
    public EdmpResponse queryApl(@RequestBody EdmpRequertPara para){
        EdmpResponse response = null;
        try {
            log.info("接收到参数：{}",para);
            response = new EdmpResponse();
            response.mes =  "查询自动化生产线信息操作成功";
            response.state = "1";
            response.res = new HashMap();
        } catch (Exception e) {
            log.error("查询自动化生产线信息发生异常",e);
            response = new EdmpResponse();
            response.mes =  "查询自动化生产线信息发生异常,请与管理员联系查看日志";
            response.state = "-1";
        }
        return response;
    }

}
