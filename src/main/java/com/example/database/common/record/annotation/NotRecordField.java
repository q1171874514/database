package com.example.database.common.record.annotation;

import java.lang.annotation.*;

/**
 * 不记录的字段（只有标有@RecordTypeField注解的类的字段有效）
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NotRecordField {

}
