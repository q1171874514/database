package com.example.database.modules.sys.controller;

import com.example.database.common.record.RecordFieldMap;
import com.example.database.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sys/record")
@Api(tags="获取记录字段信息")
public class RecordFieldController {
    @GetMapping("key")
    @ApiOperation("查询key")
    public Result listKey(){
        return new Result<>().ok(RecordFieldMap.recordField.keySet());
    }

    @GetMapping("value")
    @ApiOperation("查询field")
    public Result listField(@RequestParam Map<String, Object> params){
        String key = (String) params.get("key");
        if(key == null)
            return new Result<>().ok(RecordFieldMap.recordField.values());
        return new Result<>().ok(RecordFieldMap.recordField.get(key));
    }

    @GetMapping("list")
    @ApiOperation("获取所有")
    public Result list(){
        return new Result<>().ok(RecordFieldMap.recordField);
    }
}
