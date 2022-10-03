package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/10/2 14:56
 */


@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    @Reference
    private DictService dictService;

    public static final String PAGE_INDEX = "dict/index";

    @GetMapping("/findZnodes")
    @ResponseBody
    public Result findByParentId(@RequestParam(value = "id", defaultValue = "0")Long id) {
        List<Map<String, Object>> zNodes = dictService.findZnodes(id);
        return Result.ok(zNodes);
    }

    @GetMapping
    public String index(ModelMap model) {
        return PAGE_INDEX;
    }

    @GetMapping("/findListByParentId/{parentId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId) {
        List<Dict> list = dictService.findListByParentId(parentId);
        return Result.ok(list);
    }

    @GetMapping("/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }

}
