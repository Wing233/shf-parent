package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserFollowService extends BaseService<UserFollow> {
    /**
     * 关注房源
     * @param userId
     * @param houseId
     */
    Boolean follow(Long userId, Long houseId);

    /**
     * 用户是否关注房源
     * @param userId
     * @param houseId
     * @return
     */
    Boolean isFollow(Long userId, Long houseId);

    PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize, Long userId);

    /**
     * 取消关注
     * @param id
     * @return
     */
    Boolean cancelFollow(Long id);

    void insertBatch(List<UserFollow> userFollowList);

    void deleteByUserId(Long userId);

    List<Long> findHouseIdByUserId(Long userId);
}
