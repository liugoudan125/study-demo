package online.goudan.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "spring.dynamic")
public class DSProperties {
    private Map<String, DataSourceProperties> datasource;
}
