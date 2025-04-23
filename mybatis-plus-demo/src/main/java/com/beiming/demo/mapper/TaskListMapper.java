package com.beiming.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beiming.demo.domain.TaskList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * TaskListMapper
 */
@Mapper
public interface TaskListMapper extends BaseMapper<TaskList> {
    int batchInsert(@Param("list") List<TaskList> list);
}