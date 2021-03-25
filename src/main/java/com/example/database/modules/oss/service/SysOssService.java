/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.database.modules.oss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.database.common.page.PageData;
import com.example.database.common.service.BaseService;
import com.example.database.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * 
 * @author Mark sunlightcs@gmail.com
 */
public interface SysOssService extends BaseService<SysOssEntity> {
	QueryWrapper<SysOssEntity> getWrapper(Map<String, Object> params);

	PageData<SysOssEntity> page(Map<String, Object> params);

	List<SysOssEntity> getListByIds(List<Long> ids);

	List<SysOssEntity> fileDelete(List<SysOssEntity> sysOssEntityList);

	void updateStateById(Integer state, Object... id);
}
