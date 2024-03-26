package com.jacksoft.dao;

import com.jacksoft.entity.Department;
import com.jacksoft.entity.Lineinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentDao {

    int insDepart(@Param("list")List<Department> departments);
    int delDepart(@Param("list")List<Department> departments);
    int updDepart(@Param("filter") Department filter,@Param("replacement") Department replacement);

    List<Department> queryDepartByPage(@Param("id") String id,
                                       @Param("name") String name,
                                       @Param("row") int row,
                                       @Param("page") int page);

    int countDepart(@Param("id") String id, @Param("name") String name);

    List<Department> quyDepartOnly(@Param("id") String id,@Param("name") String name);

}
