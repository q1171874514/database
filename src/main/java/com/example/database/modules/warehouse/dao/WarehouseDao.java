package com.example.database.modules.warehouse.dao;

import com.example.database.common.dao.BaseDao;
import com.example.database.modules.warehouse.entity.WarehouseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 仓库
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Mapper
public interface WarehouseDao extends BaseDao<WarehouseEntity> {
    void updateState(@Param("state") Object state, @Param("id") Object... id);
}