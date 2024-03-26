package com.jacksoft.dao;

import com.jacksoft.entity.wms.Ics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IcsDao {

    int insIcs(@Param("list") List<Ics> icds);
    int delIcs(@Param("list") List<Ics> icds);
    int updIcs(@Param("filter") Ics filter,@Param("replacement") Ics replacement);

    List<Ics> queryIcsByPage(@Param("warehouse") String warehouse,
                                    @Param("id") String id,
                                    @Param("title") String title,
                                    @Param("row") int row,
                                    @Param("page") int page);

    int countIcs(@Param("warehouse") String product,
                     @Param("id") String department,
                     @Param("title") String sdate);

}
