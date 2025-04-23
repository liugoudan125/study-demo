package online.goudan.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import online.goudan.dao.domain.UserDO;
import online.goudan.dao.mapper.StudentMapper;
import online.goudan.dao.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author goudan
 * @date 2023/7/28 9:44
 * @desc StudentMapperTest
 */
@SpringBootTest
public class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;


    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFind() {

//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");
//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");
//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");
//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");
//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");
//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");
//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");
//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");
//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");
//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");
//         studentMapper.selectByAgeGreaterThanOrderByAge(500, "ffff");

        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
        studentMapper.selectById(32L);
    }

    @Test
    public void testRateLimiter() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        RRateLimiter rateLimiter = redissonClient.getRateLimiter("SMS_RATE_LIMITER:GETAPP:1");
        //每三秒产生一个令牌
        rateLimiter.trySetRate(RateType.OVERALL, 1, 3, RateIntervalUnit.SECONDS);
        call(rateLimiter, dateTimeFormatter);
        call(rateLimiter, dateTimeFormatter);
        call(rateLimiter, dateTimeFormatter);
        call(rateLimiter, dateTimeFormatter);
        call(rateLimiter, dateTimeFormatter);
        Thread.sleep(3000L);
        call(rateLimiter, dateTimeFormatter);
    }

    private static void call(RRateLimiter rateLimiter, DateTimeFormatter dateTimeFormatter) {
        if (rateLimiter.tryAcquire()) {
            System.out.println("调用成功,时间：" + LocalDateTime.now().format(dateTimeFormatter));
        } else {
            System.out.println("调用失败,时间：" + LocalDateTime.now().format(dateTimeFormatter));
        }
    }


    @Test
    public void testUserFind() {
        UserDO user = userMapper.selectById(997);
        user.setUserName("aaaaaaaaaa");
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }


    @Test
    public void testPage() {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<UserDO>()
                .ge(UserDO::getCreateTime,LocalDateTime.of(2022, 6, 1, 0, 0, 0))
                        .orderByDesc(UserDO::getCreateTime);
        PageHelper.startPage(1,5);
        List<UserDO> userList = userMapper.selectList(queryWrapper);
        PageInfo<UserDO> pageInfo = PageInfo.of(userList);
        pageInfo.getList().forEach(System.out::println);
    }
}
