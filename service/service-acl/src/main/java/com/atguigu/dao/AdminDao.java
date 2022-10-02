package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Admin;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/28 17:30
 */


public interface AdminDao extends BaseDao<Admin> {
    Admin getByUserName(String username);
}
