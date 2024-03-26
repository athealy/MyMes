package com.jacksoft.entity.ds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 品质控制
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quality {
    public int      serial;
    public String   product;
    public String   department;
    public String   barcode;     //产品条形码
    public String   sdate;       //测试发生日期
    public String   tester;      //测试人员工号
    public String   parametes;   //测试参数
    public String   result;      //测试结果
    public String   stime;       //测试发生时段
    public String   handle;      //处置：1转存、2维修、3复检、4暂存、5报废
    public String   reviewer;    //复核人
    public String   batch;       //批次号
}
