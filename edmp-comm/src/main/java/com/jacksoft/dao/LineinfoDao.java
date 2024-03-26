package com.jacksoft.dao;

import com.jacksoft.entity.Lineinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LineinfoDao {

    int insLineinfo(@Param("list")List<Lineinfo> lineinfoes);
    int delLineinfo(@Param("list")List<Lineinfo> lineinfoes);
    int updLineinfo(@Param("filter") Lineinfo filter,@Param("replacement") Lineinfo replacement);

    List<Lineinfo> queryLineinfoByPage(@Param("id") String id,
                                       @Param("department") String department,
                                       @Param("state") String state,
                                       @Param("row") int row,
                                       @Param("page") int page);

    int accountLineinfo(@Param("id") String id,
                               @Param("department") String department,
                               @Param("state") String state);

    List<Lineinfo> quyAplOnly(@Param("id") String id,
                              @Param("department") String department,
                              @Param("state") String state);

    List<Lineinfo> quyActivity();
}
