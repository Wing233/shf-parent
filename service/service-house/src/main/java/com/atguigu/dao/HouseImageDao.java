package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 18:38
 */


public interface HouseImageDao extends BaseDao<HouseImage> {
    List<HouseImage> findList(@Param("houseId") Long houseId, @Param("type") Integer type);
}
