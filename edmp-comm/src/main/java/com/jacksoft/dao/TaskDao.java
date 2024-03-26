package com.jacksoft.dao;

import com.jacksoft.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TaskDao {

    int insTask(@Param("list")List<Task> departments);
    int delTask(@Param("list")List<Task> departments);
    int updTask(@Param("filter") Task filter,@Param("replacement") Task replacement);

    List<Task> queryTaskByPage(@Param("workType") String workType,
                                 @Param("serial") String serial,
                                 @Param("department") String department,
                                 @Param("row") int row,
                                 @Param("page") int page);

    int countTask(@Param("workType") String workType,
                  @Param("serial") String serial,
                  @Param("department") String department);

    List<Task> quyTaskOnly(@Param("workType") String workType,
                           @Param("serial") String serial,
                           @Param("department") String department);

}
