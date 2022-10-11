package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

    public static final String LIST_ACTION = "redirect:/role";

    public static final String PAGE_INDEX = "role/index";

    public static final String PAGE_CREATE = "role/create";

    public static final String PAGE_EDIT = "role/edit";

    public static final String PAGE_ASSIGN_SHOW = "role/assignShow";

    @PreAuthorize("hasAnyAuthority('role.show')")
    @RequestMapping
    public String index(@NotNull ModelMap model, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        PageInfo<Role> page = roleService.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    @PreAuthorize("hasAnyAuthority('role.create')")
    @GetMapping("/create")
    public String create(ModelMap modelMap) {
        return PAGE_CREATE;
    }

    @PreAuthorize("hasAnyAuthority('role.create')")
    @PostMapping("/save")
    public String save(Role role, @NotNull HttpServletRequest request) {
        roleService.insert(role);
        return this.successPage("添加成功!", request);
    }

    @PreAuthorize("hasAnyAuthority('role.edit')")
    @GetMapping("/edit/{id}")
    public String edit(@NotNull ModelMap model, @PathVariable Long id) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        return PAGE_EDIT;
    }

    @PreAuthorize("hasAnyAuthority('role.edit')")
    @PostMapping(value="/update/{id}")
    public String update(@PathVariable Long id,Role role, HttpServletRequest request) {
        Role currentRole = roleService.getById(id);
        BeanUtils.copyProperties(role, currentRole);

        roleService.update(currentRole);
        return this.successPage(MESSAGE_SUCCESS, request);
    }

    @PreAuthorize("hasAnyAuthority('role.delete')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        roleService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 进入分配权限页面
     * @param roleId
     * @return
     */
    @PreAuthorize("hasAuthority('role.assgin')")
    @GetMapping("/assignShow/{roleId}")
    public String assignShow(Model model, @PathVariable Long roleId) {
        List<Map<String, Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        model.addAttribute("zNodes", JSON.toJSONString(zNodes));
        model.addAttribute("roleId", roleId);
        return PAGE_ASSIGN_SHOW;
    }

    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @PreAuthorize("hasAuthority('role.assgin')")
    @PostMapping("/assignPermission")
    public String assignPermission(Long roleId, Long[] permissionIds, HttpServletRequest request) {
        permissionService.saveRolePermissionRelationShip(roleId, permissionIds);
        return this.successPage("修改成功", request);
    }

}
