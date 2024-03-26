package com.jacksoft.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtil extends StringUtils {

    /**
     * 校验字符串是否是整形或长整形非负数
     * @param str
     * @return
     * @throws Exception
     */
    public static boolean isInteger(String str) throws Exception{
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
