package com.jacksoft.entity.wms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ic {

    public String sdate;
    public String warehouse;
    public String type;
    public String area;
    public String gtype;
    public String goods;
    public String bdirection;
    public int amount;

}
