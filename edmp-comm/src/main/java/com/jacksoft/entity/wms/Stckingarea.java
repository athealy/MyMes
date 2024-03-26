package com.jacksoft.entity.wms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stckingarea {
    public String warehouse;
    public String id;
    public int upperlimit;
    public float saved;
    public String responsible;
    public String title;
}
