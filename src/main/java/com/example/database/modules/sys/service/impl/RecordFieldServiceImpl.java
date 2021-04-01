package com.example.database.modules.sys.service.impl;

import com.example.database.common.record.RecordFieldMap;
import com.example.database.common.record.dto.RecordFieldDTO;
import com.example.database.modules.sys.service.RecordFieldService;
import com.example.database.modules.sys.service.SysSetUpDataService;
import com.example.database.modules.sys.service.SysSetUpTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class RecordFieldServiceImpl implements RecordFieldService {
    @Autowired
    private SysSetUpTypeService sysSetUpTypeService;
    @Autowired
    private SysSetUpDataService sysSetUpDataService;

    @Override
    public Set<String> listSaveType() {
        Set<String> typeNameSet = new LinkedHashSet<>(RecordFieldMap.recordField.keySet());
        sysSetUpTypeService.list(null).stream()
                .map(dto -> dto.getTypeName())
                .forEach(typeNameSet::remove);
        return typeNameSet;
    }

    @Override
    public Set<RecordFieldDTO> listSaveData(@NotNull Long setUpTypeId) {

        String typeName = sysSetUpTypeService.selectById(setUpTypeId).getTypeName();
        LinkedHashSet<RecordFieldDTO> recordDataSet = new LinkedHashSet<>();
        Map<String, Boolean> typeNameToBoolMap = new LinkedHashMap<>();
        sysSetUpDataService.listBySetUpTypeId(setUpTypeId, null).stream()
                .map(dto -> dto.getFieldName())
                .forEach(fieldName -> typeNameToBoolMap.put(fieldName, true));
        RecordFieldMap.recordField.get(typeName).stream()
                .filter(dto -> typeNameToBoolMap.get(dto.getFieldName()) == null)
                .forEach(recordDataSet::add);

        return recordDataSet;
    }
}
