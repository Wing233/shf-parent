package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Dict;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 14:49
 */


public interface DictDao extends BaseDao<Dict> {
    List<Dict> findListByParentId(Long parentId);
    Integer countIsParent(Long id);

    String getNameById(Long id);
    Dict getByDictCode(String dictCode);
}
