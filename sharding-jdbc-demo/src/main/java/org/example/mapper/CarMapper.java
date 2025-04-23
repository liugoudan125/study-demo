package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.domain.CarDO;

import java.io.Serializable;
import java.util.List;

/**
 * @author ${AUTHOR}
 * @date 2023/3/21 0:06
 * @desc ${DESC}
 */
@Mapper
public interface CarMapper extends BaseMapper<CarDO> {

    List<CarDO> selectCarByTestId();

}