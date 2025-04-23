package online.goudan.factory;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2023/7/28 17:45
 * @desc SpringCacheHelper
 */
@Component
@Slf4j
public class SpringCacheHelper implements BeanPostProcessor {


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Object o = bean;
        if (bean instanceof MapperFactoryBean) {
            o = new MyMapperFactoryBean((MapperFactoryBean) bean);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(o, beanName);
    }
}
