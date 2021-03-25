package com.example.database.modules.warehouse.service;

import com.example.database.common.service.CrudService;
import com.example.database.modules.warehouse.dto.FileBackupsDTO;
import com.example.database.modules.warehouse.dto.FileDTO;
import com.example.database.modules.warehouse.entity.FileEntity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 文件
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
public interface FileService extends CrudService<FileEntity, FileDTO> {
    List<FileDTO> loginDataList(Map<String, Object> params);

    FileDTO loginDataInfo(Long id);

    FileDTO upload(FileDTO fileDTO) throws IOException;

    Boolean isData(Object... id);

    String[] getFolderIdById(Object... ids);

    void backups(FileBackupsDTO dto);

    Boolean isDataByOss(Long sysOssId);
}