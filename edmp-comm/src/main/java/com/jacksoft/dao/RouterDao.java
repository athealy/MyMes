package com.jacksoft.dao;

import com.jacksoft.entity.dc.RouterData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RouterDao {

    public List<RouterData> queryAll();

}
