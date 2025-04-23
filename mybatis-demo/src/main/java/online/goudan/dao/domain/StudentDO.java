package online.goudan.dao.domain;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import online.goudan.domain.BaseObject;

/**
 * (Student)实体类
 *
 * @author lcl
 * @since 2023-08-10 17:53:53
 */
@TableName("student")
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentDO extends BaseObject {
    private static final long serialVersionUID = 907310629553367372L;

    @TableField(value = "id")
    private Long id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "sex")
    private Integer sex;
    @TableField(value = "age")
    private Integer age;
    @TableField(value = "sign")
    private String sign;
    @TableField(value = "teacher_id")
    private Long teacherId;
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
    @TableField(value = "is_deleted")
    private Integer isDeleted;

}

