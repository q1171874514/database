/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.example.database.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.example.database.common.page.PageData;
import com.example.database.common.service.CrudService;
import com.example.database.common.utils.ConvertUtils;
import org.springframework.beans.BeanUtils;
import java.util.*;

/**
 *  CRUD基础服务类
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class CrudServiceImpl<M extends BaseMapper<T>, T, D> extends BaseServiceImpl<M, T> implements CrudService<T, D> {

    protected Class<D> currentDtoClass() {
        return (Class<D>)ReflectionKit.getSuperClassGenericType(getClass(), 2);
    }

    public abstract QueryWrapper<T> getWrapper(Map<String, Object> params);

    /**
     * entity转换dto
     * @param entity
     * @return
     */
    protected D entityToDto(T entity, Class<?> dtoClass) {
        D dto = (D) ConvertUtils.sourceToTarget(entity, dtoClass);
        return dto;
    }

    /**
     * entityList转换dto
     * @param entityList
     * @return
     */
    protected List<D> entityToDtoList(List<T> entityList) {
//        List<D> dtoList = new ArrayList<>();
//        entityList.stream().forEach((entity) -> {
//            dtoList.add(this.entityToDto(entity, currentDtoClass()));
//        });

        return ConvertUtils.sourceToTarget(entityList, currentDtoClass());
    }

    /**
     * entityPage转换dto
     * @param page
     * @return
     */
    protected PageData<D> getDtoPageData(IPage<T> page) {
//        List<D> dtoList = this.entityToDtoList(page.getRecords(), dtoClass);
        return new PageData<>(ConvertUtils.sourceToTarget(page.getRecords(), currentDtoClass()), page.getTotal());
    }

    protected T dtoToEntity(D dto, Class<?> entityClass) {
        return (T) ConvertUtils.sourceToTarget(dto, entityClass);
    }


    @Override
    public PageData<D> page(Map<String, Object> params) {
        IPage<T> page = baseDao.selectPage(
            getPage(params, null, false),
            getWrapper(params)
        );

        return getDtoPageData(page);
    }

    @Override
    public List<D> list(Map<String, Object> params) {
        List<T> entityList;
        if (params == null)
            entityList = baseDao.selectList(null);
        else
            entityList = baseDao.selectList(getWrapper(params));
        return entityToDtoList(entityList);
    }

    @Override
    public D get(Long id) {
        T entity = baseDao.selectById(id);
        return entityToDto(entity, currentDtoClass());
    }

    @Override
    public void save(D dto) {
        T entity = dtoToEntity(dto, currentModelClass());
        insert(entity);
        //copy主键值到dto
        BeanUtils.copyProperties(entity, dto);
    }

    @Override
    public void update(D dto) {
        T entity = dtoToEntity(dto, currentModelClass());
        updateById(entity);
    }

    @Override
    public void delete(Long[] ids) {
        baseDao.deleteBatchIds(Arrays.asList(ids));
    }
}