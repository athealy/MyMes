package com.jacksoft.dao;

import com.jacksoft.entity.Manufacture;
import com.jacksoft.entity.Product;
import com.jacksoft.entity.ds.ProductAccData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MpcDao {

    int insMpc(@Param("list")List<Manufacture> manufactures);
    int delMpc(@Param("list")List<Manufacture> manufactures);
    int updMpc(@Param("filter") Manufacture filter,@Param("replacement") Manufacture replacement);

    List<Manufacture> queryMpcByPage(@Param("id") String id,
                                     @Param("department") String department,
                                     @Param("product") String product,
                                     @Param("classes") String classes,
                                     @Param("timezone") String timezone,
                                     @Param("line") String line,
                                     @Param("mfg") String mfg,
                                     @Param("batch") String batch,
                                     @Param("row") int row,
                                     @Param("page") int page);

    int countMpc(@Param("id") String id,
                 @Param("department") String department,
                 @Param("product") String product,
                 @Param("classes") String classes,
                 @Param("timezone") String timezone,
                 @Param("line") String line,
                 @Param("mfg") String mfg,
                 @Param("batch") String batch);

    Manufacture quyMakingProduct( @Param("line") String line,
                                  @Param("mfg") String mfg,
                                  @Param("classes") String classes,
                                  @Param("timezone") String timezone );

    ProductAccData quyProdAcc( @Param("line") String line,
                               @Param("department") String department,
                               @Param("product") String product,
                               @Param("mfg") String mfg,
                               @Param("batch") String batch);

    List<Manufacture> quyCapaByLine(@Param("line") String line,
                                    @Param("department") String department,
                                    @Param("classes") String classes,
                                    @Param("mfg") String mfg );

    List<Manufacture> quyProduction(@Param("line") String line,
                                    @Param("department") String department,
                                    @Param("classes") String classes,
                                    @Param("mfg") String mfg);

    List<Manufacture> quyProdByLineMfg(@Param("line") String line,
                                       @Param("mfg") String mfg);

    List<Manufacture> quyCapaOfDate(@Param("mfg") String mfg);

    List<Manufacture> quyAllDayPlan(@Param("line") String line,
                                    @Param("department") String department,
                                    @Param("mfg") String mfg);

}
