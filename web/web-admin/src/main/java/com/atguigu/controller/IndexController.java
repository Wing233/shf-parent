package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/27 13:21
 */

@Controller
public class IndexController extends BaseController {

    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_MAIN = "frame/main";
    private static final String PAGE_LOGIN = "frame/login";
    private static final String PAGE_AUTH = "frame/auth";

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    @GetMapping("/")
    public String index(@NotNull ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Admin admin = adminService.getByUserName(user.getUsername());
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());
        model.addAttribute("admin", admin);
        model.addAttribute("permissionList", permissionList);
        return PAGE_INDEX;
    }

    @GetMapping("/main")
    public String main() {
        return PAGE_MAIN;
    }

    @GetMapping("/getInfo")
    @ResponseBody
    public Object getInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal();
    }

    @GetMapping("/login")
    public String login() {
        return PAGE_LOGIN;
    }

    @GetMapping("/auth")
    public String auth() {
        return PAGE_AUTH;
    }
}
