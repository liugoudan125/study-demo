//package com.beiming.demo.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.annotation.MapperScans;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * MysqlDataSourceConfig
// */
//@Configuration
//@MapperScans(value = {@MapperScan(value = "com.beiming.demo.mapper", sqlSessionFactoryRef = "pgsqlSqlSessionFactory")})
//public class PostgresDataSourceConfig {
//
//    @Bean(name = "pgsqlDataSource")
//    @Primary
//    public DataSource pgsqlDataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("org.postgresql.Driver")
//                .url("jdbc:pgsql://10.1.1.20:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&autoReconnect=true&allowMultiQueries=true&rewriteBatchedStatements=true")
//                .username("postgres")
//                .password("goudan125")
//                .build();
//    }
//
//    @Bean(name = "pgsqlSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("pgsqlDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        //mapper.xml配置需要写在code中, application.yml中无效.
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:mapping/*.xml"));
//        factoryBean.setTypeAliasesPackage("com.beiming.domain.*");
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        //配置打印Sql语句
////        configuration.setLogImpl(StdOutImpl.class);
//        //小驼峰和下划线自动映射
//        configuration.setMapUnderscoreToCamelCase(true);
//        factoryBean.setConfiguration(configuration);
//        factoryBean.setDataSource(dataSource);
//        return factoryBean.getObject();
//    }
//
//    @Bean(name = "pgsqlSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("pgsqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//
//    /**
//     * 配置事务管理
//     *
//     * @param dataSource
//     * @return
//     */
//    @Bean(name = "pgsqlTransactionManager")
//    @Primary
//    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("pgsqlDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}
