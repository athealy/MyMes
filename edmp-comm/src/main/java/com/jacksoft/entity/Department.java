package com.jacksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    public String id;
    public String name;
    public String description;
}
