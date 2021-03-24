package com.example.database.modules.warehouse.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.database.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 文件夹
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("folder")
public class FolderEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 上级id
	 */
	private Long pid;
    /**
     * 仓库id
     */
	private Long warehouseId;
    /**
     * 文件夹名称
     */
	private String name;
    /**
     * 容量大小
     */
	private Long size;
	/**
	 * 状态（0可用，1回收站，2不可用）
	 */
	private Integer state;
    /**
     * 更新者
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updater;
    /**
     * 更新时间
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
}