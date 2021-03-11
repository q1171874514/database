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
import com.example.database.modules.sys.dto.SysSetUpFieldDTO;
import com.example.database.modules.sys.excel.SysSetUpFieldExcel;
import com.example.database.modules.sys.service.SysSetUpFieldService;
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
 * 表字段设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@RestController
@RequestMapping("sys/syssetupfield")
@Api(tags="表字段设置")
public class SysSetUpFieldController {
    @Autowired
    private SysSetUpFieldService sysSetUpFieldService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("sys:syssetupfield:page")
    public Result<PageData<SysSetUpFieldDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SysSetUpFieldDTO> page = sysSetUpFieldService.page(params);

        return new Result<PageData<SysSetUpFieldDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:syssetupfield:info")
    public Result<SysSetUpFieldDTO> get(@PathVariable("id") Long id){
        SysSetUpFieldDTO data = sysSetUpFieldService.get(id);

        return new Result<SysSetUpFieldDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:syssetupfield:save")
    public Result save(@RequestBody SysSetUpFieldDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysSetUpFieldService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:syssetupfield:update")
    public Result update(@RequestBody SysSetUpFieldDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysSetUpFieldService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:syssetupfield:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        sysSetUpFieldService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("sys:syssetupfield:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysSetUpFieldDTO> list = sysSetUpFieldService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, SysSetUpFieldExcel.class);
    }

}