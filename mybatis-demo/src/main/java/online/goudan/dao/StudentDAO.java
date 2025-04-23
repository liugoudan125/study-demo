package online.goudan.dao;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import online.goudan.dao.domain.StudentDO;
import online.goudan.service.domain.StudentDTO;

/**
 * (Student)表数据库访问层
 *
 * @author lcl
 * @since 2023-08-10 17:53:53
 */
public interface StudentDAO {


    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param studentDTOList List<StudentDTO>
     * @return 影响行数
     */
    int insertBatch(List<StudentDTO> studentDTOList);


    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param studentDTOList List<StudentDTO>
     * @return 影响行数
     */
    int insertOrUpdateBatch(List<StudentDTO> studentDTOList);
}
