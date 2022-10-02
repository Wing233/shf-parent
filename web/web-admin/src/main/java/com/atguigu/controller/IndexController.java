package com.atguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/27 13:21
 */

@Controller
public class IndexController {

    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_MAIN = "frame/main";

    @GetMapping("/")
    public String index() {
        return PAGE_INDEX;
    }

    @GetMapping("/main")
    public String main() {
        return PAGE_MAIN;
    }

}
