package com.example.database.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.database.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 表字段设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_set_up_data")
public class SysSetUpDataEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 设置表名ID
     */
	private Long setUpTypeId;
    /**
     * 字段标签
     */
	private String label;
	/**
	 * 字段名
	 */
	private String fieldName;
    /**
     * 字段值
     */
	private String fieldValue;
    /**
     * 备注
     */
	private String remark;
	/**
	 * 保存类型
	 */
	private Integer saveType;
	/**
	 * 更新类型
	 */
	private Integer updateType;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 状态（0可用，1不可用）
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