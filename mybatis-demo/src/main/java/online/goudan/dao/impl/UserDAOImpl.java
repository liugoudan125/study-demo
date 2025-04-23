package online.goudan.dao.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import online.goudan.util.BeanCopyUtil;
import org.springframework.stereotype.Repository;
import online.goudan.dao.domain.UserDO;
import online.goudan.service.domain.UserDTO;
import online.goudan.dao.mapper.UserMapper;
import online.goudan.dao.UserDAO;

/**
 * (User)表数据库访问层
 *
 * @author lcl
 * @since 2023-08-10 17:54:16
 */
@RequiredArgsConstructor
@Repository
public class UserDAOImpl implements UserDAO {

    private final UserMapper mapper;

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param userDTOList List<UserDTO>
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<UserDTO> userDTOList) {
        if (null == userDTOList || userDTOList.isEmpty()) {
            return 0;
        }
        List<UserDO> userDOList = BeanCopyUtil.copyList(userDTOList, UserDO.class);
        return mapper.insertBatch(userDOList);
    }


    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param userDTOList List<UserDTO>
     * @return 影响行数
     */
    @Override
    public int insertOrUpdateBatch(List<UserDTO> userDTOList) {
        if (null == userDTOList || userDTOList.isEmpty()) {
            return 0;
        }
        List<UserDO> userDOList = BeanCopyUtil.copyList(userDTOList, UserDO.class);
        return mapper.insertOrUpdateBatch(userDOList);
    }
}
