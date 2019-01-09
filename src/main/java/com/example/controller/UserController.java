package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lc
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/getAll", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return userService.findAllUser(pageNum,pageSize);
    }

    //PageHelper封装分页信息输出
    @ResponseBody
    @RequestMapping(value = "/pageUser")
    public PageInfo<User> getByPageUser(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo pageInfo = new PageInfo(userService.findAllUser(pageNum,pageSize));
        return pageInfo;
    }


}
