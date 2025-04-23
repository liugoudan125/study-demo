package online.goudan.listener;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author
 * @date 2023/5/16 11:56
 * @desc DataSourceListener
 */
@Component
@Slf4j
public class DataSourceListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        HikariDataSource dataSource = applicationContext.getBean(HikariDataSource.class);
        log.info("DataSourceListener.onApplicationEvent:  {}", dataSource.getJdbcUrl());
    }
}
