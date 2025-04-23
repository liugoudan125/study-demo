package online.goudan.controller;

import online.goudan.domain.User;
import online.goudan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lcl
 * @date 2023/8/11 14:26
 * @desc HealthController
 */
@RestController
public class HealthController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("health")
    public String health() {
        return "ok";
    }


    @GetMapping("getOne")
    public User getOne(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
