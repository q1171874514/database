package com.example.database.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.database.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 表设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_set_up_table")
public class SysSetUpTableEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 标签
     */
	private String label;
    /**
     * 表名
     */
	private String tableName;
    /**
     * 备注
     */
	private String remark;
    /**
     * 排序
     */
	private Integer sort;
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