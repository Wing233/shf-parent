package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/4 21:03
 */


public interface PermissionService extends BaseService<Permission> {
    /**
     * 根据角色id查询权限
     * @param roleId
     * @return
     */
    List<Map<String, Object>> findPermissionByRoleId(Long roleId);

    /**
     * 保存角色权限
     * @param roleId
     * @param permissionIds
     */
    void saveRolePermissionRelationShip(Long roleId, Long[] permissionIds);

    List<Permission> findMenuPermissionByAdminId(Long adminId);

    List<Permission> findAllMenu();

    List<String> findCodeListByAdminId(Long adminId);

}
