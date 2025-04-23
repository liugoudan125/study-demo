package com.beiming.shardingdemo.mapper;

import com.beiming.shardingdemo.domain.Student;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * StudentMapper
 */
@Mapper
public interface StudentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    int batchInsert(@Param("list") List<Student> list);

    List<Student> selectByName(@Param("name")String name);


    List<Student> selectByAge(@Param("age")Integer age);


    List<Student> selectList();

    List<Map<String, Objects>> selectGroup();
}