package com.jacksoft.dao;

import com.jacksoft.entity.Product;
import com.jacksoft.entity.hr.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    int insUser(@Param("list")List<User> users);
    int delUser(@Param("list")List<User> users);
    int updUser(@Param("filter") User filter,@Param("replacement") User replacement);

    List<User> queryUserByPage(@Param("id") String id,
                               @Param("username") String username,
                               @Param("department") String department,
                               @Param("state") String state,
                               @Param("company") String company,
                               @Param("row") int row,
                               @Param("page") int page);

    int countUser(@Param("id") String id,
                  @Param("username") String username,
                  @Param("department") String department,
                  @Param("state") String state,
                  @Param("company") String company);

    List<User> quyUserNopagetion(@Param("id") String id,
                           @Param("username") String username,
                           @Param("department") String department,
                           @Param("state") String state,
                           @Param("company") String company,
                           @Param("role") String role);

    int chkUserinfo(@Param("id") String id,
                  @Param("passwd") String passwd);

    List<User> quyCompany();

}
