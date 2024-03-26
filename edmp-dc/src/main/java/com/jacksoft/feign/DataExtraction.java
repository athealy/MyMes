package com.jacksoft.feign;

import com.jacksoft.entity.EdmpRequertPara;
import com.jacksoft.entity.EdmpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调用分析后数据提取接口
 */
@FeignClient(value = "EDMP-DS", fallback = DataExtractionFallback.class)
public interface DataExtraction {

    @RequestMapping(value="{path}", method= RequestMethod.POST)
    public EdmpResponse consult(@RequestBody EdmpRequertPara analypara, @PathVariable("path") String path);

}
