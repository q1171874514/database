/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.database.modules.oss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.database.common.constant.Constant;
import com.example.database.common.page.PageData;
import com.example.database.common.service.impl.BaseServiceImpl;
import com.example.database.common.utils.FileUtil;
import com.example.database.modules.oss.dao.SysOssDao;
import com.example.database.modules.oss.entity.SysOssEntity;
import com.example.database.modules.oss.service.SysOssService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
public class SysOssServiceImpl extends BaseServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {
	@Override
	public QueryWrapper<SysOssEntity> getWrapper(Map<String, Object> params) {
		QueryWrapper<SysOssEntity> wrapper = new QueryWrapper<>();
		String id = (String)params.get("id");
		String creatorName = (String)params.get("creatorName");
		String type = (String)params.get("type");
		String state = (String)params.get("state");
		wrapper.eq(StringUtils.isNotBlank(id),"id", id)
				.eq(StringUtils.isNoneBlank(creatorName), "creator_name", creatorName)
				.eq(StringUtils.isNoneBlank(type), "type", type)
				.eq(StringUtils.isNoneBlank(state), "state", state);
		return wrapper;
	}

	@Override
	public PageData<SysOssEntity> page(Map<String, Object> params) {
		IPage<SysOssEntity> page = baseDao.selectPage(
			getPage(params, Constant.CREATE_DATE, false),
				getWrapper(params)
		);
		return getPageData(page, SysOssEntity.class);
	}

	@Override
	public List<SysOssEntity> getListByIds(List<Long> ids) {
		return baseDao.selectBatchIds(ids);
	}

	@Override
	public List<SysOssEntity> fileDelete(List<SysOssEntity> sysOssEntityList) {
		List<Long> idList = new LinkedList<>();
		sysOssEntityList.stream().forEach(sysOssEntity -> {
			if(FileUtil.delete(sysOssEntity.getUrl()))
				idList.add(sysOssEntity.getId());
		});
		if(idList.size() > 0)
			this.deleteBatchIds(idList);
		return null;
	}

	@Override
	public void updateStateById(Integer state, Object... id) {
		if(id.length == 0)return;
		baseDao.updateStateById(id, state);
	}
}
