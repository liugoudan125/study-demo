//package com.beiming.shardingdemo.config;
//
//import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//import javax.sql.DataSource;
//
///**
// * ShardingConfig
// */
//@Configuration
//public class ShardingConfig {
//
//    @Bean
//    public DataSource dataSource() throws Exception {
//        ClassPathResource resource = new ClassPathResource("sharding-config.yaml");
//        DataSource dataSource = YamlShardingSphereDataSourceFactory.createDataSource(resource.getFile());
//        return dataSource;
//    }
//}
