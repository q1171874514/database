package com.example.database.modules.warehouse.dao;

import com.example.database.common.dao.BaseDao;
import com.example.database.modules.warehouse.entity.FolderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件夹
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Mapper
public interface FolderDao extends BaseDao<FolderEntity> {
    List<String> getFolderIdByWarehouseId(Object... warehouseId);
    List<String> getWarehouseIdByFolderId(Object... folderId);
}