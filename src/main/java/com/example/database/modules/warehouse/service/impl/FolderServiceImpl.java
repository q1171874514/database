package com.example.database.modules.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.database.common.exception.RenException;
import com.example.database.common.service.impl.CrudServiceImpl;
import com.example.database.common.utils.TreeUtils;
import com.example.database.modules.warehouse.dao.FolderDao;
import com.example.database.modules.warehouse.dto.FolderDTO;
import com.example.database.modules.warehouse.entity.FolderEntity;
import com.example.database.modules.warehouse.service.FolderService;
import com.example.database.modules.warehouse.service.WarehouseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 文件夹
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Service
public class FolderServiceImpl extends CrudServiceImpl<FolderDao, FolderEntity, FolderDTO> implements FolderService {
    @Autowired
    private WarehouseService warehouseService;

    @Override
    public QueryWrapper<FolderEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String[] ids = (String[])params.get("ids");
        String[] loginDataIds = (String[])params.get("loginDataIds");
        if(loginDataIds != null && loginDataIds.length == 0)
            loginDataIds = new String[]{"-1"};
        String pid = (String)params.get("pid");
        String warehouseId = (String)params.get("warehouseId");
        QueryWrapper<FolderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id)
                .eq(StringUtils.isNotBlank(pid), "pid", pid)
                .eq(StringUtils.isNotBlank(warehouseId), "warehouse_id", warehouseId)
                .in(ids != null, "id", ids)
                .in(loginDataIds != null, "warehouse_id", loginDataIds);

        return wrapper;
    }


    @Override
    public List<FolderDTO> loginDataList(Map<String, Object> params) {
        String[] userToWarehouseIds = warehouseService.getUserToWarehouseId();
        if(userToWarehouseIds.length == 0)
            return new LinkedList<>();
        params.put("loginDataIds", userToWarehouseIds);
        return this.list(params);
    }

    @Override
    public FolderDTO loginDataInfo(Long id) {
        FolderDTO folderDTO = get(id);
        if(folderDTO == null || !warehouseService.isData(folderDTO.getWarehouseId()))
            throw new RenException("文件夹不存在");
        return folderDTO;
    }

    @Override
    public String[] getUserToFolderId() {
        String[] userToWarehouseId = warehouseService.getUserToWarehouseId();
        if(userToWarehouseId.length == 0)
            return new String[0];
        List<String> folderId = baseDao.getFolderIdByWarehouseId(userToWarehouseId);
        if(folderId.size() == 0)
            return new String[0];
        return folderId.toArray(new String[0]);
    }

    @Override
    public Boolean isData(Long... id) {
        if(id.length == 0)
            return false;
        String[] warehouseId = this.getWarehouseIdByFolderId(id);
        return warehouseService.isData(warehouseId);
    }

    @Override
    public String[] getFolderIdByWarehouseId(Object... warehouseId) {
        if(warehouseId.length == 0)
            return new String[0];
        return baseDao.getFolderIdByWarehouseId(warehouseId).toArray(new String[0]);
    }

    @Override
    public String[] getWarehouseIdByFolderId(Object... folderId) {
        if(folderId.length == 0)
            return new String[0];
        return baseDao.getWarehouseIdByFolderId(folderId).toArray(new String[0]);
    }

    @Override
    public List<FolderDTO> getWarehouseFolderList(String warehouseId) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("warehouseId", warehouseId);
        List<FolderDTO> dtoList = this.list(params);
        return TreeUtils.build(dtoList);
    }


}