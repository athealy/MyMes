package com.jacksoft.dao;

import com.jacksoft.entity.wms.Stckingarea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StckingareaDao {

    int insStckingarea(@Param("list") List<Stckingarea> tckingareaies);
    int delStckingarea(@Param("list")List<Stckingarea> tckingareaies);
    int updStckingarea(@Param("filter") Stckingarea filter,@Param("replacement") Stckingarea replacement);

    List<Stckingarea> queryStckingareaByPage(@Param("warehouse") String warehouse,
                                    @Param("id") String id,
                                    @Param("title") String title,
                                    @Param("row") int row,
                                    @Param("page") int page);

    int countStckingarea(@Param("warehouse") String warehouse,
                     @Param("id") String id,
                     @Param("title") String title);

}
