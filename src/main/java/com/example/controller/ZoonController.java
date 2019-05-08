package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author lc
 */
@Controller
@RequestMapping(value = "/zoons")
public class ZoonController {

    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String getZonn(@PathVariable int id){
        System.out.println("123123");
        System.out.println(id);
        return "9999";
    }


}
