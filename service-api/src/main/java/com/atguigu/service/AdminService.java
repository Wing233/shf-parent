package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/28 17:30
 */
public interface AdminService extends BaseService<Admin> {

    /**
     * 根据用户名查找管理员
     * @param username
     * @return
     */
    Admin getByUserName(String username);

    /**
     * 查询全部管理员返回List集合
     * @return
     */
    List<Admin> findAll();

}
