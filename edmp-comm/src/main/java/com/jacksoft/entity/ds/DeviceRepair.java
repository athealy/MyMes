package com.jacksoft.entity.ds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 保养、维修记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRepair {
    public int      serial;
    public String   device;
    public String   dispose;       //处置方式
    public String   sdate;         //执行日期
    public String   stime;         //执行时间
    public String   excutor;       //执行人工号
    public String   fdate;         //完工日期
    public String   ftime;         //完工时间
    public String   description;   //保养、维修说明
}
