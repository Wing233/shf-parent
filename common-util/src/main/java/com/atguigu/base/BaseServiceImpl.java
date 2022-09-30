package com.atguigu.base;

import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/27 20:18
 */

@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected  abstract  BaseDao<T> getEntityDao();

    public Integer insert(T t) {
        return getEntityDao().insert(t);
    }

    public void delete(Serializable id) {
        getEntityDao().delete(id);
    }

    public Integer update(T t) {
        return getEntityDao().update(t);
    }

    public T getById(Serializable id) {
        return getEntityDao().getById(id);
    }

    public PageInfo<T> findPage(@NotNull Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<T>(getEntityDao().findPage(filters), 5);
    }

}
