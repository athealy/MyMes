package com.jacksoft.entity.hr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    String id;
    String username;
    String company;
    String department;
    String passwd;
    String state;
    String role;

}
