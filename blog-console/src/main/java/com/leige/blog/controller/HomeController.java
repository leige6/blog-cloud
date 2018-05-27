package com.leige.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/5/25  10:03
 */
@Controller
public class HomeController {
    @RequestMapping(value={"/","/index"})
    @ResponseBody
    public String index(Model model){
        System.out.println("访问主页");
        return "index";
    }
}
