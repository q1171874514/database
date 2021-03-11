package com.example.database.common.service.impl;

import com.example.database.common.annotation.OutTablesField;
import com.example.database.common.dao.OutFieldDao;
import com.example.database.common.service.OutFieldService;

import java.lang.reflect.Field;
import java.util.*;

public abstract class OutFieldServiceImpl<M extends OutFieldDao<T>, T, D> extends CrudServiceImpl<M, T, D> implements OutFieldService<T, D> {
    /**
     * 获取dto标有OutTablesField的字段
     */
    protected List<Field> outDtoFields = new ArrayList<>();

    /**
     * 获取对应外键字段信息(key外键名，val外键字段Field)
     */
    protected Map<String, Field> foreignNameToFieldMap = new HashMap<>();

    /**
     * 记录要查询的字段（key：查询条件， val：查询的字段名（逗号隔开））
     * @return
     */
    protected Map<String, String> whereToOutKey = new HashMap<>();

    /**
     * 获取dto带有OutTablesField注解的字段
     */
    public OutFieldServiceImpl() {
        Map<String, StringBuilder> whereToOutKeyMap = new HashMap<>();
        Arrays.stream(currentDtoClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(OutTablesField.class) != null)
                .forEach(field -> {
                    OutTablesField annotation = field.getAnnotation(OutTablesField.class);
                    try {
                        Field foreignField = currentDtoClass().getDeclaredField(annotation.foreignKey());
                        //判断外键是否存在，存在记录
                        if(foreignField != null) {
                            //把字段的私有变成公有
                            field.setAccessible(true);
                            //保存属性
                            outDtoFields.add(field);
                            //保存外键字段
                            if(foreignNameToFieldMap.get(annotation.foreignKey()) == null) {
                                //把外键foreignField私有变成公有
                                foreignField.setAccessible(true);
                                foreignNameToFieldMap.put(annotation.foreignKey(), foreignField);
                            }
                            //保存外表字段
                            //没有条件的话，编写默认条件（outKey = foreignKey）
                            String where = annotation.outTableWhere().equals("")
                                    ? annotation.outKey() + " = " + annotation.foreignKey()
                                    : annotation.outTableWhere();
                            if (whereToOutKeyMap.get(where) == null) {
                                whereToOutKeyMap.put(where, new StringBuilder(annotation.outField()));
                            } else {
                                whereToOutKeyMap.get(where).append("," + annotation.outField());
                            }
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                });
        //把查询记录到this.whereToOutKey
        whereToOutKeyMap.keySet().stream().forEach((key) -> {
            this.whereToOutKey.put(key, whereToOutKeyMap.get(key).toString());
        });

    }

    /**
     * 增加外表字段
     * @param dto
     * outTablesEntityMap 外表信息（key：外键名+外键值）
     * @return
     */
    protected D addOutTablesField(D dto, Map<String, Map> outTablesToEntityMap) {
        this.outDtoFields.stream().forEach(field -> {
            //获取注解信息
            OutTablesField annotation = field.getAnnotation(OutTablesField.class);
            try {
                //获取字段的外键字段
                Field foreignField = foreignNameToFieldMap.get(annotation.foreignKey());
                String foreignVal = "null";
                if(foreignField != null) {
                    foreignVal = String.valueOf(foreignField.get(dto));
                }
                if(!foreignVal.equals("null") && !foreignVal.equals("")) {
                    //获取外表信息
                    Map outEntityValue = outTablesToEntityMap.get(annotation.foreignKey() + foreignVal);
                    //如果没有，进行编写查询
                    if (outEntityValue == null) {
                        //编写where语句
                        String whereContent = "".equals(annotation.outTableWhere())
                                ? annotation.outKey() + " = " + foreignVal
                                : annotation.outTableWhere();
                        String where = annotation.outTableWhere().equals("")
                                ? annotation.outKey() + " = " + annotation.foreignKey()
                                : annotation.outTableWhere();
                        //查询并记录
                        outEntityValue = this.baseDao.getByOut(whereToOutKey.get(where)
                                , annotation.outTableName(), whereContent);
                        outTablesToEntityMap.put(annotation.foreignKey() + foreignVal, outEntityValue);
                    }
                    if(outEntityValue != null) {
                        field.set(dto, outEntityValue.get(annotation.outField()));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return dto;
    }

    @Override
    protected D entityToDto(T entity, Class<?> dtoClass) {
        D dto = super.entityToDto(entity, dtoClass);
        addOutTablesField(dto, new HashMap<>());
        return dto;
    }
}
