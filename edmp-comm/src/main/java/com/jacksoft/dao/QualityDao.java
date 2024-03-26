package com.jacksoft.dao;

import com.jacksoft.entity.Capacity;
import com.jacksoft.entity.ds.Quality;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QualityDao {

    int insQulity(@Param("list") List<Quality> qualities);
    int delQulity(@Param("list")List<Quality> qualities);
    int updQulity(@Param("filter") Quality filter,@Param("replacement") Quality replacement);

    List<Quality> queryQulityByPage(@Param("product") String product,
                                    @Param("department") String department,
                                    @Param("sdate") String sdate,
                                    @Param("stime") String stime,
                                    @Param("batch") String batch,
                                    @Param("row") int row,
                                    @Param("page") int page);

    int countQuality(@Param("product") String product,
                     @Param("department") String department,
                     @Param("sdate") String sdate,
                     @Param("stime") String stime,
                     @Param("batch") String batch);

}
