package com.example.database.modules.sys.service;

import com.example.database.common.service.BaseService;
import com.example.database.modules.sys.entity.SysUserAddEntity;
import com.example.database.modules.sys.entity.SysUserEntity;

public interface SysUserAddService extends BaseService<SysUserAddEntity> {
    void save(SysUserEntity sysUserEntity, SysUserAddEntity sysUserAddEntity);
    SysUserAddEntity getAndSaveByUser(SysUserEntity sysUserEntity);
}
