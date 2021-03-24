package com.example.database.modules.warehouse.controller;

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
import com.example.database.modules.warehouse.dto.FolderDTO;
import com.example.database.modules.warehouse.excel.FolderExcel;
import com.example.database.modules.warehouse.service.FolderService;
import com.example.database.modules.warehouse.service.WarehouseService;
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
 * 文件夹
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@RestController
@RequestMapping("warehouse/folder")
@Api(tags="文件夹")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @Autowired
    private WarehouseService warehouseService;
    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("warehouse:folder:page")
    public Result<PageData<FolderDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<FolderDTO> page = folderService.page(params);

        return new Result<PageData<FolderDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("warehouse:folder:info")
    public Result<FolderDTO> get(@PathVariable("id") Long id){
        FolderDTO data = folderService.get(id);

        return new Result<FolderDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("warehouse:folder:save")
    public Result save(@RequestBody FolderDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        folderService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("warehouse:folder:update")
    public Result update(@RequestBody FolderDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        folderService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("warehouse:folder:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        folderService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("warehouse:folder:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<FolderDTO> list = folderService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, FolderExcel.class);
    }



    @GetMapping("loginData/list")
    @ApiOperation("登入用户文件夹信息")
    public Result loginDataList(@RequestParam Map<String, Object> params) {
        List<FolderDTO> dtoList = folderService.loginDataList(params);
        return new Result().ok(dtoList);
    }

    @GetMapping("loginData/{id}")
    @ApiOperation("登入用户文件夹信息")
    public Result loginDataInfo(@PathVariable("id") Long id) {
        FolderDTO dto = folderService.loginDataInfo(id);
        return new Result().ok(dto);
    }

    @PostMapping("loginData")
    @ApiOperation("登入用户保存")
    @LogOperation("登入用户保存")
    public Result loginDataSave(@RequestBody FolderDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        if(!warehouseService.isData(dto.getWarehouseId()))
            return new Result().error("仓库不存在或无权限操作");
        folderService.save(dto);
        return new Result();
    }

    @PutMapping("loginData")
    @ApiOperation("登入用户修改")
    @LogOperation("登入用户修改")
    public Result loginDataUpdate(@RequestBody FolderDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        if(!folderService.isData(dto.getId()))
            return new Result().error("仓库不存在或无权限操作");
        folderService.update(dto);
        return new Result();
    }

    @DeleteMapping("loginData")
    @ApiOperation("登入用户删除")
    @LogOperation("登入用户删除")
    public Result loginDataDelete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");
        if(!folderService.isData(ids))
            return new Result().error("文件夹不存在或无权限操作");
        folderService.delete(ids);
        return new Result();
    }

}