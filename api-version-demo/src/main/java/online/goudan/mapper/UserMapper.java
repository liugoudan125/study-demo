package online.goudan.mapper;

import online.goudan.annotation.DataValue;
import online.goudan.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author lcl
 * @date 2023/8/11 17:55
 * @desc ${DESC} 
 */
@Mapper
@DataValue(value = "aaa")

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);
    @DataValue(value = "aaa")

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);
}