package com.jacksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物料清单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    public String product;
    public String name;         //元件名
    public int    equmaterial;  //等效元件数
    public int    materialnum;  //元件数
}
