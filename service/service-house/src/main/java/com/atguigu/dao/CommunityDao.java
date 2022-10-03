package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * @Author : C22
 * @create 2022/10/2 17:49
 */


public interface CommunityDao extends BaseDao<Community> {
    List<Community> findAll();
}
