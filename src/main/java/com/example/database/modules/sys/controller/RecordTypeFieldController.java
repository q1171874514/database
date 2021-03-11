package com.example.database.modules.sys.controller;

import com.example.database.common.record.RecordTypeFieldMap;
import com.example.database.common.utils.Result;
import com.example.database.modules.sys.dto.SysDeptDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/record")
@Api(tags="获取记录字段信息")
public class RecordTypeFieldController {
    @GetMapping("key")
    @ApiOperation("查询key")
    public Result listKey(){
        return new Result<>().ok(RecordTypeFieldMap.recordTypeField.keySet());
    }

    @GetMapping("field")
    @ApiOperation("查询field")
    public Result listField(@RequestParam Map<String, Object> params){
        String key = (String) params.get("key");
        if(key == null)
            return new Result<>().ok(RecordTypeFieldMap.recordTypeField.values());
        return new Result<>().ok(RecordTypeFieldMap.recordTypeField.get(key));
    }

    @GetMapping("list")
    @ApiOperation("获取所有")
    public Result list(){
        return new Result<>().ok(RecordTypeFieldMap.recordTypeField);
    }
}
