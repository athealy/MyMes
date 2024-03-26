package com.jacksoft.dao;

import com.jacksoft.entity.ds.Quality;
import com.jacksoft.entity.ds.Repair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RepairDao {

    int insRepair(@Param("list") List<Repair> qualities);
    int delRepair(@Param("list")List<Repair> qualities);
    int updRepair(@Param("filter") Repair filter,@Param("replacement") Repair replacement);

    List<Repair> queryRepairByPage(@Param("product") String product,
                                    @Param("department") String department,
                                    @Param("sdate") String sdate,
                                    @Param("barcode") String barcode,
                                    @Param("batch") String batch,
                                    @Param("row") int row,
                                    @Param("page") int page);

    int countRepair(@Param("product") String product,
                     @Param("department") String department,
                     @Param("sdate") String sdate,
                     @Param("barcode") String stime,
                     @Param("batch") String batch);

}
