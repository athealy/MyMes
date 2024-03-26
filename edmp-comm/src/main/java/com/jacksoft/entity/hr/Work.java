package com.jacksoft.entity.hr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排班记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work {
    public String department;
    public String employee;
    public String sdate;        //工作日期
    public String duties;       //岗位标识
    public String work_type;    //班制
    public String attendance;   //出勤情况: 1全勤(默认)、2迟到、3早退、4病假、5事假、6旷工
}
