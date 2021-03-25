/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.database.modules.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.database.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 文件上传
 * 
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
    void updateStateById(@Param("id") Object[] id, @Param("state") Integer state);
}
