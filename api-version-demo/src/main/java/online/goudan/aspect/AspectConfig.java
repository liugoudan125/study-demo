package online.goudan.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lcl
 * @date 2023/8/11 18:06
 * @desc AspectConfig
 */
@Configuration
public class AspectConfig {

    @Bean
    public MyAdvisor myAdvisor() {
        MyAdvisor myAdvisor = new MyAdvisor();
        myAdvisor.setAdvice(new MyAdvice());
        return myAdvisor;
    }
}
