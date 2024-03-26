package com.jacksoft.dao;

import com.jacksoft.entity.Capacity;
import com.jacksoft.entity.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MaterialDao {

    int insMaterial(@Param("list") List<Material> materials);
    int delMaterial(@Param("list") List<Material> materials);
    int updMaterial(@Param("filter") Material filter, @Param("replacement") Material replacement);

    List<Material> queryMaterialByPage(@Param("product") String product,
                                       @Param("row") int row,
                                       @Param("page") int page);

    int countMaterial(@Param("product") String product);

    List<Material> quyEqualNum(@Param("list") List<Material> materials);
}
