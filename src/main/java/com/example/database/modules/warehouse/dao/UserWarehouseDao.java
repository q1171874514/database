package com.example.database.modules.warehouse.dao;

import com.example.database.common.dao.BaseDao;
import com.example.database.modules.warehouse.entity.UserWarehouseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户仓库关联表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Mapper
public interface UserWarehouseDao extends BaseDao<UserWarehouseEntity> {
	List<String> getWarehouseIdByUserId(Object... userId);

	List<String> getUserIdByWarehouseId(Object... warehouseId);

	Integer isData(@Param("userId") Object userId, @Param("warehouseId") Object warehouseId);

	Boolean deleteByUserWarehouse(@Param("userId") Object userId, @Param("warehouseId") Object... warehouseId);

	UserWarehouseEntity getByUserWarehouse(@Param("userId") Object userId, @Param("warehouseId") Object warehouseId);
}