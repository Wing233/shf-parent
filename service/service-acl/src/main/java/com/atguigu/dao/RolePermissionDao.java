package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.RolePermission;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/5 8:47
 */


public interface RolePermissionDao extends BaseDao<RolePermission> {

    List<Long> findPermissionIdListByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);
}
