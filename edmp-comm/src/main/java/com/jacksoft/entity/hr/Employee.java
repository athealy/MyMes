package com.jacksoft.entity.hr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    String id;
    String username;
    String duties;
    String photo;
    String department;
    String gender;
}
