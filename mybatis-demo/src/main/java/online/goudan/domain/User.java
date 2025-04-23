package online.goudan.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ${AUTHOR}
 * @date 2023/8/3 16:55
 * @desc ${DESC} 
 */

/**
 * 用户登录表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 主键ID
     */
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