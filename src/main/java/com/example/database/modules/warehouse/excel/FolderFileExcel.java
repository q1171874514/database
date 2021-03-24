package com.example.database.modules.warehouse.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 文件夹文件关联表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Data
public class FolderFileExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "文件夹id")
    private Long folderId;
    @Excel(name = "文件id")
    private Long fileId;
    @Excel(name = "文件名")
    private String fileName;
    @Excel(name = "创建者")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;

}