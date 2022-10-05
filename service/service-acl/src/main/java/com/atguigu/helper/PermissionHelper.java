package com.atguigu.helper;

import com.atguigu.entity.Permission;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/5 13:19
 *
 * 根据权限数据构建菜单数据
 */


public class PermissionHelper {

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    public static @NotNull List<Permission> build(@NotNull List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if (treeNode.getParentId().longValue() == 0) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNode
     * @param treeNodes
     * @return
     */

    @Contract("_, _ -> param1")
    public static @NotNull Permission findChildren(@NotNull Permission treeNode, @NotNull List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());
        for (Permission it : treeNodes) {
            if (treeNode.getId().longValue() == it.getParentId().longValue()) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

}
