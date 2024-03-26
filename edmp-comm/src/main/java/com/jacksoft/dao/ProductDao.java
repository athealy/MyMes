package com.jacksoft.dao;

import com.jacksoft.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductDao {

    int insProduct(@Param("list")List<Product> products);
    int delProduct(@Param("list")List<Product> products);
    int updProduct(@Param("filter") Product filter,@Param("replacement") Product replacement);

    List<Product> queryProductByPage(@Param("id") String id,
                                     @Param("department") String department,
                                     @Param("state") String state,
                                     @Param("batch") String batch,
                                     @Param("row") int row,
                                     @Param("page") int page);

    int countProduct(@Param("id") String id,
                     @Param("department") String department,
                     @Param("state") String state,
                     @Param("batch") String batch);

    List<Product> quyProductOnly(@Param("id") String id,
                                 @Param("department") String department,
                                 @Param("state") String state,
                                 @Param("batch") String batch);

    List<Product> quyProdSimple();

}
