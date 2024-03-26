package com.jacksoft.dao;

import com.jacksoft.entity.hr.Employee;
import com.jacksoft.entity.hr.Postinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostinfoDao {

    int insPostinfo(@Param("list")List<Postinfo> postinfos);
    int delPostinfo(@Param("list")List<Postinfo> postinfos);
    int updPostinfo(@Param("filter") Postinfo filter,@Param("replacement") Postinfo replacement);

    List<Postinfo> queryPostByPage(@Param("postid") String postid,
                                       @Param("posttitle") String posttitle,
                                       @Param("department") String department,
                                       @Param("row") int row,
                                       @Param("page") int page);

    int countPost(@Param("postid") String postid,
                      @Param("posttitle") String posttitle,
                      @Param("department") String department);

    List<Postinfo> quyDistincTitle(@Param("department") String department);
}
