package com.jacksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产能统计记录类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialAcc {
    public String product;
    public int chip;
    public int ic;
    public int element;
    public int equalNum;    //等效元件数(点数)
}
