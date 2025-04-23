package online.goudan.dao.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import online.goudan.util.BeanCopyUtil;
import org.springframework.stereotype.Repository;
import online.goudan.dao.domain.StudentDO;
import online.goudan.service.domain.StudentDTO;
import online.goudan.dao.mapper.StudentMapper;
import online.goudan.dao.StudentDAO;

/**
 * (Student)表数据库访问层
 *
 * @author lcl
 * @since 2023-08-10 17:53:53
 */
@RequiredArgsConstructor
@Repository
public class StudentDAOImpl implements StudentDAO {

    private final StudentMapper mapper;

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param studentDTOList List<StudentDTO>
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<StudentDTO> studentDTOList) {
        if (null == studentDTOList || studentDTOList.isEmpty()) {
            return 0;
        }
        List<StudentDO> studentDOList = BeanCopyUtil.copyList(studentDTOList, StudentDO.class);
        return mapper.insertBatch(studentDOList);
    }


    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param studentDTOList List<StudentDTO>
     * @return 影响行数
     */
    @Override
    public int insertOrUpdateBatch(List<StudentDTO> studentDTOList) {
        if (null == studentDTOList || studentDTOList.isEmpty()) {
            return 0;
        }
        List<StudentDO> studentDOList = BeanCopyUtil.copyList(studentDTOList, StudentDO.class);
        return mapper.insertOrUpdateBatch(studentDOList);
    }
}
