package com.example.database.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.database.common.service.impl.CrudServiceImpl;
import com.example.database.modules.sys.dao.SysSetUpFieldDao;
import com.example.database.modules.sys.dto.SysSetUpFieldDTO;
import com.example.database.modules.sys.entity.SysSetUpFieldEntity;
import com.example.database.modules.sys.service.SysSetUpFieldService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 表字段设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Service
public class SysSetUpFieldServiceImpl extends CrudServiceImpl<SysSetUpFieldDao, SysSetUpFieldEntity, SysSetUpFieldDTO> implements SysSetUpFieldService {

    @Override
    public QueryWrapper<SysSetUpFieldEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String tableName = (String)params.get("tableName");
        QueryWrapper<SysSetUpFieldEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id)
                .eq(StringUtils.isNotBlank(tableName), "table_name", tableName);

        return wrapper;
    }


}