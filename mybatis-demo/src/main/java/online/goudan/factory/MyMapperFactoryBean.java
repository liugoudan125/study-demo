package online.goudan.factory;

import lombok.extern.slf4j.Slf4j;
import online.goudan.annotation.DataIsolation;
import online.goudan.util.CGLibUtil;
import online.goudan.util.ThreadLocalUtil;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @date 2023/8/4 14:12
 * @desc MyMapperFactoryBean
 */
@Slf4j
public class MyMapperFactoryBean<T> extends MapperFactoryBean {
    private Class<T> mapperInterface;
    private MapperFactoryBean<T> mapperFactoryBean;

    public MyMapperFactoryBean() {
        super();
    }

    public MyMapperFactoryBean(MapperFactoryBean<T> mapperFactoryBean) {
        this.mapperFactoryBean = mapperFactoryBean;
        this.mapperInterface = mapperFactoryBean.getMapperInterface();
    }


    @Override
    public T getObject() throws Exception {
        T object = mapperFactoryBean.getObject();
        return CGLibUtil.createProxyObject(object, mapperInterface, new CGLibUtil.ProxyHandler() {
            private Map<Method, DataIsolation[]> map = new ConcurrentHashMap<>();

            @Override
            public void before(Method method, Object[] objects) {
                DataIsolation[] dataIsolationArr = map.get(method);
                if (dataIsolationArr == null) {
                    synchronized (map) {
                        if (dataIsolationArr == null) {
                            DataIsolation dataIsolation = AnnotatedElementUtils.findMergedAnnotation(method, DataIsolation.class);
                            dataIsolationArr = new DataIsolation[1];
                            dataIsolationArr[0] = dataIsolation;
                            map.put(method, dataIsolationArr);
                        }
                    }
                }
                if (null != dataIsolationArr[0]) {
                    ThreadLocalUtil.set("dataIsolation", dataIsolationArr[0]);
                }
            }

            @Override
            public Object after(Object returnValue) {
                ThreadLocalUtil.remove("dataIsolation");
                return returnValue;
            }
        });
    }

    public MyMapperFactoryBean(Class mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
    }

    @Override
    public Class getObjectType() {
        return mapperFactoryBean.getObjectType();
    }

    @Override
    public boolean isSingleton() {
        return mapperFactoryBean.isSingleton();
    }

    @Override
    public void setMapperInterface(Class mapperInterface) {
        mapperFactoryBean.setMapperInterface(mapperInterface);
    }

    @Override
    public Class getMapperInterface() {
        return mapperFactoryBean.getMapperInterface();
    }

    @Override
    public void setAddToConfig(boolean addToConfig) {
        mapperFactoryBean.setAddToConfig(addToConfig);
    }

    @Override
    public boolean isAddToConfig() {
        return mapperFactoryBean.isAddToConfig();
    }
}
