package com.example.database.modules.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.database.common.service.impl.BaseServiceImpl;
import com.example.database.modules.security.user.SecurityUser;
import com.example.database.modules.warehouse.dao.UserWarehouseDao;
import com.example.database.modules.warehouse.entity.UserWarehouseEntity;
import com.example.database.modules.warehouse.service.UserWarehouseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户仓库关联表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Service
public class UserWarehouseServiceImpl extends BaseServiceImpl<UserWarehouseDao, UserWarehouseEntity> implements UserWarehouseService {


    @Override
    public String[] getWarehouseIdByUserId(Object... userId) {
        if(userId.length == 0)
            return new String[0];
        List<String> warehouseId = baseDao.getWarehouseIdByUserId(userId);
        if(warehouseId.size() == 0)
            return new String[0];
        return warehouseId.toArray(new String[0]);
    }

    @Override
    public String[] getUserIdByWarehouseId(Object... warehouseId) {
        if(warehouseId.length == 0)
            return new String[0];
        List<String> userId = baseDao.getUserIdByWarehouseId(warehouseId);
        if(userId.size() == 0)
            return new String[0];
        return userId.toArray(new String[0]);
    }

    @Override
    public Boolean isData(Object userId, Object warehouseId) {
        Integer data = baseDao.isData(userId, warehouseId);
        String a = "d";
        return baseDao.isData(userId, warehouseId) != null;
    }

    @Override
    public Boolean deleteByUserWarehouse(Object userId, Object... warehouseId) {
        if(warehouseId.length == 0)
            return true;
        return baseDao.deleteByUserWarehouse(userId, warehouseId);
    }

    @Override
    public UserWarehouseEntity getByUserWarehouse(Object warehouseId) {
        return baseDao.getByUserWarehouse(SecurityUser.getUser().getId(), warehouseId);
    }
}