package online.goudan.aspect;

import online.goudan.annotation.DataValue;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @author lcl
 * @date 2023/8/11 17:58
 * @desc MyAdvisor
 */
public class MyAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    @Override
    public Pointcut getPointcut() {
        return new AnnotationMatchingPointcut(DataValue.class);
    }
}
