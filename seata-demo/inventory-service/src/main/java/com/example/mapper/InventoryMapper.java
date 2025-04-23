package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.InventoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author ${AUTHOR}
 * @date 2023/4/7 18:26
 * @desc ${DESC}  */
@Mapper
public interface InventoryMapper extends BaseMapper<InventoryDO> {
}