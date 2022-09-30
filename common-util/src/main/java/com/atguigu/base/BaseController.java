package com.atguigu.base;

import com.github.pagehelper.StringUtil;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA
 *
 * @Author : C22
 * @create 2022/9/27 20:43
 */


public class BaseController {

    private static final String PAGE_SUCCESS = "common/successPage";

    public static final String MESSAGE_SUCCESS = "操作成功!";

    protected String successPage(String message, @NotNull HttpServletRequest request) {
        request.setAttribute("messagePage", StringUtil.isEmpty(message) ? MESSAGE_SUCCESS : message);
        return PAGE_SUCCESS;
    }

    protected Map<String, Object> getFilters(@NotNull HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> filters = new TreeMap();
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            if (values != null && values.length != 0) {
                if (values.length > 1) {
                    filters.put(paramName, values);
                } else {
                    filters.put(paramName, values[0]);
                }
            }
        }
        if (!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if (!filters.containsKey("pageSize")) {
            filters.put("pageSize", 10);
        }
        return filters;
    }

}
