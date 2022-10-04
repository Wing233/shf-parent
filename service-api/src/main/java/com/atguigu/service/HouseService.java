package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 18:02
 */


public interface HouseService extends BaseService<House> {
    void publish(Long id, Integer status);

    PageInfo<HouseVo> findListPage(int pageNum, int pageSize, HouseQueryVo houseQueryVo);
}
