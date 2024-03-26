package com.jacksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 产能参数表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capacity {
    public String product;
    public float  transfertime;
    public float  capacity;
    public String department;
    public String line;
    public String description;
}
