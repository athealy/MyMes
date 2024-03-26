package com.jacksoft.util;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.util.DruidPasswordCallback;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * 功能说明：通过密码密文和公钥来解密成明文密码
 * @date 2021/10/12
 */
@Slf4j
public class DBPasswordCallBack extends DruidPasswordCallback {

    /**
     * 功能说明： 重写源码的setProperties方法使其对密文进性解密，
     * 若不重写则会导致druid拿密文去连接数据库导致密码错误无法连接数据库
     * @param properties
     * @return
     */
    @Override
    public void setProperties(Properties properties) {
        try {
            super.setProperties(properties);
            String password = null!=properties && null!=properties.get("password") ? properties.get("password").toString() : null;
            if(null!=password) {
                String decrypt = ConfigTools.decrypt(password);
                setPassword(decrypt.toCharArray());
            }else{
                log.error("没有设置密码,无法连接数据库");
            }
        } catch (Exception e) {
            log.error("解密异常，无法连接数据库，异常为：", e);
        }
    }

    /**
     * 功能说明： 通过druid解密出明文密码提供给ag模块使用
     * @param password 密码的密文
     * @param publickey 密码的公钥
     * @return
     * @author Wang Kangquan
     * @date 2021/10/12
     */
    public String getPwd(String password, String publickey) throws Exception {
        return ConfigTools.decrypt(publickey, password);
    }

    /**
     * 功能说明： 通过druid解密出明文密码提供给ag模块使用
     * @param password
     * @return
     * @throws Exception
     */
    public String getPwd(String password) throws Exception{
        return ConfigTools.decrypt(password);
    }

}
