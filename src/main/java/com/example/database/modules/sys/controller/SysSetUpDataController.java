package com.example.database.modules.sys.controller;

import com.example.database.common.annotation.LogOperation;
import com.example.database.common.constant.Constant;
import com.example.database.common.page.PageData;
import com.example.database.common.record.RecordFieldMap;
import com.example.database.common.record.dto.RecordFieldDTO;
import com.example.database.common.utils.ExcelUtils;
import com.example.database.common.utils.Result;
import com.example.database.common.validator.AssertUtils;
import com.example.database.common.validator.ValidatorUtils;
import com.example.database.common.validator.group.AddGroup;
import com.example.database.common.validator.group.DefaultGroup;
import com.example.database.common.validator.group.UpdateGroup;
import com.example.database.modules.sys.dto.SysSetUpDataDTO;
import com.example.database.modules.sys.dto.SysSetUpTypeDTO;
import com.example.database.modules.sys.excel.SysSetUpDataExcel;
import com.example.database.modules.sys.service.SysSetUpDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import com.example.database.modules.sys.service.SysSetUpTypeService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 表字段设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@RestController
@RequestMapping("sys/setup/data")
@Api(tags="表字段设置")
public class SysSetUpDataController {
    @Autowired
    private SysSetUpDataService SysSetUpDataService;

    @Autowired
    private SysSetUpTypeService SysSetUpTypeService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("sys:setuptype:page")
    public Result<PageData<SysSetUpDataDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SysSetUpDataDTO> page = SysSetUpDataService.page(params);

        return new Result<PageData<SysSetUpDataDTO>>().ok(page);
    }


    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:setuptype:info")
    public Result<SysSetUpDataDTO> get(@PathVariable("id") Long id){
        SysSetUpDataDTO data = SysSetUpDataService.get(id);

        return new Result<SysSetUpDataDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:setuptype:save")
    public Result save(@RequestBody SysSetUpDataDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        RecordFieldMap.recordField.get(dto.getFieldName());
        SysSetUpTypeDTO sysSetUpTypeDTO = SysSetUpTypeService.get(dto.getSetUpTypeId());
        if(sysSetUpTypeDTO == null || RecordFieldMap.recordField.get(sysSetUpTypeDTO.getTypeName()) == null)
            return new Result().error("字段存在");
        Set<RecordFieldDTO> recordFieldDTOSet = RecordFieldMap.recordField.get(sysSetUpTypeDTO.getTypeName());
        recordFieldDTOSet.stream()
                .filter(fieldDTO -> fieldDTO.getFieldName().equals(dto.getFieldName()))
                .forEach(fieldDTO -> dto.setFieldType(fieldDTO.getSimpleType()));
        if(dto.getFieldType() == null)
            return new Result().error("字段无记录");

        SysSetUpDataService.save(dto);
        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:setuptype:update")
    public Result update(@RequestBody SysSetUpDataDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        SysSetUpDataService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:setuptype:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        SysSetUpDataService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("sys:setuptype:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysSetUpDataDTO> list = SysSetUpDataService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, SysSetUpDataExcel.class);
    }

}