package com.example.database.common.record.dto;

import lombok.Data;

@Data
public class SetUpDataDTO {
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段值
     */
    private String fieldValue;
    /**
     * 保存类型（0默认，1初始）
     */
    private Integer saveType;
    /**
     * 更新类型（0可改，1不可改）
     */
    private Integer updateType;
    /**
     * 类型(0可用，1不可用)
     */
    private Integer type;
}
