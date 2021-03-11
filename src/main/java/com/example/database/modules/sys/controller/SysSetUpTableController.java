package com.example.database.modules.sys.controller;

import com.example.database.common.annotation.LogOperation;
import com.example.database.common.constant.Constant;
import com.example.database.common.page.PageData;
import com.example.database.common.utils.ExcelUtils;
import com.example.database.common.utils.Result;
import com.example.database.common.validator.AssertUtils;
import com.example.database.common.validator.ValidatorUtils;
import com.example.database.common.validator.group.AddGroup;
import com.example.database.common.validator.group.DefaultGroup;
import com.example.database.common.validator.group.UpdateGroup;
import com.example.database.modules.sys.dto.SysSetUpTableDTO;
import com.example.database.modules.sys.excel.SysSetUpTableExcel;
import com.example.database.modules.sys.service.SysSetUpTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 表设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@RestController
@RequestMapping("sys/syssetuptable")
@Api(tags="表设置")
public class SysSetUpTableController {
    @Autowired
    private SysSetUpTableService sysSetUpTableService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("sys:syssetuptable:page")
    public Result<PageData<SysSetUpTableDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SysSetUpTableDTO> page = sysSetUpTableService.page(params);

        return new Result<PageData<SysSetUpTableDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:syssetuptable:info")
    public Result<SysSetUpTableDTO> get(@PathVariable("id") Long id){
        SysSetUpTableDTO data = sysSetUpTableService.get(id);

        return new Result<SysSetUpTableDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:syssetuptable:save")
    public Result save(@RequestBody SysSetUpTableDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysSetUpTableService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:syssetuptable:update")
    public Result update(@RequestBody SysSetUpTableDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysSetUpTableService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:syssetuptable:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        sysSetUpTableService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("sys:syssetuptable:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysSetUpTableDTO> list = sysSetUpTableService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, SysSetUpTableExcel.class);
    }

}