package com.example.database.modules.warehouse.dao;

import com.example.database.common.dao.BaseDao;
import com.example.database.modules.warehouse.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文件
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Mapper
public interface FileDao extends BaseDao<FileEntity> {
	List<String> getFolderIdById(Object... ids);
}