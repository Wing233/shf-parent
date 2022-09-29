package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/27 12:53
 */

@Repository("roleDao")
public interface RoleDao extends BaseDao<Role> {
    List<Role> findAll();

}
