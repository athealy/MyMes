package com.jacksoft.data;

import com.jacksoft.dao.RouterDao;
import com.jacksoft.entity.dc.RouterData;
import com.jacksoft.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class RouterInfoConfig {

    @Resource
    private RouterDao routerDao;

    @Resource
    private RedisUtil redisUtil;

    //@Value("dc.effetime")
    private Long lEffetime = 86400l*365;

    /**
     * 装载数据
     * @return
     */
    public boolean setup(){
        boolean result  = false;
        try {
            List<RouterData> routerData =   routerDao.queryAll();
            routerData.forEach(item->{
                redisUtil.set(item.getSerial(),item.getRouter(),lEffetime);
            });
            result  =   true;
        } catch (Exception e) {
            log.error("加载路由数据发生异常",e);
            result  =   false;
        }
        return result;
    }

    /**
     * 读取缓存
     * @param serial
     * @return
     */
    public String readMapper(String serial) throws Exception{
        return (String)redisUtil.get(serial);
    }

}
