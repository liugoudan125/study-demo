package online.goudan.dao.domain;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import online.goudan.domain.BaseObject;

/**
 * (User)实体类
 *
 * @author lcl
 * @since 2023-08-10 17:54:15
 */
@TableName("user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseObject {
    private static final long serialVersionUID = -35623466818132681L;

    @TableField(value = "id")
    private Integer id;
    /**
     * 第三方用户ID
     */
    @TableField(value = "third_account_id")
    private String thirdAccountId;
    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;
    /**
     * 登录方式: 0-微信登录，1-账号密码登录
     */
    @TableField(value = "login_type")
    private Integer loginType;
    /**
     * 是否删除
     */
    @TableField(value = "deleted")
    private Integer deleted;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

}

