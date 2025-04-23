//package com.beiming.demo.config;
//
//import com.mysql.cj.jdbc.Driver;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.annotation.MapperScans;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * MysqlDataSourceConfig
// */
//@Configuration
//@MapperScans(value = {@MapperScan(value = "com.beiming.demo.mysql.mapper", sqlSessionFactoryRef = "mysqlSqlSessionFactory")})
//public class MysqlDataSourceConfig {
//
//    @Bean(name = "mysqlDataSource")
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName(Driver.class.getName())
//                .url("jdbc:mysql://10.1.1.20:3306/test")
//                .username("root")
//                .password("mZG7BBRFp9QsrT8v3XsWyU6Uej95ofB5")
//                .build();
//    }
//
//    @Bean(name = "mysqlSqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        //mapper.xml配置需要写在code中, application.yml中无效.
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:mapping/mysql/*.xml"));
//        factoryBean.setTypeAliasesPackage("com.beiming.domain.mysql.*");
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
//    @Bean(name = "mysqlSqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
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
//    @Bean(name = "mysqlTransactionManager")
//    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}
