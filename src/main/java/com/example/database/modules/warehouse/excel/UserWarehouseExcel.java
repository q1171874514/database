package com.example.database.modules.warehouse.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 用户仓库关联表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Data
public class UserWarehouseExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "用户ID")
    private Long userId;
    @Excel(name = "仓库id")
    private Long warehouseId;
    @Excel(name = "仓库名")
    private String warehouseName;
    @Excel(name = "创建者")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;

}