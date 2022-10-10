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

    /**
     * 查询全部社区对象返回List集合
     * @return
     */
    List<Community> findAll();

}
