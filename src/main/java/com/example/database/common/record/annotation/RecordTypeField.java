package com.example.database.common.record.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;

/**
 *记录类字段
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RecordTypeField {
    /**
     * key
     * @return
     */
    String value();
}
