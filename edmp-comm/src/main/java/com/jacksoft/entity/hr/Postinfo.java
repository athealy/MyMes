package com.jacksoft.entity.hr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 岗位信息表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Postinfo {

    public String department;
    public String post_id;
    public String post_title;
    public String description;
    public int    post_member;

}
