package com.example.database.modules.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.database.common.constant.Constant;
import com.example.database.common.exception.RenException;
import com.example.database.common.service.impl.CrudServiceImpl;
import com.example.database.modules.oss.cloud.OSSFactory;
import com.example.database.modules.oss.entity.SysOssEntity;
import com.example.database.modules.oss.service.SysOssService;
import com.example.database.modules.warehouse.dao.FileDao;
import com.example.database.modules.warehouse.dto.FileBackupsDTO;
import com.example.database.modules.warehouse.dto.FileDTO;
import com.example.database.modules.warehouse.entity.FileEntity;
import com.example.database.modules.warehouse.service.FileService;
import com.example.database.modules.warehouse.service.FolderService;
import com.example.database.modules.warehouse.service.WarehouseService;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 文件
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Service
public class FileServiceImpl extends CrudServiceImpl<FileDao, FileEntity, FileDTO> implements FileService {
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private FolderService folderService;
    @Autowired
    private SysOssService sysOssService;

    @Override
    public void delete(Long[] ids) {
        List<Long> sysOssIdList = baseDao.getSysOssIdById(ids);
        super.delete(ids);
        for (int i = 0; i < sysOssIdList.size(); i++) {
            if(!isDataByOss(sysOssIdList.get(i)))
                sysOssIdList.remove(i--);
        }
        sysOssService.updateStateById(Constant.oss.STATEINVALID.getValue(), sysOssIdList.toArray());
    }

    @Override
    public QueryWrapper<FileEntity> getWrapper(Map<String, Object> params){
        QueryWrapper<FileEntity> wrapper = new QueryWrapper<>();
        String id = (String)params.get("id");
        String[] ids = (String[])params.get("ids");
        String[] loginDataIds = (String[])params.get("loginDataIds");
        if(loginDataIds != null && loginDataIds.length == 0)
            loginDataIds = new String[]{"-1"};
        String folderId = (String)params.get("folderId");
        String warehouseId = (String)params.get("warehouseId");
        String[] warehouseIds = null;
        if(warehouseId != null) {
            warehouseIds = folderService.getFolderIdByWarehouseId(warehouseId);
            if(warehouseIds.length == 0)
                warehouseIds = new String[]{"-1"};
        }

        wrapper.eq(StringUtils.isNotBlank(id), "id", id)
                .in(ids != null, "id", ids)
                .eq(StringUtils.isNotBlank(folderId), "folder_id", folderId)
                .in(loginDataIds != null, "folder_id", loginDataIds)
                .in(warehouseIds != null, "folder_id", warehouseIds);

        return wrapper;
    }


    @Override
    public List<FileDTO> loginDataList(Map<String, Object> params) {
        if(params.get("id") != null) {
            if(!isData(params.get("id")))
                return new LinkedList<>();
            return this.list(params);
        }
        params.put("loginDataIds", folderService.getUserToFolderId());
        return this.list(params);
    }

    @Override
    public FileDTO loginDataInfo(Long id) {
        if(!isData(id))
            throw new RenException("文件夹不存在或无权限访问");
        return get(id);
    }

    /**
     * 文件上传
     * @param fileDTO
     * @return
     */
    @Override
    public FileDTO upload(FileDTO fileDTO) throws IOException {
        //上传文件
        String extension = FilenameUtils.getExtension(fileDTO.getFile().getOriginalFilename());
        String url = OSSFactory.build().uploadSuffix(fileDTO.getFile().getBytes(), extension);

        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setType(1);
        ossEntity.setCreateDate(new Date());
        sysOssService.insert(ossEntity);

        fileDTO.setSysOssId(ossEntity.getId());
        fileDTO.setUrl(url);
        fileDTO.setSize(fileDTO.getFile().getSize());
        fileDTO.setName(fileDTO.getFile().getOriginalFilename());
        this.save(fileDTO);

        return fileDTO;
    }


    @Override
    public Boolean isData(Object... id) {
        String[] folderId = getFolderIdById(id);
        String[] warehouseId = folderService.getWarehouseIdByFolderId(folderId);
        return warehouseService.isData(warehouseId);
    }

    @Override
    public String[] getFolderIdById(Object... ids) {
        List<String> folderId = baseDao.getFolderIdById(ids);
        if(folderId.size() == 0)
            return new String[0];
        return baseDao.getFolderIdById(ids).toArray(new String[0]);
    }

    @Override
    public void backups(FileBackupsDTO dto) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("ids", dto.getIds());
        List<FileEntity> fileEntities = baseDao.selectList(getWrapper(params));

        fileEntities.stream().forEach(fileEntity -> {
            fileEntity.setId(null);
            fileEntity.setFolderId(dto.getFolderId());
        });
        this.insertBatch(fileEntities);
    }

    @Override
    public Boolean isDataByOss(Long sysOssId) {
        return baseDao.isDataOss(sysOssId) != null;
    }


}