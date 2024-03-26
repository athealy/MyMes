package com.jacksoft.entity.wms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    public String id;
    public String title;
    public int stckingarea;
    public int shelves;
    public int stckingupper;
    public int shelvesupper;
    public String addr;
    public String planar;
    public String description;
}
