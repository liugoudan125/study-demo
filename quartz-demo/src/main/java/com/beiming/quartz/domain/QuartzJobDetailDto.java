package com.beiming.quartz.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
@ApiModel(value = "Quartz定时任务详情类")
public class QuartzJobDetailDto {
    @ApiModelProperty(value = "任务类路径")
    private String jobClazz;

    @ApiModelProperty(value = "任务类名")
    private String jobName;

    @ApiModelProperty(value = "任务组名，命名空间")
    private String jobGroup;

    @ApiModelProperty(value = "任务数据")
    private Map<String, Object> jobDataMap;

    @ApiModelProperty(value = "触发器列表")
    private List<QuartzTriggerDetailDto> triggerDetailDtoList;
}
