package com.example.database.common.utils;

/**
 * 字段工具类
 */
public class FieldHandleUtils {
    /**
     * 封装类转换
     * @param type
     * @param value
     * @return
     */
    public static Object objConver(Class<?> type, Object value) {
        if(value == null) return null;
        if (type == String.class) {
            return String.valueOf(value);
        } else if (type == Integer.class) {
            return Integer.parseInt(String.valueOf(value));
        } else if (type == Double.class) {
            return Double.parseDouble(String.valueOf(value));
        } else if(type == Long.class) {
            return Long.parseLong(String.valueOf(value));
        }
        return value;
    }
}
