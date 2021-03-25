/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.database.modules.oss.controller;

import com.google.gson.Gson;
import com.example.database.common.annotation.LogOperation;
import com.example.database.common.constant.Constant;
import com.example.database.common.exception.ErrorCode;
import com.example.database.common.page.PageData;
import com.example.database.common.utils.*;
import com.example.database.common.validator.ValidatorUtils;
import com.example.database.common.validator.group.AliyunGroup;
import com.example.database.common.validator.group.QcloudGroup;
import com.example.database.common.validator.group.QiniuGroup;
import com.example.database.modules.oss.cloud.CloudStorageConfig;
import com.example.database.modules.oss.cloud.OSSFactory;
import com.example.database.modules.oss.entity.SysOssEntity;
import com.example.database.modules.oss.service.SysOssService;
import com.example.database.modules.sys.service.SysParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * 文件上传
 * 
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("sys/oss")
@Api(tags="文件上传")
public class SysOssController {
	@Autowired
	private SysOssService sysOssService;
    @Autowired
    private SysParamsService sysParamsService;

    private final static String KEY = Constant.CLOUD_STORAGE_CONFIG_KEY;

	@GetMapping("page")
	@ApiOperation(value = "分页")
	@RequiresPermissions("sys:oss:all")
	public Result<PageData<SysOssEntity>> page(@ApiIgnore @RequestParam Map<String, Object> params){
		PageData<SysOssEntity> page = sysOssService.page(params);

		return new Result<PageData<SysOssEntity>>().ok(page);
	}

    @GetMapping("info")
	@ApiOperation(value = "云存储配置信息")
    @RequiresPermissions("sys:oss:all")
    public Result<CloudStorageConfig> info(){
        CloudStorageConfig config = sysParamsService.getValueObject(KEY, CloudStorageConfig.class);

        return new Result<CloudStorageConfig>().ok(config);
    }

	@PostMapping
	@ApiOperation(value = "保存云存储配置信息")
	@LogOperation("保存云存储配置信息")
	@RequiresPermissions("sys:oss:all")
	public Result saveConfig(@RequestBody CloudStorageConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);

		if(config.getType() == Constant.CloudService.QINIU.getValue()){
			//校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		}else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}

		sysParamsService.updateValueByCode(KEY, new Gson().toJson(config));

		return new Result();
	}

	@PostMapping("upload")
	@ApiOperation(value = "上传文件")
	@LogOperation("上传文件")
	@RequiresPermissions("sys:oss:all")
	public Result<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			return new Result<Map<String, Object>>().error(ErrorCode.UPLOAD_FILE_EMPTY);
		}

		//上传文件
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String url = OSSFactory.build().uploadSuffix(file.getBytes(), extension);

		//保存文件信息
		SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.insert(ossEntity);

		Map<String, Object> data = new HashMap<>(1);
		data.put("src", url);

		return new Result<Map<String, Object>>().ok(data);
	}

	@DeleteMapping
	@ApiOperation(value = "删除")
	@LogOperation("删除")
	@RequiresPermissions("sys:oss:all")
	public Result delete(@RequestBody Long[] ids){
		sysOssService.deleteBatchIds(Arrays.asList(ids));
		return new Result();
	}

	@DeleteMapping("file")
	@ApiOperation(value = "文件删除")
	@LogOperation("文件删除")
	@RequiresPermissions("sys:oss:all")
	public Result fileDelete(@RequestBody Long[] ids){
		List<SysOssEntity> sysOssEntityList = sysOssService.getListByIds(Arrays.asList(ids));
		for (SysOssEntity sysOssEntity: sysOssEntityList) {
			if(Constant.oss.STATEINVALID.getValue() == sysOssEntity.getState())
				return new Result().error("有文件可用，不可删除");
		}
		sysOssService.fileDelete(sysOssEntityList);
		return new Result();
	}

}