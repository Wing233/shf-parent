package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/5 15:00
 */

@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {
    private static final String PAGE_INDEX = "permission/index";
    private static final String PAGE_CREATE = "permission/create";
    public static final String MESSAGE_SUCCESS = "保存成功!";
    private static final String PAGE_EDIT = "permission/edit";
    private static final String LIST_ACTION = "redirect:/permission";
    @Reference
    private PermissionService permissionService;

    /**
     * 获取菜单
     * @return
     */
    @GetMapping
    public String index(@NotNull ModelMap model) {
        List<Permission> list = permissionService.findAllMenu();
        model.addAttribute("list", list);
        return PAGE_INDEX;
    }

    /**
     * 进入新增
     * @param model
     * @param permission
     * @return
     */
    @GetMapping("/create")
    public String create(@NotNull ModelMap model, Permission permission) {
        model.addAttribute("permission", permission);
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param permission
     * @param request
     * @return
     */
    @PostMapping("/save")
    public String save(Permission permission, HttpServletRequest request) {
        permissionService.insert(permission);
        return this.successPage(MESSAGE_SUCCESS, request);
    }

    /**
     * 编辑
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable Long id) {
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission", permission);
        return PAGE_EDIT;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, Permission permission, HttpServletRequest request) {
        permission.setId(id);
        permissionService.update(permission);
        return this.successPage(MESSAGE_SUCCESS, request);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        permissionService.delete(id);
        return LIST_ACTION;
    }

}
