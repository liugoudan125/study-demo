package com.beiming.demo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.beiming.demo.model.MessageBoard;
import org.apache.ibatis.annotations.Mapper;

/**
 * 留言板表(MessageBoard)表数据库访问层
 *
 * @author makejava
 * @since 2025-10-09 15:36:17
 */
@Mapper
public interface MessageBoardDao extends BaseMapper<MessageBoard> {

}

