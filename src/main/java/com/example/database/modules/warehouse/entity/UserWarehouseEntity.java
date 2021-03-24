package com.example.database.modules.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.database.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户仓库关联表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("user_warehouse")
public class UserWarehouseEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
	private Long userId;
    /**
     * 仓库id
     */
	private Long warehouseId;
    /**
     * 仓库名
     */
	private String warehouseName;
}