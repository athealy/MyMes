package com.jacksoft.dao;

import com.jacksoft.entity.Capacity;
import com.jacksoft.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CapacityInfoDao {

    int insCapacityInfo(@Param("list") List<Capacity> capacity);
    int delCapacityInfo(@Param("list")List<Capacity> capacities);
    int updCapacityInfo(@Param("filter") Capacity filter,@Param("replacement") Capacity replacement);

    List<Capacity> queryCapacityByPage(@Param("product") String product,
                                       @Param("department") String department,
                                       @Param("row") int row,
                                       @Param("page") int page);

    int countCapacityInfo(@Param("product") String product,
                          @Param("department") String department);

    List<Capacity> quyProdOnly(@Param("department") String department);

}
