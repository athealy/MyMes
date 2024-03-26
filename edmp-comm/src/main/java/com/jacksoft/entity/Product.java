package com.jacksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    public String id;
    public int order;
    public int actuality;
    public int finish;
    public int capacity;
    public String state;
    public String department;
    public String today;
    public String batch;
    public String deliverydate;
}
