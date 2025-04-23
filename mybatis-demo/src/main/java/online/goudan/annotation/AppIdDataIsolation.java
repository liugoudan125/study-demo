package online.goudan.annotation;

import lombok.Data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author goudan
 * @date 2023/7/28 15:34
 * @desc AppIdDataIsolation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@DataIsolation(value = "app_id")
public @interface AppIdDataIsolation {
}
