package com.example.database.modules.sys.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 表字段设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Data
public class SysSetUpFieldExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "设置表名ID")
    private Long setUpTableId;
    @Excel(name = "字段标签")
    private String fieldLabel;
    @Excel(name = "字段值")
    private String fieldValue;
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "排序")
    private Integer sort;
    @Excel(name = "类型（0未设置，1默认，2初始）")
    private Integer type;
    @Excel(name = "创建者")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;
    @Excel(name = "更新者")
    private Long updater;
    @Excel(name = "更新时间")
    private Date updateDate;

}