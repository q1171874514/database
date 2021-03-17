package com.example.database.common.record;

import com.example.database.common.record.dto.RecordFieldDTO;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.*;

@Data
public class RecordFieldMap {
    public final static Map<String, Set<RecordFieldDTO>> recordField = new LinkedHashMap<>();

    public static void addRecordField(String key, Field field, String name) {
        if(RecordFieldMap.recordField.get(key) == null)
            RecordFieldMap.recordField.put(key, new LinkedHashSet());
        RecordFieldMap.recordField.get(key).add(valueOfFieldDTO(field, name));
    }

    public static RecordFieldDTO valueOfFieldDTO(Field field, String name) {
        RecordFieldDTO recordFieldDTO = new RecordFieldDTO();
        recordFieldDTO.setFieldName(name);
        recordFieldDTO.setType(field.getType().getTypeName());
        recordFieldDTO.setSimpleType(field.getType().getSimpleName());
        return recordFieldDTO;
    }
}
