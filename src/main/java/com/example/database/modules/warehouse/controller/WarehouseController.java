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
import com.example.database.modules.security.user.SecurityUser;
import com.example.database.modules.warehouse.dto.WarehouseDTO;
import com.example.database.modules.warehouse.excel.WarehouseExcel;
import com.example.database.modules.warehouse.service.UserWarehouseService;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 仓库
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@RestController
@RequestMapping("warehouse/warehouse")
@Api(tags="仓库")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private UserWarehouseService userWarehouseService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("warehouse:warehouse:page")
    public Result<PageData<WarehouseDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<WarehouseDTO> page = warehouseService.page(params);
        return new Result<PageData<WarehouseDTO>>().ok(page);
    }

    @GetMapping("loginData/list")
    @ApiOperation("登入用户仓库信息")
    public Result loginDataList(@RequestParam Map<String, Object> params) {

        List<WarehouseDTO> dtoList = warehouseService.loginDataList(params);
        return new Result().ok(dtoList);
    }

    @GetMapping("loginData/{id}")
    @ApiOperation("登入用户仓库信息")
    public Result loginDataInfo(@PathVariable("id") Long id) {
        WarehouseDTO dto = warehouseService.loginDataInfo(id);
        return new Result().ok(dto);
    }

    @PostMapping("loginData")
    @ApiOperation("登入用户保存仓库")
    @LogOperation("登入用户保存")
    public Result loginDataSave(@RequestBody WarehouseDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        warehouseService.loginDataSave(dto);
        return new Result();
    }

    @PutMapping("loginData")
    @ApiOperation("登入用户修改")
    @LogOperation("登入用户修改")
    public Result loginDataUpdate(@RequestBody WarehouseDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        if(!warehouseService.isData(dto.getId()))
            return new Result().error("仓库不存在或无权限修改");
        warehouseService.update(dto);

        return new Result();
    }

    @DeleteMapping("loginData")
    @ApiOperation("登入用户删除")
    @LogOperation("登入用户删除")
    public Result loginDataDelete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");
        Long userId = SecurityUser.getUser().getId();
        if(!warehouseService.isData(ids))
            return new Result().error("仓库不存在或无权限删除");
        userWarehouseService.deleteByUserWarehouse(userId, ids);
        List<Long> deleteIds = new LinkedList<>();
        for (int i = 0; i < ids.length; i++) {
            if(!userWarehouseService.isData(null, ids[i]))
                deleteIds.add(ids[i]);
        }
        warehouseService.updateState(1, deleteIds.toArray(new Long[0]));

        return new Result();
    }


    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("warehouse:warehouse:info")
    public Result<WarehouseDTO> get(@PathVariable("id") Long id){
        WarehouseDTO data = warehouseService.get(id);

        return new Result<WarehouseDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("warehouse:warehouse:save")
    public Result save(@RequestBody WarehouseDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        warehouseService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("warehouse:warehouse:update")
    public Result update(@RequestBody WarehouseDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        warehouseService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("warehouse:warehouse:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");
        if(warehouseService.isState(0, ids))
            new Result<>().error("有在使用的仓库，无法删除");
        warehouseService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("warehouse:warehouse:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<WarehouseDTO> list = warehouseService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, WarehouseExcel.class);
    }

}