package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 14:39
 */


public interface DictService extends BaseService<Dict> {
    List<Map<String, Object>> findZnodes(Long id);
    List<Dict> findListByParentId(Long parentId);
    List<Dict> findListByDictCode(String dictCode);

    String getNameById(Long id);
}
