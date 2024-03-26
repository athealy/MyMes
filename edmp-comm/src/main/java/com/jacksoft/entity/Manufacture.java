package com.jacksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacture {

    int id;
    String product;
    String department;
    String timezone;
    String classes;
    String line;
    int plan;
    int output;
    String mfg;
    String batch;

}
