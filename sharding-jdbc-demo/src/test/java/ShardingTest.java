import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.Application;
import org.example.domain.CarDO;
import org.example.mapper.CarMapper;
import org.example.mapper.Test1Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2023/3/20 22:57
 * @desc ShardingTest
 */
@SpringBootTest(classes = Application.class)
public class ShardingTest {
    @Autowired
    private Test1Mapper test1Mapper;
    @Autowired
    private CarMapper carMapper;

    @Test
    public void testInsert() {
        for (int i = 0; i < 100; i++) {
            test1Mapper.insert(org.example.domain.Test.builder()
                    .name("test" + i)
                    .age(12)
                    .brithday(new Date())
                    .build());
        }
    }

    @Test
    public void testInsertCar() {
        List<org.example.domain.Test> tests = test1Mapper.selectList(null);
        for (int i = 0; i < 10; i++) {
            carMapper.insert(
                    CarDO.builder()
                            .id(i + 1)
                            .car("car" + i)
                            .testId(tests.get(i).getId()).build()
            );

        }
        tests.stream().forEach(System.out::println);
    }

    @Test
    public void testSelectTest() {
        for (int i = 0; i < 2; i++) {
            List<org.example.domain.Test> tests = test1Mapper.selectList(
                    new LambdaQueryWrapper<org.example.domain.Test>()
                            .orderBy(true, true, org.example.domain.Test::getName)
            );
        }
        long s = System.currentTimeMillis();
        List<org.example.domain.Test> tests = test1Mapper.selectList(
                new LambdaQueryWrapper<org.example.domain.Test>()
                        .orderBy(true, true, org.example.domain.Test::getName)
        );
        long e = System.currentTimeMillis();
        tests.stream().forEach(System.out::println);

        System.out.println(e - s);
    }

    @Test
    public void testFindOne() {
        org.example.domain.Test test = test1Mapper.selectById(1637847935638519810L);
        org.example.domain.Test test1 = test1Mapper.selectById(1637847937236549634L);
        System.out.println(test);
        Assert.isTrue(test.equals(test1), "两者不能等同");
    }

    @Test
    public void testMutilTableQuery() {
        List<CarDO> cars = carMapper.selectCarByTestId();
        cars.stream().forEach(System.out::println);
    }
}
