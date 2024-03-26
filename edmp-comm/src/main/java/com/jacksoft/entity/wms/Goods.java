package com.jacksoft.entity.wms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    public String barcode;  //产品二维码
    public String product;
    public String batch;
    public int    serial;   //流水号
    public String state;    //状态：1测试、2维修、3暂存、4出库
    public String gtype;    //产品类型

}
