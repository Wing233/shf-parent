package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 17:50
 */


public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();
}
