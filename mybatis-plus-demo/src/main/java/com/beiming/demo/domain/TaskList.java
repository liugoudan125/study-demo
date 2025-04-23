package com.beiming.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TaskList
 */

/**
 * 任务名单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "task_list")
public class TaskList {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务ID
     */
    @TableField(value = "task_id")
    private Long taskId;

    /**
     * 被呼叫叫人名称
     */
    @TableField(value = "called_name")
    private String calledName;

    /**
     * 被呼叫叫人电话
     */
    @TableField(value = "called_phone")
    private String calledPhone;

    /**
     * 外部数据
     */
    @TableField(value = "external_data")
    private Object externalData;

    /**
     * 是否执行中(0:否,1:是)默认0
     */
    @TableField(value = "is_executing")
    private Integer isExecuting;

    /**
     * 执行时间
     */
    @TableField(value = "execution_time")
    private LocalDateTime executionTime;

    /**
     * 状态 10 未开始 20 就绪 21 通话中  30 通话完成
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 重试次数
     */
    @TableField(value = "retry_num")
    private Integer retryNum;

    /**
     * 就绪时间
     */
    @TableField(value = "ready_time")
    private LocalDateTime readyTime;

    /**
     * 通话开始时间
     */
    @TableField(value = "calling_time")
    private LocalDateTime callingTime;

    /**
     * 是否可用(1可用,0不可用) 由黑名单/敏感词决定
     */
    @TableField(value = "`enable`")
    private Integer enable;

    /**
     * 不可用的原因
     */
    @TableField(value = "disable_reason")
    private String disableReason;

    /**
     * 通话状态
     */
    @TableField(value = "dialog_status")
    private Integer dialogStatus;

    /**
     * 通话任务Id(最后一次通话任务Id)
     */
    @TableField(value = "task_dialog_id")
    private Long taskDialogId;

    /**
     * 最后一次更新通话状态时间
     */
    @TableField(value = "last_update_dialog_time")
    private LocalDateTime lastUpdateDialogTime;

    /**
     * 是否可见(1可见 0 不可见)
     */
    @TableField(value = "is_visible")
    private Integer isVisible;

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