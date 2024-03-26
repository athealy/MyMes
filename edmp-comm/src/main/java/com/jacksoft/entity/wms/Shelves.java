package com.jacksoft.entity.wms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shelves {
    public String warehouse;     //仓库标识
    public String id;            //货架标识
    public String title;         //货架名称
    public int upperlimit;       //货架存储上限
    public float saved;          //货架存储百分比
    public String responsible;   //管理员标识
}
