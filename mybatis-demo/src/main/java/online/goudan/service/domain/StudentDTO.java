package online.goudan.service.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import online.goudan.domain.BaseObject;

/**
 * (Student)实体类
 *
 * @author lcl
 * @since 2023-08-10 17:53:53
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentDTO extends BaseObject {
    private static final long serialVersionUID = 131845691336680809L;

    private Long id;
    private String name;
    private Integer sex;
    private Integer age;
    private String sign;
    private Long teacherId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;

}

