package com.jacksoft.entity.ds;

import com.jacksoft.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAccData extends Product {

    public int outputTotalToday;  //当日已生产量
    public int planTotalToday;    //当日计划生产数

}
