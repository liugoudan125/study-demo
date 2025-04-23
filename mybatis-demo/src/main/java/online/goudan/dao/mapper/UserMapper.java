package online.goudan.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import online.goudan.dao.domain.UserDO;

/**
 * (User)表数据库访问层
 *
 * @author lcl
 * @since 2023-08-10 17:54:16
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserDO> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserDO> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserDO> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserDO> entities);

}

