package com.beiming.quartz.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@ApiModel(value = "Quartz任务详情请求参数")
public class QuartzDetailParam extends BaseParam {
    @NotBlank(message = "任务类名不能为空")
    @ApiModelProperty(value = "任务类名", required = true)
    private String jobName;

    @ApiModelProperty(value = "任务组名，命名空间")
    private String jobGroup;
}
