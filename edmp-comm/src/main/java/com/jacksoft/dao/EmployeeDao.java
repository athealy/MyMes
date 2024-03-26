package com.jacksoft.dao;

import com.jacksoft.entity.hr.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeDao {

    int insEmployee(@Param("list")List<Employee> employee);
    int delEmployee(@Param("list")List<Employee> employee);
    int updEmployee(@Param("filter") Employee filter,@Param("replacement") Employee replacement);

    List<Employee> queryEmployeeByPage(@Param("id") String id,
                                       @Param("username") String department,
                                       @Param("duties") String state,
                                       @Param("row") int row,
                                       @Param("page") int page);

    int countEmployee(@Param("id") String id,
                               @Param("username") String department,
                               @Param("duties") String state);
}
