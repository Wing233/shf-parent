package com.atguigu.service.impl;

import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/27 13:00
 */

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    @Qualifier("roleDao")
    RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    /**
     * 根据用户获取角色数据
     * @param adminId
     * @return
     */
//@Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        //查询所有的角色
        List<Role> allRolesList = roleDao.findAll();

        //拥有的角色id
        List<Long> existRoleIdList = adminRoleDao.findRoleIdByAdminId(adminId);

        //对角色进行分类
        List<Role> noAssignRoleList = new ArrayList<>();
        List<Role> assignRoleList = new ArrayList<>();
        for (Role role : allRolesList) {
            //已分配
            if(existRoleIdList.contains(role.getId())) {
                assignRoleList.add(role);
            } else {
                noAssignRoleList.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("noAssignRoleList", noAssignRoleList);
        roleMap.put("assignRoleList", assignRoleList);
        return roleMap;
    }

    /**
     * 分配角色
     * @param adminId
     * @param roleIds
     */
    @Override
    public void saveUserRoleRelationShip(Long adminId, Long @NotNull [] roleIds) {
        adminRoleDao.deleteByAdminId(adminId);

        List<AdminRole> userRoleList = new ArrayList<>();
        for(Long roleId : roleIds) {
            if(StringUtils.isEmpty(roleId)) continue;
            AdminRole userRole = new AdminRole();
            userRole.setAdminId(adminId);
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        }
        adminRoleDao.insertBatch(userRoleList);
    }

}
