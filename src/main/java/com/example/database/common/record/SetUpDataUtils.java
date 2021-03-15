package com.example.database.common.record;

import com.example.database.common.constant.Constant;
import com.example.database.common.exception.RenException;
import com.example.database.common.record.annotation.NotRecordField;
import com.example.database.common.record.annotation.RecordField;
import com.example.database.common.record.dto.SetUpDataDTO;
import lombok.SneakyThrows;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 根据设置处理对象
 */
public class SetUpDataUtils {

    /**
     * 保存设置
     * @param object    设置的对象
     * @param setUpDataDTOList 设置信息
     */
    public static void saveSetUp(Object object, List<SetUpDataDTO> setUpDataDTOList){
        if(setUpDataDTOList == null)return;
        setUpDataDTOList.stream()
                .filter(setUpDataDTO -> isSaveField(object,setUpDataDTO))
                .forEach(setUpDataDTO -> {
                    try {
                        Field field = object.getClass().getDeclaredField(setUpDataDTO.getFieldName());
                        Object value = objConver(field.getType(), setUpDataDTO.getFieldValue());
                        field.setAccessible(true);
                        field.set(object, value);
                        field.setAccessible(false);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 修改设置
     * @param object    设置的对象
     * @param setUpDataDTOList 设置信息
     */
    public static void updateSetUp(Object object, List<SetUpDataDTO> setUpDataDTOList){
        if(setUpDataDTOList == null)return;
        setUpDataDTOList.stream()
                .filter(setUpDataDTO -> isUpdateField(object,setUpDataDTO))
                .forEach(setUpDataDTO -> {
                    try {
                        Field field = object.getClass().getField(setUpDataDTO.getFieldName());
                        Object value = objConver(field.getType(), setUpDataDTO.getFieldValue());
                        field.setAccessible(true);
                        field.set(object, value);
                        field.setAccessible(false);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }
    @SneakyThrows
    private static Boolean isSaveField(Object object, SetUpDataDTO setUpDataDTO){
        Field field = object.getClass().getDeclaredField(setUpDataDTO.getFieldName());
        if(field == null) return false;
        field.setAccessible(true);
        Object fieldObject = field.get(object);
        field.setAccessible(false);
        if(setUpDataDTO.getSaveType() == Constant.RecordField.INITIAL.getValue() || fieldObject == null) {
            return true;
        }
        return false;
    }

    @SneakyThrows
    private static Boolean isUpdateField(Object object, SetUpDataDTO setUpDataDTO){
        Field field = object.getClass().getField(setUpDataDTO.getFieldName());
        if(field == null) return false;
        field.setAccessible(true);
        Object fieldObject = field.get(object);
        field.setAccessible(false);
        if(setUpDataDTO.getUpdateType() == Constant.RecordField.NOTMODIFIED.getValue()) {
            return true;
        }
        return false;
    }

    /**
     * Object转换
     *
     * @param type  转换类型
     * @param value 值
     * @return
     */
    private static Object objConver(Class<?> type, Object value) {
        if(value == null) return null;
        if (type == String.class) {
            return String.valueOf(value);
        } else if (type == Integer.class) {
            return Integer.parseInt(String.valueOf(value));
        } else if (type == Double.class) {
            return Double.parseDouble(String.valueOf(value));
        } else if(type == Long.class) {
            String s = String.valueOf(value);
            return Long.parseLong(s);
        }
        return value;
    }

    public static String getTypeName(@NotNull Object object) {
        Class<?> classData = object.getClass();
        RecordField recordField = classData.getAnnotation(RecordField.class);
        if(recordField != null) {
            return recordField.value();
        }
        NotRecordField notRecordField = classData.getAnnotation(NotRecordField.class);
        if(notRecordField != null)
            return notRecordField.value();
        return null;
    }
}
