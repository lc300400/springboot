package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 读取系统资源配置文件信息
 */
@RestController
@PropertySource(value = {"classpath:databases.properties"})
public class PropertiesController {

    @Value("${test.msg}")
    private String msg;

    @Autowired
    private Environment env;

    //一、读取核心配置文件信息application.properties的内容(@value)
    @RequestMapping("/method1")
    public String method1() {
        return "方式一：" + msg;
    }

    //二、读取核心配置文件信息application.properties的内容(@Autowired Environment)
    @RequestMapping("/method2")
    public String method2() {
        return "方式二：" + env.getProperty("test.msg");
    }

    //三、读取自定义properties文件内容(@PropertySource+Environment)
    @RequestMapping("/method3")
    public String method3() {
        return "方式三： " + env.getProperty("test.password");
    }


}
