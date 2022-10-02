package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/27 13:05
 */

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

    @Reference
    RoleService roleService;

    public static final String LIST_ACTION = "redirect:/role";

    public static final String PAGE_INDEX = "role/index";

    public static final String PAGE_CREATE = "role/create";

    public static final String PAGE_EDIT = "role/edit";

//    @RequestMapping
//    public String index(@NotNull ModelMap model) {
//        List<Role> list = roleService.findAll();
//        model.addAttribute("list", list);
//        return PAGE_INDEX;
//    }

    @RequestMapping
    public String index(@NotNull ModelMap model, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        PageInfo<Role> page = roleService.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    @GetMapping("/create")
    public String create(ModelMap modelMap) {
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(Role role, @NotNull HttpServletRequest request) {
        roleService.insert(role);
        return this.successPage("添加成功!", request);
    }

    @GetMapping("/edit/{id}")
    public String edit(@NotNull ModelMap model, @PathVariable Long id) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        return PAGE_EDIT;
    }

    @PostMapping(value="/update/{id}")
    public String update(@PathVariable Long id,Role role, HttpServletRequest request) {
        Role currentRole = roleService.getById(id);
        BeanUtils.copyProperties(role, currentRole);

        roleService.update(currentRole);
        return this.successPage(MESSAGE_SUCCESS, request);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        roleService.delete(id);
        return LIST_ACTION;
    }

}
