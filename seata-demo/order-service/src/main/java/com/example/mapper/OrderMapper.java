package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.OrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author ${AUTHOR}
 * @date 2023/4/7 17:09
 * @desc ${DESC}  */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
}