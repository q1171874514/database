package com.example.database.modules.sys.service.impl;

import com.example.database.common.service.impl.BaseServiceImpl;
import com.example.database.modules.sys.dao.SysUserAddDao;
import com.example.database.modules.sys.entity.SysUserAddEntity;
import com.example.database.modules.sys.entity.SysUserEntity;
import com.example.database.modules.sys.service.SysUserAddService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class SysUserAddServiceImpl extends BaseServiceImpl<SysUserAddDao, SysUserAddEntity> implements SysUserAddService {

    @Override
    public void save(@NotNull SysUserEntity sysUserEntity, @NotNull SysUserAddEntity sysUserAddEntity) {
        sysUserAddEntity.setId(sysUserEntity.getId());
        this.insert(sysUserAddEntity);
    }

    @Override
    public SysUserAddEntity getAndSaveByUser(@NotNull SysUserEntity sysUserEntity) {
        SysUserAddEntity sysUserAddEntity = this.selectById(sysUserEntity.getId());
        if(sysUserAddEntity == null) {
            sysUserAddEntity = new SysUserAddEntity();
            this.save(sysUserEntity, sysUserAddEntity);
        }
        return sysUserAddEntity;
    }
}
