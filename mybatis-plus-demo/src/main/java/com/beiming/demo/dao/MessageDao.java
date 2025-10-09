package com.beiming.demo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.beiming.demo.model.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * 留言表(Message)表数据库访问层
 *
 * @author makejava
 * @since 2025-10-09 15:36:16
 */
@Mapper
public interface MessageDao extends BaseMapper<Message> {

}

