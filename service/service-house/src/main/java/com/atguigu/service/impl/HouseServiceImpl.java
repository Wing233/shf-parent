package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.House;
import com.atguigu.entity.Role;
import com.atguigu.entity.UserFollow;
import com.atguigu.service.HouseService;
import com.atguigu.service.UserFollowService;
import com.atguigu.service.UserInfoService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 18:00
 */

@Transactional
@Service(interfaceClass = HouseService.class)
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService{
    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;

    @Reference(check = false)
    private UserFollowService userFollowService;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long id, Integer status) {
        House house = new House();
        house.setId(id);
        house.setStatus(status);
        houseDao.update(house);
    }

    @Override
    public PageInfo<HouseVo> findListPage(int pageNum,int pageSize,HouseQueryVo houseQueryVo) {
        PageHelper.startPage(pageNum, pageSize);
        Page<HouseVo> page = houseDao.findListPage(houseQueryVo);
        List<HouseVo> list = page.getResult();
        for(HouseVo houseVo : list) {
            //户型：
            String houseTypeName = dictDao.getNameById(houseVo.getHouseTypeId());
            //楼层
            String floorName = dictDao.getNameById(houseVo.getFloorId());
            //朝向：
            String directionName = dictDao.getNameById(houseVo.getDirectionId());
            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(floorName);
            houseVo.setDirectionName(directionName);
        }
        return new PageInfo<HouseVo>(page, 10);
    }

    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        if(null == house) return null;

        //户型：
        String houseTypeName = dictDao.getNameById(house.getHouseTypeId());
        //楼层
        String floorName = dictDao.getNameById(house.getFloorId());
        //建筑结构：
        String buildStructureName = dictDao.getNameById(house.getBuildStructureId());
        //朝向：
        String directionName = dictDao.getNameById(house.getDirectionId());
        //装修情况：
        String decorationName = dictDao.getNameById(house.getDecorationId());
        //房屋用途：
        String houseUseName = dictDao.getNameById(house.getHouseUseId());
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setBuildStructureName(buildStructureName);
        house.setDirectionName(directionName);
        house.setDecorationName(decorationName);
        house.setHouseUseName(houseUseName);
        return house;
    }

    @Override
    public Map<String, Object> findFollowByUserId(Long userId) {
        List<House> allHousesList = houseDao.findAll();
        //拥有的角色id
        List<Long> existHouseIdList = userFollowService.findHouseIdByUserId(userId);
        //对角色进行分类
        List<House> noAssignHouseList = new ArrayList<>();
        List<House> assignHouseList = new ArrayList<>();
        for (House house : allHousesList) {
            //已分配
            if(existHouseIdList.contains(house.getId())) {
                assignHouseList.add(house);
            } else {
                noAssignHouseList.add(house);
            }
        }

        Map<String, Object> houseMap = new HashMap<>();
        houseMap.put("noAssignHouseList", noAssignHouseList);
        houseMap.put("assignHouseList", assignHouseList);
        return houseMap;
    }

    @Override
    public void saveUserHouseRelationShip(Long userId, Long @NotNull [] houseIds) {
        userFollowService.deleteByUserId(userId);

        if (houseIds == null || houseIds.length == 0) return;

        List<UserFollow> userFollowList = new ArrayList<>();

        for(Long houseId : houseIds) {
            if(StringUtils.isEmpty(houseId)) continue;
            UserFollow userFollow = new UserFollow();
            userFollow.setUserId(userId);
            userFollow.setHouseId(houseId);
            userFollowList.add(userFollow);
        }

        userFollowService.insertBatch(userFollowList);
    }

    @Override
    public List<House> findAll() {
        return houseDao.findAll();
    }


}
