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
public class Teacher {
    private Long id;

    private String name;

    private Boolean sex;

    private Date createTime;

    private Date updateTime;
}