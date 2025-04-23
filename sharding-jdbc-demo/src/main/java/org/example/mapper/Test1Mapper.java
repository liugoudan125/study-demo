package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.domain.CarDO;
import org.example.domain.Test;

import java.io.Serializable;

/**
 * @author ${AUTHOR}
 * @date 2023/3/20 23:02
 * @desc ${DESC}
 */
@Mapper
public interface Test1Mapper extends BaseMapper<Test> {
    Test selectById(Serializable id);

}