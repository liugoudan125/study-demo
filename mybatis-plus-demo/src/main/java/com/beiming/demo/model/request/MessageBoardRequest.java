package com.beiming.demo.model.request;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * 留言板表(MessageBoard)表实体类
 *
 * @author makejava
 * @since 2025-10-09 15:36:17
 */
@Data
public class MessageBoardRequest implements Serializable {

    /**
     * 主键ID
     **/
    private Long id;
    /**
     * 留言板名称
     **/
    private String name;
    /**
     * 留言板描述
     **/
    private String description;
    /**
     * 状态：active-启用，inactive-禁用
     **/
    private String status;
}

