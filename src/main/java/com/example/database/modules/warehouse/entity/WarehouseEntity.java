package com.example.database.modules.warehouse.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.database.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 仓库
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("warehouse")
public class WarehouseEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
	private String name;
    /**
     * 容量大小{B)
     */
	private Long size;
	/**
	 * 状态（0在使用，1无人用）
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