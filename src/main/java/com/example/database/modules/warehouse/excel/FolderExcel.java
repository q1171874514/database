package com.example.database.modules.warehouse.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 文件夹
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Data
public class FolderExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "仓库id")
    private Long warehouseId;
    @Excel(name = "文件夹名称")
    private String name;
    @Excel(name = "容量大小")
    private Long size;
    @Excel(name = "创建者")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;
    @Excel(name = "更新者")
    private Long updater;
    @Excel(name = "更新时间")
    private Date updateDate;

}