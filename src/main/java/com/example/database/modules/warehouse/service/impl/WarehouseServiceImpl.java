package com.example.database.modules.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.database.common.exception.RenException;
import com.example.database.common.service.impl.CrudServiceImpl;
import com.example.database.modules.security.user.SecurityUser;
import com.example.database.modules.security.user.UserDetail;
import com.example.database.modules.warehouse.dao.UserWarehouseDao;
import com.example.database.modules.warehouse.dao.WarehouseDao;
import com.example.database.modules.warehouse.dto.WarehouseDTO;
import com.example.database.modules.warehouse.entity.UserWarehouseEntity;
import com.example.database.modules.warehouse.entity.WarehouseEntity;
import com.example.database.modules.warehouse.service.UserWarehouseService;
import com.example.database.modules.warehouse.service.WarehouseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 仓库
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Service
public class WarehouseServiceImpl extends CrudServiceImpl<WarehouseDao, WarehouseEntity, WarehouseDTO> implements WarehouseService {
    @Autowired
    private UserWarehouseService userWarehouseService;

    @Override
    protected WarehouseDTO entityToDto(WarehouseEntity entity, Class<?> dtoClass) {
        WarehouseDTO warehouseDTO = super.entityToDto(entity, dtoClass);
        warehouseDTO.setName(userWarehouseService.getByUserWarehouse(warehouseDTO.getId()).getWarehouseName());
        return warehouseDTO;
    }

    @Override
    protected List<WarehouseDTO> entityToDtoList(List<WarehouseEntity> entityList) {
        List<WarehouseDTO> dtoList = new ArrayList<>();
        entityList.stream().forEach((entity) -> {
            dtoList.add(this.entityToDto(entity, currentDtoClass()));
        });
        return dtoList;
    }

    @Override
    public QueryWrapper<WarehouseEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String state = (String)params.get("state");
        String[] loginDataIds = (String[])params.get("loginDataIds");
        QueryWrapper<WarehouseEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id)
                .eq(StringUtils.isNotBlank(state), "state", state)
                .in(loginDataIds != null, "id", loginDataIds.length == 0 ? new String[]{"-1"}: loginDataIds);
        return wrapper;
    }


    /**
     * list查找
     * @param params
     * @return
     */
    @Override
    public List<WarehouseDTO> loginDataList(Map<String, Object> params) {
        String[] userToWarehouseId = getUserToWarehouseId();
        if(userToWarehouseId.length == 0)
            return new LinkedList<>();
        params.put("loginDataIds", userToWarehouseId);
        return this.list(params);
    }

    /**
     * id查找
     * @param id
     * @return
     */
    @Override
    public WarehouseDTO loginDataInfo(Long id) {
        if(!isData(id))
            throw new RenException("仓库不存在或无权访问");
        WarehouseDTO warehouseDTO = this.get(id);
        return warehouseDTO;
    }

    /**
     * 保存
     * @param dto
     * @return
     */
    @Override
    public WarehouseDTO loginDataSave(WarehouseDTO dto) {
        this.save(dto);
        UserWarehouseEntity userWarehouseEntity = dtoToUserWarehouseEntity(dto);
        userWarehouseService.insert(userWarehouseEntity);
        return dto;
    }


    @Override
    public String[] getUserToWarehouseId() {
        Long userId = SecurityUser.getUser().getId();
        return userWarehouseService.getWarehouseIdByUserId(userId);
    }

    @Override
    public Boolean isData(Object... id) {
        if(id.length == 0) return false;
        Long userId = SecurityUser.getUser().getId();
        for (int i = 0; i < id.length; i++) {
            if(!userWarehouseService.isData(userId, id[i]))
                return false;
        }
        return true;
    }

    @Override
    public void updateState(Integer state, Object... id) {
        if(id.length == 0)
            return;
        baseDao.updateState(state, id);
    }

    @Override
    public void update(WarehouseDTO dto) {
        if(dto.getName() != null){
            UserWarehouseEntity userWarehouse = userWarehouseService.getByUserWarehouse(dto.getId());
            userWarehouse.setWarehouseName(dto.getName());
            userWarehouseService.updateById(userWarehouse);
        }
        super.update(dto);
    }

    private UserWarehouseEntity dtoToUserWarehouseEntity(WarehouseDTO dto) {
        UserDetail user = SecurityUser.getUser();
        UserWarehouseEntity userWarehouseEntity = new UserWarehouseEntity();
        userWarehouseEntity.setUserId(user.getId());
        userWarehouseEntity.setWarehouseId(dto.getId());
        userWarehouseEntity.setWarehouseName(dto.getName());
        return userWarehouseEntity;
    }
}