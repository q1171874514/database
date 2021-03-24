package com.example.database.modules.warehouse.dto;

import lombok.Data;

/**
 * 文件备份表单
 */
@Data
public class FileBackupsDTO {
    /**
     * 备份文件id
     */
    private String[] ids;
    /**
     * 备份文件夹id
     */
    private Long folderId;
}
