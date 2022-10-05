package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 14:40
 */

@Service(interfaceClass = DictService.class)
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {

    @Autowired
    private DictDao dictDao;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        List<Dict> dictList = dictDao.findListByParentId(id);
        List<Map<String, Object>> zNodes = new ArrayList<>();
        for (Dict dict : dictList) {
            Integer count = dictDao.countIsParent(dict.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("id", dict.getId());
            map.put("isParent", count > 0 ? true : false);
            map.put("name", dict.getName());
            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get("zfw:dict:parentId:" + parentId);
            if (!StringUtils.isEmpty(value)) {
                Type listType = new TypeReference<List<Dict>>(){}.getType();
                List<Dict> list = JSON.parseObject(value, listType);
                return list;
            }
            List<Dict> dictList = dictDao.findListByParentId(parentId);
            if (!CollectionUtils.isEmpty(dictList)) {
                jedis.set("zfw:dict:parentId:" + parentId, JSON.toJSONString(dictList));
                return dictList;
            }
        } finally {
            if (jedis != null) {
                jedis.close();
                if (jedis.isConnected()) {
                    try {
                        jedis.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get("zfw:dict:dictCode:" + dictCode);
            if(!StringUtils.isEmpty(value)) {
                return JSON.parseObject(value, List.class);
            }
            Dict dict = dictDao.getByDictCode(dictCode);
            List<Dict> dictList = this.findListByParentId(dict.getId());
            if (!CollectionUtils.isEmpty(dictList)) {
                jedis.set("zfw:dict:dictCode:" + dictCode, JSON.toJSONString(dictList));
                return dictList;
            }
        } finally {
            if(jedis != null) {
                jedis.close();
                if (jedis.isConnected()) {
                    try {
                        jedis.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getNameById(Long id) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get("zfw:dict:id:" + id);
            if(!StringUtils.isEmpty(value)) {
                return value;
            }
            String name = dictDao.getNameById(id);
            if (!StringUtils.isEmpty(name)) {
                jedis.set("zfw:dict:id:" + id, name);
                return name;
            }
        } finally {
            if(jedis != null) {
                jedis.close();
                if (jedis.isConnected()) {
                    try {
                        jedis.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "";
    }

    @Override
    protected BaseDao<Dict> getEntityDao() {
        return dictDao;
    }
}
