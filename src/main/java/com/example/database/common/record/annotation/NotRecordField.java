package com.example.database.common.record.annotation;

import java.lang.annotation.*;

/**
 * 不记录的字段
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NotRecordField {

    String value() default "";

}
