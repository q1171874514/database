package com.example.database.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.database.modules.sys.entity.SysSetUpTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 表设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Mapper
public interface SysSetUpTypeDao extends BaseMapper<SysSetUpTypeEntity> {
	SysSetUpTypeEntity getByTypeName(@Param("typeName") String typeName);
}