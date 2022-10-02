package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/28 17:30
 */


public interface AdminService extends BaseService<Admin> {
    Admin getByUserName(String username);
}
