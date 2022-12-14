package com.atguigu.base;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/27 19:58
 */


public interface BaseDao<T> {

    /**
     * 保存一个实体
     * @param t
     */
    Integer insert(T t);

    void insertBatch(List<T> list);

    /**
     * 删除
     * @param id 标识ID 可以是自增长ID,也可以是唯一标识.
     */
    void delete(Serializable id);

    /**
     * 更新一个实体
     * @param t
     */
    Integer update(T t);

    /**
     * 通过一个标识ID 获取一个唯一实体
     * @param id 标识ID 可以是自增长ID,也可以是唯一标识.
     * @return
     */
    T getById(Serializable id);

    /**
     * 查询过滤后的页面数据
     * @param filters
     * @return
     */
    Page<T> findPage(Map<String, Object> filters);

}
