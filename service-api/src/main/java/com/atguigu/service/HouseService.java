package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 18:02
 */


public interface HouseService extends BaseService<House> {
    void publish(Long id, Integer status);
}
