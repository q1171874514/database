package com.example.database.modules.sys.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.database.modules.sys.entity.SysUserAddEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户额外表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-07
 */
@Mapper
public interface SysUserAddDao extends BaseMapper<SysUserAddEntity> {
	
}