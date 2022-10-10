package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.UserInfo;
import com.atguigu.service.HouseService;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/7 12:05
 */

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

    @Reference
    private HouseService houseService;

    @Reference
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String PAGE_INDEX = "member/index";

    private final static String LIST_ACTION = "redirect:/member";
    private final static String PAGE_CREATE = "member/create";
    private final static String PAGE_EDIT = "member/edit";
    private final static String PAGE_FOLLOW_SHOW = "member/follow";

    @RequestMapping
    public String index(@NotNull ModelMap model, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        PageInfo<UserInfo> page = userInfoService.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(@NotNull UserInfo user, HttpServletRequest request) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        user.setStatus(1);
        userInfoService.insert(user);
        return this.successPage("保存成功!", request);
    }

    @GetMapping("/edit/{id}")
    public String edit(@NotNull ModelMap model, @PathVariable Long id) {
        UserInfo user = userInfoService.getById(id);
        model.addAttribute("user", user);
        return PAGE_EDIT;
    }

    @PostMapping(value = "/update")
    public String update(UserInfo user, HttpServletRequest request) {
        userInfoService.update(user);
        return this.successPage("修改成功!", request);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userInfoService.delete(id);
        return LIST_ACTION;
    }

    @GetMapping("/follow/{userId}")
    public String toFollow(@NotNull ModelMap model, @PathVariable Long userId) {
        Map<String, Object> followMap = houseService.findFollowByUserId(userId);
        model.addAllAttributes(followMap);
        model.addAttribute("userId", userId);
        return PAGE_FOLLOW_SHOW;
    }


    @PostMapping("/follow")
    public String follow(Long userId, Long[] houseIds, HttpServletRequest request) {
        houseService.saveUserHouseRelationShip(userId,houseIds);
        return this.successPage(MESSAGE_SUCCESS, request);
    }

}
