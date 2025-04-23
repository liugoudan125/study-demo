import com.beiming.clickhouse.Application;
import com.beiming.clickhouse.repository.ClickHouseRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TestClickhouse
 */
@SpringBootTest(classes = Application.class)
public class TestClickhouse {

    @Resource
    private ClickHouseRepository clickHouseRepository;

    private static final Logger log = LoggerFactory.getLogger(TestClickhouse.class);

    @Test
    public void testCount() {
        List<Map<String, Object>> maps = clickHouseRepository.queryData("select URL, count(1) as c from hits_v1 group by URL limit 10");
        maps.forEach(System.out::println);
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Shanghai"));
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
