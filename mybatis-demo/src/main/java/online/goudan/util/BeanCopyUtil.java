package online.goudan.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lcl
 * @date 2023/8/10 17:09
 * @desc BeanCopyUtil
 */
@UtilityClass
public class BeanCopyUtil {

    private static final Map<String, BeanCopier> beanCopierCache = new ConcurrentHashMap<>();

    public static <S, T> T copyProperties(S s, T t) {
        String cacheKey = s.getClass().getName() + "&" + t.getClass().getName();
        BeanCopier beanCopier = beanCopierCache.get(cacheKey);
        if (null == beanCopier) {
            synchronized (BeanCopyUtil.class) {
                beanCopier = beanCopierCache.get(cacheKey);
                if (null == beanCopier) {
                    beanCopier = BeanCopier.create(s.getClass(), t.getClass(), false);
                    beanCopierCache.put(cacheKey, beanCopier);
                }
            }
        }
        beanCopier.copy(s, t, null);
        return t;
    }

    public static <S, T> T copyProperties(S s, Class<T> tClass) throws Exception {
        return copyProperties(s, tClass.newInstance());
    }

    @SneakyThrows
    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> tClass) {
        if (null != sourceList) {
            List<T> list = new ArrayList<>(sourceList.size());
            for (S s : sourceList) {
                list.add(copyProperties(s, tClass.newInstance()));
            }
            return list;
        } else {
            return null;
        }
    }

}
