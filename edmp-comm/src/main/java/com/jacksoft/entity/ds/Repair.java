package com.jacksoft.entity.ds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产品维修记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repair {
    public int      serial;
    public String   sdate;       //发生日期
    public String   product;
    public String   barcode;     //产品条形码
    public String   department;
    public String   maintainer;  //维修人员标识
    public String   defect;      //不良现象描述
    public String   liable;      //责任归属: SMD为'S'、后焊为'H'、装配为'C'、测试为'T'、物料为'M'、其他为'O'
    public String   handle;      //处置: 1维修、2暂存、3转出复测
    public String   batch;       //批次号
}
