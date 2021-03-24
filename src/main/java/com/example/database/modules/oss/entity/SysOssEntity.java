/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.database.modules.oss.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.database.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件上传
 * 
 * @author Mark sunlightcs@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_oss")
public class SysOssEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * URL地址
	 */
	private String url;

	/**
	 * 用户名
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String creatorName;

	/**
	 * 类型
	 */
	private Integer type;
}