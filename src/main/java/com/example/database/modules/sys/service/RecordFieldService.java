package com.example.database.modules.sys.service;

import com.example.database.common.record.dto.RecordFieldDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RecordFieldService {
    Set<String> listSaveType();

    Set<RecordFieldDTO> listSaveData(Long setUpTypeId);
}
