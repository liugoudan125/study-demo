package com.beiming.es.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.beiming.es.model.CallLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * CallLogMapper
 */
@Mapper
public interface CallLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CallLog record);

    int insertSelective(CallLog record);

    CallLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CallLog record);

    int updateByPrimaryKey(CallLog record);

     List<CallLog> selectAllByIdGreaterThan(@Param("minId")Long minId);


}