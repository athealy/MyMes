package com.jacksoft.dao;

import com.jacksoft.entity.wms.Warehouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WarehouseDao {

    int insWarehouse(@Param("list") List<Warehouse> warehouses);
    int delWarehouse(@Param("list") List<Warehouse> warehouses);
    int updWarehouse(@Param("filter") Warehouse filter,@Param("replacement") Warehouse replacement);

    List<Warehouse> queryWarehouseByPage(@Param("id") String id,
                                         @Param("title") String title,
                                         @Param("row") int row,
                                         @Param("page") int page);

    int countWarehouse(@Param("id") String id,
                     @Param("title") String title);

}
