package com.example.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;

/**
 * @author
 * @date 2023/7/28 15:42
 * @desc MyNameValue
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@MyValue(value = "name")
@Inherited
public @interface MyNameValue {
    @AliasFor(annotation = MyValue.class, attribute = "name")
    String name() default "";
}
