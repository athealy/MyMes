package com.jacksoft.dao;

import com.jacksoft.entity.wms.Shelves;
import com.jacksoft.entity.wms.Stckingarea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShelvesDao {

    int insShelves(@Param("list") List<Shelves> shelves);
    int delShelves(@Param("list")List<Shelves> shelves);
    int updShelves(@Param("filter") Shelves filter,@Param("replacement") Shelves replacement);

    List<Shelves> queryShelvesByPage(@Param("warehouse") String warehouse,
                                    @Param("id") String id,
                                    @Param("title") String title,
                                    @Param("row") int row,
                                    @Param("page") int page);

    int countShelves(@Param("warehouse") String product,
                     @Param("id") String department,
                     @Param("title") String sdate);

}
