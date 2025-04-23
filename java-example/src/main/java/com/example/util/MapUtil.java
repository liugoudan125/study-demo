package com.example.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @date 2023/6/8 11:45
 * @desc MapUtil
 */
public class MapUtil {
    public static Builder createBuilder(){
        return new Builder();
    }

    public static class Builder {
        private Map<Object, Object> map = new HashMap<>();

        public Builder add(@NotNull Object key, Object value) {
            map.put(key, value);
            return this;
        }

        public <K, V> Map<K, V> builder() {
            return (Map<K, V>) map;
        }
    }

}
