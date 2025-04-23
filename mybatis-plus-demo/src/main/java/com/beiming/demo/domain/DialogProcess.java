package com.beiming.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DialogProcess的实体类
 */
@TableName("dialog_process")
@Getter
@Setter
public class DialogProcess {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户ID
     */
    @TableField(value = "customer_id")
    private Long customerId;
    /**
     * 流程名称
     */
    @TableField(value = "name")
    private String name;
    /**
     * 流程内容(json)
     */
    @TableField(value = "content")
    private String content;
    /**
     * 审核状态 10 待审核 20审核通过 30 审核不通过 40 重新提交待审核
     */
    @TableField(value = "audit_status")
    private Integer auditStatus;
    /**
     * 是否可用(0 不可用,1 可用)
     */
    @TableField(value = "is_available")
    private Integer isAvailable;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

}

