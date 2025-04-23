package online.goudan.dao;

import java.util.List;

import online.goudan.service.domain.UserDTO;

/**
 * (User)表数据库访问层
 *
 * @author lcl
 * @since 2023-08-10 17:54:16
 */
public interface UserDAO {


    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param userDTOList List<UserDTO>
     * @return 影响行数
     */
    int insertBatch(List<UserDTO> userDTOList);


    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param userDTOList List<UserDTO>
     * @return 影响行数
     */
    int insertOrUpdateBatch(List<UserDTO> userDTOList);
}
