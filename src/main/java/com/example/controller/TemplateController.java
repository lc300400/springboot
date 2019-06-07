package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * thymeleaf模板测试
 *
 * @author lc
 */
@Controller
@RequestMapping
public class TemplateController {

    @RequestMapping("/helloHtml")
    public String helloHtml(HttpServletRequest request) {
        request.setAttribute("title", "我的第一个web页面");
        return "index";
    }
}
