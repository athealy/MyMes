package com.jacksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {
    public String  id;
    public String  name;
    public String  description;
    public String  department;
    public String  dop;         //设备出厂日期
    public String  pd;          //投产使用日期
    public String  state;       //设备状态
}
