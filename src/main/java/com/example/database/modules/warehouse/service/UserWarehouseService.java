package com.example.database.modules.warehouse.service;

import com.example.database.common.service.BaseService;
import com.example.database.modules.warehouse.entity.UserWarehouseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户仓库关联表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
public interface UserWarehouseService extends BaseService<UserWarehouseEntity> {
    String[] getWarehouseIdByUserId(Object... userId);

    String[] getUserIdByWarehouseId(Object... warehouseId);

    Boolean isData(Object userId, Object warehouseId);

    Boolean deleteByUserWarehouse(Object userId, Object... warehouseId);

    UserWarehouseEntity getByUserWarehouse(Object warehouseId);
}