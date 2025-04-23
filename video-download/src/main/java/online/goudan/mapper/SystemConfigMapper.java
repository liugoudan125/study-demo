package online.goudan.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import online.goudan.domain.SystemConfigDO;

/**
 * @author ${AUTHOR}
 * @date 2023/4/5 0:40
 * @desc ${DESC}
 */
@Mapper
public interface SystemConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemConfigDO record);

    int insertSelective(SystemConfigDO record);

    SystemConfigDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemConfigDO record);

    int updateByPrimaryKey(SystemConfigDO record);

    SystemConfigDO selectByPropertyName(@Param("propertyName") String propertyName);


}