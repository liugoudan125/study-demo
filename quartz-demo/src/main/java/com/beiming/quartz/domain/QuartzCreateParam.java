package com.beiming.quartz.domain;

import lombok.Data;

import java.util.Map;


@Data
public class QuartzCreateParam extends BaseParam {
//    @NotBlank(message = "任务类不能为空")
//    @ApiModelProperty(value = "任务类路径", required = true)
    private String jobClazz;

//    @NotBlank(message = "任务类名不能为空")
//    @ApiModelProperty(value = "任务类名", required = true)
    private String jobName;

    /**
     * 组名+任务类key组成唯一标识，所以如果这个参数为空，那么默认以任务类key作为组名
     */
//    @ApiModelProperty(value = "任务组名，命名空间")
    private String jobGroup;

//    @ApiModelProperty(value = "任务数据")
    private Map<String, Object> jobDataMap;

//    @ApiModelProperty(value = "cron表达式")
    private String cron;

//    @ApiModelProperty(value = "描述")
    private String description;
}
