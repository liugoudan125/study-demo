package online.goudan.controller.domain;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import online.goudan.domain.BaseObject;

/**
 * (Student)实体类
 *
 * @author lcl
 * @since 2023-08-10 17:53:53
 */
@ApiModel(description = "Student")
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentVO extends BaseObject {
    private static final long serialVersionUID = -59259398258024932L;

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "sex")
    private Integer sex;
    @ApiModelProperty(value = "age")
    private Integer age;
    @ApiModelProperty(value = "sign")
    private String sign;
    @ApiModelProperty(value = "teacherId")
    private Long teacherId;
    @ApiModelProperty(value = "createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "updateTime")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "isDeleted")
    private Integer isDeleted;

}

