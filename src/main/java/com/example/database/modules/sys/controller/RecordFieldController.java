package com.example.database.modules.sys.controller;

import com.example.database.common.constant.Constant;
import com.example.database.common.record.RecordFieldMap;
import com.example.database.common.utils.Result;
import com.example.database.modules.sys.service.RecordFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sys/record")
@Api(tags="获取记录字段信息")
public class RecordFieldController {
    @Autowired
    private RecordFieldService recordFieldService;

    @GetMapping("typeName")
    @ApiOperation("查询key")
    public Result listKey(){
        return new Result<>().ok(recordFieldService.listSaveType());
    }

    @GetMapping("field")
    @ApiOperation("查询field")
    public Result listField(Long setUpTypeId){
        if(setUpTypeId == null)
            return new Result<>().ok(RecordFieldMap.recordField.values());
        return new Result<>().ok(recordFieldService.listSaveData(setUpTypeId));
    }

    @GetMapping("list")
    @ApiOperation("获取所有")
    public Result list(){
        return new Result<>().ok(RecordFieldMap.recordField);
    }
//
//    @GetMapping("save/key")
//    @ApiOperation("查询可保存的key")
//    public Result listKey(){
//        return new Result<>().ok(RecordFieldMap.recordField.keySet());
//    }

}
