package online.goudan.service.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import online.goudan.domain.BaseObject;

/**
 * (User)实体类
 *
 * @author lcl
 * @since 2023-08-10 17:54:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseObject {
    private static final long serialVersionUID = 195971018904598502L;

    private Integer id;
    /**
     * 第三方用户ID
     */
    private String thirdAccountId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 登录方式: 0-微信登录，1-账号密码登录
     */
    private Integer loginType;
    /**
     * 是否删除
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;

}

