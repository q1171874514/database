package com.example.database.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.database.modules.sys.entity.SysSetUpDataEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表字段设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Mapper
public interface SysSetUpDataDao extends BaseMapper<SysSetUpDataEntity> {
	List<SysSetUpDataEntity> listBySetUpTypeId(@Param("setUpTypeId") Long setUpTypeId);
}