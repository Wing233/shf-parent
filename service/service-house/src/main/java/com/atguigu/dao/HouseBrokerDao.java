package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 18:37
 */


public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    List<HouseBroker> findListByHouseId(Long houseId);
}
