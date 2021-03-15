package com.example.database.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.database.common.constant.Constant;
import com.example.database.common.record.dto.SetUpDataDTO;
import com.example.database.common.service.impl.CrudServiceImpl;
import com.example.database.common.utils.ConvertUtils;
import com.example.database.modules.sys.dao.SysSetUpTypeDao;
import com.example.database.modules.sys.dto.SysSetUpTypeDTO;
import com.example.database.modules.sys.entity.SysSetUpDataEntity;
import com.example.database.modules.sys.entity.SysSetUpTypeEntity;
import com.example.database.modules.sys.service.SysSetUpDataService;
import com.example.database.modules.sys.service.SysSetUpTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 表设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Service
public class SysSetUpTypeServiceImpl extends CrudServiceImpl<SysSetUpTypeDao, SysSetUpTypeEntity, SysSetUpTypeDTO> implements SysSetUpTypeService {

    @Autowired
    private SysSetUpDataService sysSetUpDataService;
    @Override
    public QueryWrapper<SysSetUpTypeEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String typeName = (String)params.get("typeName");
        QueryWrapper<SysSetUpTypeEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id)
                .eq(StringUtils.isNotBlank(typeName), "type_name", typeName);

        return wrapper;
    }


    @Override
    public SysSetUpTypeEntity getByTypeName(String typeName) {
        return baseDao.getByTypeName(typeName);
    }

    @Override
    public List<SysSetUpDataEntity> listBySetUpTypeId(Long setUpTypeId, Integer state) {
        return sysSetUpDataService.listBySetUpTypeId(setUpTypeId, state);
    }

    @Override
    public List<SetUpDataDTO> listByTypeNameSetUp(@NotNull String typeName) {
        SysSetUpTypeEntity sysSetUpTypeEntity = this.getByTypeName(typeName);
        if( sysSetUpTypeEntity == null)
            return null;
        List<SysSetUpDataEntity> sysSetUpDataEntitieList = sysSetUpDataService.listBySetUpTypeId(sysSetUpTypeEntity.getId(),
                Constant.RecordField.AVAILABLE.getValue());
        return ConvertUtils.sourceToTarget(sysSetUpDataEntitieList, SetUpDataDTO.class);
    }


}