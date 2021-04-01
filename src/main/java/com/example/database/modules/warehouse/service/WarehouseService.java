package com.example.database.modules.warehouse.service;

import com.example.database.common.service.CrudService;
import com.example.database.modules.warehouse.dto.WarehouseDTO;
import com.example.database.modules.warehouse.entity.WarehouseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 仓库
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
public interface WarehouseService extends CrudService<WarehouseEntity, WarehouseDTO> {

    List<WarehouseDTO> loginDataList(Map<String, Object> params);

    WarehouseDTO loginDataInfo(Long id);

    WarehouseDTO loginDataSave(WarehouseDTO dto);

    String[] getUserToWarehouseId();

    Boolean isData(Object... id);

    void updateState(Integer state, Object... id);

    Boolean isState(Integer state, Object... id);
}