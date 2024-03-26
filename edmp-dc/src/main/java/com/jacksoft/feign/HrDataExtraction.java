package com.jacksoft.feign;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 调用分析后数据提取接口
 */
@FeignClient(value = "EDMP-HR", fallback = HrDataExtractionFallback.class)
public interface HrDataExtraction {

    @RequestMapping(value="{path}", method= RequestMethod.POST)
    public EdmpResponse consult(@RequestBody EdmpRequertPara analypara, @PathVariable("path") String path);

}
