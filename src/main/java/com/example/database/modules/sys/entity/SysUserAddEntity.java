package com.example.database.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.database.common.entity.BaseEntity;
import com.example.database.common.record.annotation.RecordTypeField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户额外表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_user_add")
@RecordTypeField("sys_user_add")
public class SysUserAddEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 仓库总大小（B）
     */
	private Long totalSize;
}