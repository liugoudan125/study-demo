package com.beiming.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beiming.demo.domain.DialogIntention;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * DialogIntentionMapper
 */
@Mapper
public interface DialogIntentionMapper extends BaseMapper<DialogIntention> {
    int batchInsert(@Param("list") List<DialogIntention> list);
}