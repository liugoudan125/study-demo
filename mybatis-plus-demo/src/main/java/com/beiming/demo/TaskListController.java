package com.beiming.demo;

import com.beiming.demo.domain.IdGenerator;
import com.beiming.demo.domain.TaskList;
import com.beiming.demo.mapper.TaskListMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.util.List;

/**
 * TaskListController
 */
@RestController
@RequestMapping("taskList")
public class TaskListController {

    private static final Logger log = LoggerFactory.getLogger(TaskListController.class);
    @Resource
    private TaskListMapper taskListMapper;

    @PostMapping("batchInsert")
    public Boolean batchInsert(@RequestBody List<TaskList> list) {
        for (TaskList taskList : list) {
            taskList.setId(IdGenerator.id());
            taskList.setIsExecuting(1);
            taskList.setStatus(10);
            taskList.setRetryNum(0);
            taskList.setEnable(1);
            taskList.setIsVisible(1);
        }
        long millis = Clock.systemUTC().millis();
        taskListMapper.batchInsert(list);
        log.info("本次用时: {}", Clock.systemUTC().millis() - millis);
        return true;
    }
}
