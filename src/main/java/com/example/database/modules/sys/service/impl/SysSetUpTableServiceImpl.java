package com.example.database.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.database.common.service.impl.CrudServiceImpl;
import com.example.database.modules.sys.dao.SysSetUpTableDao;
import com.example.database.modules.sys.dto.SysSetUpTableDTO;
import com.example.database.modules.sys.entity.SysSetUpTableEntity;
import com.example.database.modules.sys.service.SysSetUpTableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 表设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Service
public class SysSetUpTableServiceImpl extends CrudServiceImpl<SysSetUpTableDao, SysSetUpTableEntity, SysSetUpTableDTO> implements SysSetUpTableService {

    @Override
    public QueryWrapper<SysSetUpTableEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String setUpTableId = (String)params.get("setUpTableId");
        String type = (String)params.get("type");
        QueryWrapper<SysSetUpTableEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id)
                .eq(StringUtils.isNotBlank(setUpTableId), "set_up_table_id", setUpTableId)
                .eq(StringUtils.isNotBlank(type), "type", type);

        return wrapper;
    }


}