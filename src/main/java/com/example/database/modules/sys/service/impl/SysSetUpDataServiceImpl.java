package com.example.database.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.database.common.service.impl.CrudServiceImpl;
import com.example.database.modules.sys.dao.SysSetUpDataDao;
import com.example.database.modules.sys.dto.SysSetUpDataDTO;
import com.example.database.modules.sys.entity.SysSetUpDataEntity;
import com.example.database.modules.sys.service.SysSetUpDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 表字段设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Service
public class SysSetUpDataServiceImpl extends CrudServiceImpl<SysSetUpDataDao, SysSetUpDataEntity, SysSetUpDataDTO> implements SysSetUpDataService {

    @Override
    public QueryWrapper<SysSetUpDataEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String tableName = (String)params.get("tableName");
        QueryWrapper<SysSetUpDataEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id)
                .eq(StringUtils.isNotBlank(tableName), "table_name", tableName);

        return wrapper;
    }


    @Override
    public List<SysSetUpDataEntity> listBySetUpTypeId(Long setUpTypeId) {
        return baseDao.listBySetUpTypeId(setUpTypeId);
    }
}