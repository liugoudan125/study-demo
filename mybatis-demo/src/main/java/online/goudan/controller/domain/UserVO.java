package online.goudan.controller.domain;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import online.goudan.domain.BaseObject;

/**
 * (User)实体类
 *
 * @author lcl
 * @since 2023-08-10 17:54:15
 */
@ApiModel(description = "User")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BaseObject {
    private static final long serialVersionUID = 934706766922368670L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "第三方用户ID")
    private String thirdAccountId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "登录方式: 0-微信登录，1-账号密码登录")
    private Integer loginType;
    @ApiModelProperty(value = "是否删除")
    private Integer deleted;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;

}

