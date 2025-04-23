import com.beiming.quartz.QuartzApplication;
import com.beiming.quartz.domain.Person;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * JdbcTest-2024/4/1-9:50
 */
@SpringBootTest(classes = QuartzApplication.class)
public class JdbcTest {

    @Resource
    private JdbcTemplate jdbcTemplate;


    @Test
    public void testQuery() {
        System.out.println(jdbcTemplate.queryForList("select * from person"));

    }
}
