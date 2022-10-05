package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/5 18:40
 */

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("username----->" + s);
        Admin admin = adminService.getByUserName(s);
        if (null == admin) {
            throw new UsernameNotFoundException("用户名不存在!");
        }

        List<String> codeList = permissionService.findCodeListByAdminId(admin.getId());
        Collection<GrantedAuthority> anthorities = new ArrayList<>();
        for (String code : codeList) {
            if (StringUtils.isEmpty(code)) continue;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(code);
            anthorities.add(authority);
        }
        return new User(s, admin.getPassword(), anthorities);
    }
}
