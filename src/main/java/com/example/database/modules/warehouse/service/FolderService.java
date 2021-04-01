package com.example.database.modules.warehouse.service;

import com.example.database.common.service.CrudService;
import com.example.database.modules.warehouse.dto.FolderDTO;
import com.example.database.modules.warehouse.entity.FolderEntity;

import java.util.List;
import java.util.Map;

/**
 * 文件夹
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
public interface FolderService extends CrudService<FolderEntity, FolderDTO> {

    List<FolderDTO> loginDataList(Map<String, Object> params);

    FolderDTO loginDataInfo(Long id);

    String[] getUserToFolderId();

    Boolean isData(Long... id);

    String[] getFolderIdByWarehouseId(Object... warehouseId);

    String[] getWarehouseIdByFolderId(Object... folderId);

    List<FolderDTO> getWarehouseFolderList(String warehouseId);
}