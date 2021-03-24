package com.example.database.modules.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.database.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 文件夹文件关联表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("folder_file")
public class FolderFileEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 文件夹id
     */
	private Long folderId;
    /**
     * 文件id
     */
	private Long fileId;
    /**
     * 文件名
     */
	private String fileName;
}