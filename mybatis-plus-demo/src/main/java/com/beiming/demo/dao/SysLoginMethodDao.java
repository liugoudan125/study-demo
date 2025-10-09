package com.beiming.demo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.beiming.demo.model.SysLoginMethod;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录方式表(SysLoginMethod)表数据库访问层
 *
 * @author makejava
 * @since 2025-10-09 15:36:18
 */
@Mapper
public interface SysLoginMethodDao extends BaseMapper<SysLoginMethod> {

}

