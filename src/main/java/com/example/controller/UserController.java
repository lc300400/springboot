package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lc
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping(value = "/getAll", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return userService.findAllUser(pageNum, pageSize);
    }


    //PageHelper封装分页信息输出
    @ResponseBody
    @RequestMapping(value = "/pageUser")
    public PageInfo<User> getByPageUser(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(userService.findAllUser(pageNum, pageSize));
    }


    /**
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     * 方法返回的user对象没有ID，redis中存储数据key不正确（待修改）
     */
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    //@CachePut(cacheNames = "userInfo", key = "'user:'+#userId", unless = "#result eq null")
    public User addUser(User user) {
        userService.addUser(user);
        return user;
    }


    /**
     * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * unless 表示条件表达式成立的话不放入缓存
     */
    @ResponseBody
    @RequestMapping("/getById")
    @Cacheable(cacheNames = "userInfo", key = "'user:'+#userId", unless = "#result eq null")
    public User getById(@RequestParam("userId") int userId) {
        User user = userService.getById(userId);
        System.out.println("redis缓存测试");
        return user;
    }


    /**
     * @Cacheable 应用到删除数据的方法上
     */
    @ResponseBody
    @RequestMapping("/delUser")
    @CacheEvict(cacheNames = "userInfo", key = "'user:'+#userId", condition = "#result eq null")
    public void delUserInfo(@RequestParam int userId) {
        userService.deleteByPrimaryKey(userId);
    }

    @ResponseBody
    @RequestMapping("/uid")
    public Map<String, Object> testSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        // 設置session中的值
        httpSession.setAttribute("username", "session" + System.currentTimeMillis()+"");
        Map<String, Object> rtnMap = new HashMap<>();
        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            rtnMap.put(name, httpSession.getAttribute(name));
        }
        rtnMap.put("sessionId", httpSession.getId());
        return rtnMap;
    }

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", "www.baidu.com");
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getAttribute("request Url"));
        map.put("message", request.getSession().getAttribute("map"));
        return map;
    }


}
