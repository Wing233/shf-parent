package com.atguigu.base;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/27 20:15
 */


public interface BaseService<T> {

    /**
     * 增加
     * @return
     */
    Integer insert(T t);

    /**
     * 根据id查询单体对象
     * @param id
     * @return
     */
    T getById(Serializable id);

    /**
     * 修改
     * @return
     */
    Integer update(T t);

    /**
     * 根据id软删除对象数据
     * @param id
     */
    void delete(Serializable id);

    /**
     * 根据过滤条件查询分页数据
     * @param filters
     * @return
     */
    PageInfo<T> findPage(Map<String, Object> filters);

}
