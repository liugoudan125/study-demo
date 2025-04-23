package online.goudan.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.pro.packaged.D;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author goudan
 * @date 2023/8/3 17:30
 * @desc DataSource
 */
@Configuration
@EnableConfigurationProperties(DSProperties.class)
public class DataSourceConfig {

    @Bean
    public DataSource targetDataSources(DSProperties dsProperties) {
        Map<Object, Object> map = new HashMap<>() {
            {
                DataSource master = dsProperties.getDatasource().get("master").initializeDataSourceBuilder().build();
                HikariDataSource hikariDataSource = (HikariDataSource) master;
                hikariDataSource.setReadOnly(false);
                put("master", master);
                put("slave", dsProperties.getDatasource().get("slave").initializeDataSourceBuilder().build());
            }
        };
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(map);
//        dynamicDataSource.setDefaultTargetDataSource(map.get("master"));
        return dynamicDataSource;
    }


}
