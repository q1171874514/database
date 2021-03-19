package com.example.database.common.record;

import com.example.database.common.record.dto.RecordFieldDTO;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.*;

@Data
public class RecordFieldMap {
    public final static Map<String, Set<RecordFieldDTO>> recordField = new LinkedHashMap<>();

    public static void addRecordField(String key, Field field, Map<String, String> annotationDataMap) {
        if(RecordFieldMap.recordField.get(key) == null)
            RecordFieldMap.recordField.put(key, new LinkedHashSet());
        RecordFieldMap.recordField.get(key).add(valueOfFieldDTO(field, annotationDataMap));
    }

    public static RecordFieldDTO valueOfFieldDTO(Field field, Map<String, String> annotationDataMap) {
        RecordFieldDTO recordFieldDTO = new RecordFieldDTO();
        recordFieldDTO.setFieldName(annotationDataMap.get("fieldName") == null? field.getName()
                : annotationDataMap.get("fieldName"));
        recordFieldDTO.setType(field.getType().getTypeName());
        recordFieldDTO.setSimpleType(annotationDataMap.get("type") == null? field.getType().getSimpleName()
                : annotationDataMap.get("type"));
        recordFieldDTO.setLabel(annotationDataMap.get("label"));
        return recordFieldDTO;
    }
}
