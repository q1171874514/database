/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.database.modules.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.database.modules.log.entity.SysLogErrorEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 异常日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Mapper
public interface SysLogErrorDao extends BaseMapper<SysLogErrorEntity> {
	
}
