package com.example.database.modules.sys.service;

import com.example.database.common.record.dto.SetUpDataDTO;
import com.example.database.common.service.CrudService;
import com.example.database.modules.sys.dto.SysSetUpTypeDTO;
import com.example.database.modules.sys.entity.SysSetUpDataEntity;
import com.example.database.modules.sys.entity.SysSetUpTypeEntity;

import java.util.List;

/**
 * 表设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
public interface SysSetUpTypeService extends CrudService<SysSetUpTypeEntity, SysSetUpTypeDTO> {

    SysSetUpTypeEntity getByTypeName(String typeName);

    List<SysSetUpDataEntity> listBySetUpTypeId(Long setUpTypeId, Integer state);

    List<SetUpDataDTO> listByTypeNameSetUp(String typeName);
}