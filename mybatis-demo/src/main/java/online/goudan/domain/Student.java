package online.goudan.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ${AUTHOR}
 * @date 2023/7/28 9:43
 * @desc ${DESC}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Long id;

    private String name;

    private Byte sex;

    private Integer age;

    private String sign;

    private Long teacherId;

    private Date createTime;

    private Date updateTime;

    private Integer isDeleted;
}