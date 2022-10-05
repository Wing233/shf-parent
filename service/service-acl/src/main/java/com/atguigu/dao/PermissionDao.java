package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/5 8:46
 */


public interface PermissionDao extends BaseDao<Permission> {

    List<Permission> findAll();

    List<Permission> findListByAdminId(Long adminId);

    List<String> findAllCodeList();

    List<String> findCodeListByAdminId(Long adminId);
}
