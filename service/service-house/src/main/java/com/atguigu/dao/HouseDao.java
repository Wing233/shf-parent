package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.House;
import com.atguigu.entity.Role;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 17:59
 */


public interface HouseDao extends BaseDao<House> {
    Page<HouseVo> findListPage(@Param("vo") HouseQueryVo houseQueryVo);

    List<House> findAll();
}
