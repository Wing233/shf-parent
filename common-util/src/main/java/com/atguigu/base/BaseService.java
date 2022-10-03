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

    Integer insert(T t);

    T getById(Serializable id);

    Integer update(T t);

    void delete(Serializable id);

    PageInfo<T> findPage(Map<String, Object> filters);

}
