package com.example.database.modules.warehouse.controller;

import com.example.database.common.annotation.LogOperation;
import com.example.database.common.constant.Constant;
import com.example.database.common.exception.ErrorCode;
import com.example.database.common.page.PageData;
import com.example.database.common.utils.ExcelUtils;
import com.example.database.common.utils.FileUtil;
import com.example.database.common.utils.Result;
import com.example.database.common.validator.AssertUtils;
import com.example.database.common.validator.ValidatorUtils;
import com.example.database.common.validator.group.AddGroup;
import com.example.database.common.validator.group.DefaultGroup;
import com.example.database.common.validator.group.UpdateGroup;
import com.example.database.modules.warehouse.dto.FileBackupsDTO;
import com.example.database.modules.warehouse.dto.FileDTO;
import com.example.database.modules.warehouse.excel.FileExcel;
import com.example.database.modules.warehouse.service.FileService;
import com.example.database.modules.warehouse.service.FolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 文件
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@RestController
@RequestMapping("warehouse/file")
@Api(tags="文件")
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private FolderService folderService;
    
    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("warehouse:file:page")
    public Result<PageData<FileDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<FileDTO> page = fileService.page(params);

        return new Result<PageData<FileDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("warehouse:file:info")
    public Result<FileDTO> get(@PathVariable("id") Long id){
        FileDTO data = fileService.get(id);

        return new Result<FileDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("warehouse:file:save")
    public Result save(@RequestBody FileDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        fileService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("warehouse:file:update")
    public Result update(@RequestBody FileDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        fileService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("warehouse:file:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        fileService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("warehouse:file:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<FileDTO> list = fileService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, FileExcel.class);
    }

    @GetMapping("loginData/list")
    @ApiOperation("登入用户文件信息")
    public Result loginDataList(@RequestParam Map<String, Object> params) {
        List<FileDTO> dtoList = fileService.loginDataList(params);
        return new Result().ok(dtoList);
    }

    @GetMapping("loginData/{id}")
    @ApiOperation("登入用户文件信息")
    public Result loginDataInfo(@PathVariable("id") Long id) {
        FileDTO dto = fileService.loginDataInfo(id);
        return new Result().ok(dto);
    }

    @PostMapping("loginData/backups")
    @ApiOperation("登入用户文件备份")
    @LogOperation("登入用户文件备份")
    public Result loginDataSave(@RequestBody FileBackupsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        if(!fileService.isData(dto.getIds()))
            return new Result().error("文件不存在或无权限访问");
        if(!folderService.isData(dto.getFolderId()))
            return new Result().error("文件夹不存在或无权限操作");
        fileService.backups(dto);
        return new Result();
    }

    @PostMapping("loginData/upload")
    @ApiOperation("登入用户文件上传")
    @LogOperation("登入用户文件上传")
    public Result loginDataUpload(@NotNull MultipartFile file, @NotNull Long folderId) throws IOException {

        if(!folderService.isData(folderId))
            return new Result().error("文件夹不存在或无权限操作");
        FileDTO dto = new FileDTO();
        dto.setFile(file);
        dto.setFolderId(folderId);
        if (dto.getFile().isEmpty()) {
            return new Result<Map<String, Object>>().error(ErrorCode.UPLOAD_FILE_EMPTY);
        }
        fileService.upload(dto);
        return new Result();
    }

    @GetMapping("loginData/download/{id}")
    @ApiOperation("登入用户文件下载")
    @LogOperation("登入用户文件下载")
    public ResponseEntity loginDataDownload(@PathVariable("id") Long id) throws Exception {
        FileDTO fileDTO = fileService.get(id);
        if(!fileService.isData(id))
            return null;
        return FileUtil.download(fileDTO.getUrl(), fileDTO.getName());
    }

    @PutMapping("loginData")
    @ApiOperation("登入用户文件修改")
    @LogOperation("登入用户文件修改")
    public Result loginDataUpdate(@RequestBody FileDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        if(!fileService.isData(dto.getId()))
            return new Result().error("文件不存在或无权限操作");
        fileService.update(dto);
        return new Result();
    }

    @DeleteMapping("loginData")
    @ApiOperation("登入用户文件删除")
    @LogOperation("登入用户文件删除")
    public Result loginDataDelete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");
        if(!fileService.isData(ids))
            return new Result().error("文件夹不存在或无权限操作");
        fileService.delete(ids);

        return new Result();
    }




}