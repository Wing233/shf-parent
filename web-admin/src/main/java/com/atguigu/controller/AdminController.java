package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
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
 * @create 2022/9/28 17:32
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    private final static String LIST_ACTION = "redirect:/admin";
    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";

    @RequestMapping
    public String index(@NotNull ModelMap model, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        PageInfo<Admin> page = adminService.findPage(filters);

        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(@NotNull Admin admin, HttpServletRequest request) {
        Admin exist = adminService.getByUserName(admin.getUsername());
        if (exist != null) {
            return this.successPage("用户名已存在!", request);
        }
        admin.setHeadUrl("http://127.0.0.1:8000/static/img/a1.jpg");
        adminService.insert(admin);
        return this.successPage("保存成功!", request);
    }

    @GetMapping("/edit/{id}")
    public String edit(@NotNull ModelMap model, @PathVariable Long id) {
        Admin admin = adminService.getById(id);
        model.addAttribute("admin", admin);
        return PAGE_EDIT;
    }

    @PostMapping(value = "/update")
    public String update(Admin admin, HttpServletRequest request) {
        adminService.update(admin);
        return this.successPage("修改成功!", request);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        adminService.delete(id);
        return LIST_ACTION;
    }

}
