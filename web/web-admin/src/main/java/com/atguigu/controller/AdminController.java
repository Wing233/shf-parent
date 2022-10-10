package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/28 17:32
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String LIST_ACTION = "redirect:/admin";
    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";

    private final static String PAGE_UPLOED_SHOW = "admin/upload";

    private final static String PAGE_ASSGIN_SHOW = "admin/assignShow";


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
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setHeadUrl("http://43.143.135.32:8080/static/img/a1.jpg");
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

    @GetMapping("/uploadShow/{id}")
    public String uploadShow(@NotNull ModelMap model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return PAGE_UPLOED_SHOW;
    }

    @PostMapping("/upload/{id}")
    public String upload(@PathVariable Long id, @RequestParam(value = "file") @NotNull MultipartFile file, HttpServletRequest request) throws IOException {
        String newFileName =  UUID.randomUUID().toString() ;
        // 上传图片
        QiniuUtils.upload2Qiniu(file.getBytes(),newFileName);
        String url= "http://rj61kbrzq.hn-bkt.clouddn.com/"+ newFileName;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl(url);
        adminService.update(admin);
        return this.successPage(this.MESSAGE_SUCCESS, request);
    }

    /**
     * 进入分配角色页面
     * @param adminId
     * @return
     */
    @GetMapping("/assignShow/{adminId}")
    public String assignShow(ModelMap model,@PathVariable Long adminId) {
        Map<String, Object> roleMap = roleService.findRoleByAdminId(adminId);
        model.addAllAttributes(roleMap);
        model.addAttribute("adminId", adminId);
        return PAGE_ASSGIN_SHOW;
    }

    /**
     * 根据用户分配角色
     * @param adminId
     * @param roleIds
     * @return
     */
    @PostMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds, HttpServletRequest request) {
        roleService.saveUserRoleRelationShip(adminId,roleIds);
        return this.successPage(this.MESSAGE_SUCCESS, request);
    }


}
