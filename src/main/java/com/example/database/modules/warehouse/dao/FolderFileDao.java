package com.example.database.modules.warehouse.dao;

import com.example.database.common.dao.BaseDao;
import com.example.database.modules.warehouse.entity.FolderFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件夹文件关联表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Mapper
public interface FolderFileDao extends BaseDao<FolderFileEntity> {
    List<String> getFileIdByFolderId(Object... folderId);

    List<String> getFolderIdByFileId(Object... fileId);

    Integer isData(@Param("folderId") Object folderId, @Param("fileId") Object fileId);

    void deleteByFolderFile(@Param("folderId") Object[] folderId, @Param("fileId") Object... fileId);
}