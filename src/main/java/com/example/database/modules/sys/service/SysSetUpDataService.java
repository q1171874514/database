package com.example.database.modules.sys.service;

import com.example.database.common.record.dto.SetUpDataDTO;
import com.example.database.common.service.CrudService;
import com.example.database.modules.sys.dto.SysSetUpDataDTO;
import com.example.database.modules.sys.entity.SysSetUpDataEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表字段设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
public interface SysSetUpDataService extends CrudService<SysSetUpDataEntity, SysSetUpDataDTO> {
    List<SysSetUpDataEntity> listBySetUpTypeId(Long setUpTypeId, Integer state);
}