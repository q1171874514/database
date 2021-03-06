package com.example.database.common.record.dto;

import lombok.Data;

@Data
public class RecordFieldDTO {
    //字段名
    private String fieldName;

    //字段类型
    private String type;

    //字段类型
    private String simpleType;

    //标签
    private String label;

}
