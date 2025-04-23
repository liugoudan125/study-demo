package online.goudan.config;

import online.goudan.interceptor.DyQueryInterceptor;
import online.goudan.interceptor.DyUpdateInterceptor;
import online.goudan.interceptor.MyInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author goudan
 * @date 2023/7/28 10:17
 * @desc MybatisConfig
 */
@Configuration
public class MybatisConfig {
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

//    @PostConstruct
    public void init() {
        DyQueryInterceptor dyQueryInterceptor = new DyQueryInterceptor();
        DyUpdateInterceptor dyUpdateInterceptor = new DyUpdateInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(dyQueryInterceptor);
            sqlSessionFactory.getConfiguration().addInterceptor(dyUpdateInterceptor);
        }
    }
}
