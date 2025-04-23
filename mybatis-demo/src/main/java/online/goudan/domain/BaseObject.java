package online.goudan.domain;

import org.springframework.cglib.beans.BeanCopier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lcl
 * @date 2023/8/10 16:41
 * @desc BaseObject
 */
public class BaseObject implements Serializable {

    /**
     * BeanCopier缓存
     */
    private static Map<String, BeanCopier> beanCopierCacheMap = new HashMap<>();

    /**
     * 将source对象的属性拷贝到target对象中去
     *
     * @param source source对象
     * @param target target对象
     */
    private void copyProperties(Object source, Object target) {
        String cacheKey = source.getClass().toString() +
                target.getClass().toString();

        BeanCopier beanCopier = beanCopierCacheMap.get(cacheKey);
        if (beanCopier == null) {
            synchronized (beanCopierCacheMap) {
                beanCopier = beanCopierCacheMap.get(cacheKey);
                if (beanCopier == null) {
                    beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
                    beanCopierCacheMap.put(cacheKey, beanCopier);
                }
            }
        }
        beanCopier.copy(source, target, null);
    }

    /**
     * 浅度克隆
     *
     * @param clazz
     * @return
     * @throws Exception
     */
    public <T> T clone(Class<T> clazz) throws Exception {
        T target = null;
        target = clazz.newInstance();
        copyProperties(this, target);
        return target;
    }

    /**
     * 浅度克隆
     *
     * @param target
     * @return
     */
    public <T> T clone(T target) {
        copyProperties(this, target);
        return target;
    }


    protected <T, E> List<E> copyList(List<T> sourceList, Class<E> eClass) throws Exception {
        List<E> eList = new ArrayList<>(sourceList.size());
        for (T t : sourceList) {
            E e = eClass.newInstance();
            copyProperties(t, e);
            eList.add(e);
        }
        return eList;
    }
}
