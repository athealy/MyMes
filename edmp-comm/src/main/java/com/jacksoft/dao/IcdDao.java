package com.jacksoft.dao;

import com.jacksoft.entity.wms.Icd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IcdDao {

    int insIcd(@Param("list") List<Icd> icds);
    int delIcd(@Param("list") List<Icd> icds);
    int updIcd(@Param("filter") Icd filter,@Param("replacement") Icd replacement);

    List<Icd> queryIcdByPage(@Param("warehouse") String warehouse,
                                    @Param("id") String id,
                                    @Param("title") String title,
                                    @Param("row") int row,
                                    @Param("page") int page);

    int countIcd(@Param("warehouse") String product,
                     @Param("id") String department,
                     @Param("title") String sdate);

}
