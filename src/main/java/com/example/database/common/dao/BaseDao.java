/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.database.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
public interface BaseDao<T> extends BaseMapper<T> {
    @Select("SELECT ${field} FROM ${tableName} where ${whereContent} LIMIT 1")
    Map getByOut(@Param("field") String field, @Param("tableName") String tableName,
              @Param("whereContent") String whereContent);

    @Select("SELECT ${field} FROM ${tableName} where ${whereContent}")
    List<Map> listOut(@Param("field") String field, @Param("tableName") String tableName,
                   @Param("whereContent") String whereContent);
}
