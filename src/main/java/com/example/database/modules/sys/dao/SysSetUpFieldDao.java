package com.example.database.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.database.modules.sys.entity.SysSetUpFieldEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表字段设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Mapper
public interface SysSetUpFieldDao extends BaseMapper<SysSetUpFieldEntity> {
	
}