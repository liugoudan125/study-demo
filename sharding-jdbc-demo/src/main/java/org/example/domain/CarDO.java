package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ${AUTHOR}
 * @date 2023/3/21 0:06
 * @desc ${DESC}  */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "car")
public class CarDO {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    @TableField(value = "car")
    private String car;

    @TableField(value = "test_id")
    private Long testId;

    private Test test;
}