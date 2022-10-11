package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.PermissionDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.service.PermissionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.atguigu.helper.PermissionHelper;

import java.util.*;


/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/4 21:23
 */

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }


    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        List<Permission> permissionList = permissionDao.findAll();
        List<Long> permissionIdList = rolePermissionDao.findPermissionIdListByRoleId(roleId);
        List<Map<String, Object>> zNodes = new ArrayList<>();
        for (Permission permission : permissionList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("pId", permission.getParentId());
            map.put("name", permission.getName());
            if (permissionIdList.contains(permission.getId())) {
                map.put("checked", true);
            }
            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public void saveRolePermissionRelationShip(Long roleId, Long @NotNull [] permissionIds) {
        rolePermissionDao.deleteByRoleId(roleId);

        if (null == permissionIds || permissionIds.length == 0) return;

        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            if(StringUtils.isEmpty(permissionId)) continue;
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionDao.insertBatch(rolePermissionList);
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(@NotNull Long adminId) {
        List<Permission> permissionList = null;
        if (adminId.intValue() == 1) {
            permissionList = permissionDao.findAll();
        } else {
            permissionList = permissionDao.findListByAdminId(adminId);
        }
        List<Permission> result = PermissionHelper.build(permissionList);
        return result;
    }

    @Override
    public List<Permission> findAllMenu() {
        List<Permission> permissionList = permissionDao.findAll();
        if (CollectionUtils.isEmpty(permissionList)) return null;
        List<Permission> result = PermissionHelper.build(permissionList);
        return result;
    }

    @Override
    public List<String> findCodeListByAdminId(Long adminId) {
        if (adminId.longValue() == 1) {
            return permissionDao.findAllCodeList();
        }
        return permissionDao.findCodeListByAdminId(adminId);
    }
}
