package com.jacksoft.dao;

import com.jacksoft.entity.wms.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsDao {

    int insGoods(@Param("list") List<Goods> goods);
    int delGoods(@Param("list") List<Goods> goods);
    int updGoods(@Param("filter") Goods filter,@Param("replacement") Goods replacement);

    List<Goods> queryIcdByPage(@Param("warehouse") String warehouse,
                                    @Param("id") String id,
                                    @Param("title") String title,
                                    @Param("row") int row,
                                    @Param("page") int page);

    int countGoods(@Param("warehouse") String product,
                     @Param("id") String department,
                     @Param("title") String sdate);

}
